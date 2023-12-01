package com.smart.Controller;

import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.Services.EmailService;
import com.smart.dao.UserRepository;
import com.smart.entity.User;

@Controller
public class ForgotPassword {
	Random random = new Random(1000);
	@Autowired
	private EmailService emailService;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bynBCryptPasswordEncoder;

	// forgot password handler
	@RequestMapping("/forgotPassword")
	public String forgotPassword() {
		System.err.println("enter forgot password");
		return "forgot_Password";
	}

	// send otp handler

	@PostMapping("/send_otp")
	public String otpRequest(@RequestParam("email") String email, HttpSession session) {

		int otp = random.nextInt(999999);
		String message = "<h1> This Your OTP : " + otp + "</h1>";
		String subject = "CodersArea : Confirmation";
		String to = email;
		String from = "kartikdemo5@gmail.com";

		this.emailService.sendEmail(message, subject, to, from);
		session.setAttribute("otp", otp);
		session.setAttribute("email", email);

		return "verify_otp";
	}

	@PostMapping("/verify_otp")
	public String verifyOtp(@RequestParam("otp") int otp, HttpSession session) {

		int myotp = (int) session.getAttribute("otp");
		String myEmail = (String) session.getAttribute("email");
		if (otp == myotp) {

			User user = this.userRepository.getUserByUserName(myEmail);
			if (user == null) {

				session.setAttribute("message", "User Does Not Exist");

				return "forgot_Password";
			} else {
				return "change_Password_Form";
			}

		} else {
			session.setAttribute("message", "enterd Wrong OTP");
			return "verify_otp";
		}

	}
	
	@PostMapping("/change_password")
	public String changePassword(@RequestParam("newPassword") String newPassword,HttpSession session) {
		String myEmail = (String) session.getAttribute("email");
		User user = this.userRepository.getUserByUserName(myEmail);
		
		user.setPassword(this.bynBCryptPasswordEncoder.encode(newPassword));
		userRepository.save(user);
		session.setAttribute("message", "Password Change Successfully");
		return "redirect:/user/index";
		
	}
}
