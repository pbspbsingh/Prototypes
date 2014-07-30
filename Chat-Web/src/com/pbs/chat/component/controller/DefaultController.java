package com.pbs.chat.component.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {

	@RequestMapping(value = { "/", "index.html" })
	public String welcome() {
		return "welcome";
	}

}
