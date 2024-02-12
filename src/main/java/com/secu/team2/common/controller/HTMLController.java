package com.secu.team2.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HTMLController {

	@GetMapping("/")
	public String home() {
		return "index";
	}

	@GetMapping("/html/**")
		public void goPage() {}
}