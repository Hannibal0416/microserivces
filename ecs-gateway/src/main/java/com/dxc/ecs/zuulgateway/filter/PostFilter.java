package com.dxc.ecs.zuulgateway.filter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharStreams;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class PostFilter extends ZuulFilter {

  @Override
  public String filterType() {
    return "post";
  }

  @Override
  public int filterOrder() {
    return 1;
  }

  @Override
  public boolean shouldFilter() {
    return true;
  }

  @Override
  public Object run() {
    RequestContext ctx = RequestContext.getCurrentContext();
    HttpServletRequest request = ctx.getRequest();
    HttpServletResponse response = ctx.getResponse();
    if ("/api/ecs-oauth-service/oauth/token".equals(request.getRequestURI()) && HttpStatus.OK.value() == ctx.getResponseStatusCode()) {
      try (final InputStream responseDataStream = ctx.getResponseDataStream()) {
        final String responseData = CharStreams.toString(new InputStreamReader(responseDataStream, "UTF-8"));
        ctx.setResponseBody(responseData);
      } catch (IOException e) {
        e.printStackTrace();
      }
      ObjectMapper om = new ObjectMapper();
      try {
        Map<String, Object> responseMap = om.readValue(ctx.getResponseBody(), new TypeReference<Map<String, Object>>() {});
        Cookie accessToken = new Cookie("access_token", (String) responseMap.get("access_token"));
        accessToken.setHttpOnly(true);
        accessToken.setPath("/api/");
        Cookie refreshToken = new Cookie("refresh_token", (String) responseMap.get("refresh_token"));
        refreshToken.setHttpOnly(true);
        refreshToken.setPath("/api/");
        Cookie jti = new Cookie("jti", (String) responseMap.get("jti"));
        jti.setPath("/");
        response.addCookie(accessToken);
        response.addCookie(refreshToken);
        response.addCookie(jti);
      } catch (JsonMappingException e) {
        e.printStackTrace();
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }
    response.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
    response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
    response.addHeader("Access-Control-Allow-Credentials", "true");
    response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, X-PINGOTHER");
    
    System.out.println("Inside Post Filter");

    return null;
  }

}
