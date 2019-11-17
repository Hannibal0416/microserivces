package com.dxc.ecs.oauth2.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("public")
public class PublicController {
	@Value("${server.port}")
	private int port;
	@GetMapping("/welcome")
	public @ResponseBody String getGreeting() {
		return "port " + port;
	}

}