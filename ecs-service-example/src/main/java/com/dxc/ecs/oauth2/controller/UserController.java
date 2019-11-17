package com.dxc.ecs.oauth2.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("user")
public class UserController {

	@PostMapping("/profile")
//	@PreAuthorize("#oauth2.hasAnyAuthority('read')")
	@PreAuthorize("hasAnyAuthority('ROLE_DEVELOPERS','ROLE_MANAGERS')")
	public @ResponseBody String getOauth2Principal(OAuth2Authentication auth) {
		return "Access granted for " + auth.getPrincipal();
	}
	
}
