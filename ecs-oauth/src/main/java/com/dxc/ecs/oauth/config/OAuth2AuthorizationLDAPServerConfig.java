package com.dxc.ecs.oauth.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;
import com.google.common.collect.Lists;

@Configuration
@EnableAuthorizationServer
@Import(ServerWebSecurityConfig.class)
public class OAuth2AuthorizationLDAPServerConfig extends AuthorizationServerConfigurerAdapter {

  private Logger logger = LoggerFactory.getLogger(OAuth2AuthorizationLDAPServerConfig.class);
  
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private ClientDetailsService clientDetailsService;

  // private String ldapUrl="ldap://localhost:8389/dc=tcb-bank,dc=com,dc=tw";
  private String ldapUrl = "ldap://134.251.81.13:10389/dc=tcb-bank,dc=com,dc=tw";

  @Value("${myapp.ldap.user-dn-patterns:uid={0}, ou=people}")
  // @Value("${myapp.ldap.user-dn-patterns:uid={0}, ou=employee}")
  private String ldapUserDnPatterns;

  @Value("${myapp.ldap.user-search-base:}")
  private String ldapUserSearchBase;

  @Value("${myapp.ldap.group-search-base:ou=groups}")
  private String ldapGroupSearchBase;

  @Value("${myapp.ldap.group-search-filter:(uniqueMember={0})}")
  private String ldapGroupSearchFilter;

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints.authenticationManager(authenticationManager)
    .addInterceptor(new ClientHeadersInterceptor())
    .tokenServices(defaultTokenServices()).userDetailsService(ldapUserDetailsManager());
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
    oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("permitAll()").tokenKeyAccess("permitAll()");
  }

  @Bean
  public TokenStore tokenStore() {
    return new JwtTokenStore(accessTokenConverter());
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {


    clients.inMemory().withClient("ecsapp")
        // .secret("$2a$04$0HTkzNbcvs.uYlXWU1JOyuSM/wZX5H58ldHerAY4AGNjcqspxCtza")
        // .secret("{noop}ecsapp123")
        .secret("{SSHA}Y9BtgQQGTuxNfqAlq796xBGPd4X5SlOqv42xZQ==").accessTokenValiditySeconds(300).refreshTokenValiditySeconds(600).authorizedGrantTypes("refresh_token", "password").scopes("app").and()
        .withClient("ecsclient")
        // .secret("$2a$04$ziCM9yrI9OjpH20OlkXQ2.z4dbpc3tzTOJ9y7F0ZITpdhKIx/Gn3K")
        // .secret("{noop}ecsclient123")
        .secret("{SSHA}/gkFrWsNB6qk5ZKVXm8mYaaW2K5FRKBdCqAGRQ==").accessTokenValiditySeconds(3000).authorizedGrantTypes("client_credentials").scopes("client");
  }

  @Bean
  public JwtAccessTokenConverter accessTokenConverter() {
    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    converter.setSigningKey("123");
    return converter;
  }

  @Bean
  public DefaultTokenServices defaultTokenServices() {
    final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
    defaultTokenServices.setTokenStore(tokenStore());
    defaultTokenServices.setClientDetailsService(clientDetailsService);
    defaultTokenServices.setTokenEnhancer(tokenEnhancerChain());
    defaultTokenServices.setSupportRefreshToken(true);
    return defaultTokenServices;
  }

  @Bean
  public TokenEnhancerChain tokenEnhancerChain() {
    final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
    tokenEnhancerChain.setTokenEnhancers(Lists.newArrayList(new EcsTokenEnhancer(), accessTokenConverter()));
    return tokenEnhancerChain;
  }

  private static final ThreadLocal<WebRequest> webRequestLocal = new ThreadLocal<WebRequest>();

  private static class ClientHeadersInterceptor implements WebRequestInterceptor {

    @Override
    public void preHandle(WebRequest request) throws Exception {
      webRequestLocal.set(request);
      request.getHeaderNames().forEachRemaining(header -> {
        System.out.println(header+":"+request.getHeader(header));
      });
    }

    @Override
    public void postHandle(WebRequest request, ModelMap model) throws Exception {

    }

    @Override
    public void afterCompletion(WebRequest request, Exception ex) throws Exception {

    }

  }

  private static class EcsTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
      final DefaultOAuth2AccessToken result = new DefaultOAuth2AccessToken(accessToken);
      if ("password".equals(authentication.getOAuth2Request().getGrantType())) {
        final LdapUserDetails user = (LdapUserDetails) authentication.getPrincipal();
        WebRequest webRequest = webRequestLocal.get();
        // accessToken.
        // authentication.getOAuth2Request()
        result.getAdditionalInformation().put("other", user.getAuthorities());
        result.getAdditionalInformation().put("headers", webRequest.getHeaderNames());
       
      }
      return result;
    }
  }


  @Bean
  public DefaultSpringSecurityContextSource contextSource() {
    DefaultSpringSecurityContextSource dssc = new DefaultSpringSecurityContextSource(ldapUrl);
    dssc.setUserDn("cn=admin,dc=tcb-bank,dc=com,dc=tw");
    dssc.setPassword("1234qwer");
    return dssc;
  }

  @Bean
  public FilterBasedLdapUserSearch userSearch() {
    return new FilterBasedLdapUserSearch(ldapUserSearchBase, "uid={0}", contextSource());
  }

  @Bean
  public DefaultLdapAuthoritiesPopulator ldapAuthoritiesPopulator() {
    DefaultLdapAuthoritiesPopulator authPopulator = new DefaultLdapAuthoritiesPopulator(contextSource(), ldapGroupSearchBase);
    authPopulator.setGroupSearchFilter(ldapGroupSearchFilter);
    return authPopulator;
  }

  @Bean
  public LdapUserDetailsService ldapUserDetailsManager() {
    return new LdapUserDetailsService(userSearch(), ldapAuthoritiesPopulator());
  }

  @Bean
  public PasswordEncoder userPasswordEncoder() {
    return new LdapShaPasswordEncoder();
  }

}
