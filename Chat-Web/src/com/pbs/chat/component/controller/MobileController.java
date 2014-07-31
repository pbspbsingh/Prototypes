package com.pbs.chat.component.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/mobile")
public class MobileController {

	@RequestMapping(value = "index.html")
	public String mainPage(HttpServletRequest request) {
		return "mobile/index";
	}
}
