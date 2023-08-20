package com.jfast.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/page")
public class PageController {
	
	@GetMapping("/background-account")
	public String backgroundAccount() {
		return "background-account";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

}
