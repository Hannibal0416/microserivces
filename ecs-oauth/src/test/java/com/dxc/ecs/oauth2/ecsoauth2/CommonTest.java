package com.dxc.ecs.oauth2.ecsoauth2;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;

public class CommonTest {
	@Test
	public void PwdTest() {
		System.out.println(new BCryptPasswordEncoder(4).encode("ecsapp123")); 
		System.out.println(new LdapShaPasswordEncoder().encode("ecsapp123"));
		System.out.println(new LdapShaPasswordEncoder().encode("ecsclient123"));
		System.out.println(new LdapShaPasswordEncoder().encode("hannibal123"));
		System.out.println(new LdapShaPasswordEncoder().encode("123456"));
		
	}

}
