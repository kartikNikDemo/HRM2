package com.smart.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class testController{
  
	@GetMapping("/test1")
	public String check() {
	    System.out.println("under testing");
	    return "test1"; // Make sure the filename matches the case, "test1.html"
	}
}
