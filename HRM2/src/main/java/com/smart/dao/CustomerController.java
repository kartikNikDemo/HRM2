package com.smart.dao;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smart.entity.User;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	private UserRepository userRepository;
	// home dashboard
		@RequestMapping("/index")
		public String dashboard(Model model, Principal principal) {
			model.addAttribute("title", "User Dashboard");
			String username = principal.getName();
			// getuser details
			User user = userRepository.getUserByUserName(username);
			model.addAttribute("user", user);
			return "customer/customer_dashboard";
		}

}
