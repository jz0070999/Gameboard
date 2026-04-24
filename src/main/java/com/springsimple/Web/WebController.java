package com.springsimple.Web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class WebController {

	@PostMapping(value = "/test")
    @ResponseBody
	public String testPost() {

        return 
			"You Posted";
    }


	@GetMapping(value = "/test")
    @ResponseBody
	public String testGet() {

        return 
			"You Getted";
    }

}