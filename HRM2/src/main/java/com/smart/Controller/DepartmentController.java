package com.smart.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smart.dao.DepartmentRepository;
import com.smart.dao.UserRepository;
import com.smart.entity.Contact;
import com.smart.entity.Payroll;
import com.smart.entity.User;

@Controller
@RequestMapping("/department")
public class DepartmentController {
	
	@Autowired
	private UserRepository userRepository;
    
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@RequestMapping("/emplyoeeIt")
	public String ItEmplyoeeList(Model model,Principal principal) {
		String userName = principal.getName(); // this pass the login user email id
		User user = userRepository.getUserByUserName(userName);
		List<Contact> itEmplyoee = departmentRepository.getListOFItEmplyoee(user);
		
		System.err.println("itEmplyoee  "+itEmplyoee);
		return "normal/user_dashboard";
		
	}

}
