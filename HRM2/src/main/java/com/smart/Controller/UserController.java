package com.smart.Controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.Services.EmailService;
import com.smart.dao.AttendanceRepository;
import com.smart.dao.DepartmentRepository;
import com.smart.dao.LeaveRepository;
import com.smart.dao.PayrollRepository;
import com.smart.dao.ProjectRepository;
import com.smart.dao.UserRepository;
import com.smart.dao.contactRepository;
import com.smart.entity.Attendance;
import com.smart.entity.Contact;
import com.smart.entity.Leave;
import com.smart.entity.Payroll;
import com.smart.entity.Project;
import com.smart.entity.User;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private contactRepository contactRepository;
	
	@Autowired
	private AttendanceRepository attendanceRepository;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
     private PayrollRepository payrollRepository;
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private EmailService emailService;
    
	@Autowired
	private LeaveRepository leaveRepository;
	
	@Autowired
	private BCryptPasswordEncoder bcPasswordEncoder;

	// method adding common data to response
	@ModelAttribute
	public void addCommanData(Model model, Principal principal) {
		String username = principal.getName();
		// getuser details
		User user = userRepository.getUserByUserName(username);
		model.addAttribute("user", user);

	}

	// home dashboard
	@RequestMapping("/index")
	public String dashboard(Model model, Principal principal) {
		model.addAttribute("title", "User Dashboard");
		String username = principal.getName();
		// getuser details
		User user = userRepository.getUserByUserName(username);
		model.addAttribute("user", user);
		return "normal/user_dashboard";
	}

	@GetMapping("/add_contact")
	public String addContact(Model model) {
		model.addAttribute("title", "About");
		model.addAttribute("contact", new Contact());
		return "normal/add_contact_form";
	}

	// process to add contact
	@PostMapping("/process_contact")
	public String contact_process(@ModelAttribute Contact contact, @RequestParam("profileImage") MultipartFile file,
			Principal principal, HttpSession session) {
		try {
			String name = principal.getName();
			User user = userRepository.getUserByUserName(name);
			if (!file.isEmpty()) {

				contact.setImage(file.getOriginalFilename());
				File saveFile = new ClassPathResource("static/images").getFile();
				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			}
			else {
				contact.setImage("contact.png");
			}
			contact.setUser(user);
			user.getContact().add(contact);
			userRepository.save(user);
			System.err.println("user Data " + contact);
			session.setAttribute("message", new com.smart.helper.Message("Your Emplyoee added", "success"));
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("message", new com.smart.helper.Message("something went wrong", "danger"));
		}
		return "normal/add_contact_form";
	}

	// show contact handler
	// per page =5[n]
	// current page=0[page]

	@GetMapping("/show_contact/{page}")
	public String showContact(@PathVariable("page") Integer page, Model model, Principal principal) {
		model.addAttribute("title", "View Contacts");
		String userName = principal.getName(); // this pass the login user email id
		User user = userRepository.getUserByUserName(userName);
		Pageable pageable = PageRequest.of(page, 10); // use to number of record to show single page

		// List<Contact> contacts =
		// this.contactRepository.findContactByUser(user.getId()); //this is use to get
		// list of All Contact

		Page<Contact> contacts = this.contactRepository.findContactByUser(user.getId(), pageable);
		model.addAttribute("contacts", contacts);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", contacts.getTotalPages());

		return "normal/show_contacts";
	}

	// show specific contact
	@RequestMapping("/{cId}/contact")
	public String showPerticularContact(@PathVariable("cId") Integer cId, Model model, Principal principal) {
		Optional<Contact> contactOptional = contactRepository.findById(cId);
		Contact contact = contactOptional.get();
		String username = principal.getName();
		User user = userRepository.getUserByUserName(username);
		if (user.getId() == contact.getUser().getId()) {
			model.addAttribute("contact", contact);
		}
		return "normal/contact_detail";
	}

	// delete contact

	@GetMapping("/{cId}/delete")
	public String deleteContact(@PathVariable("cId") Integer cId, Model model, Principal principal,
			HttpSession session) {

		Contact contact = this.contactRepository.findById(cId).get();
		contact.setUser(null);
		this.contactRepository.delete(contact);

		session.setAttribute("message", new com.smart.helper.Message("Contact Deleted Successfuly", "success"));

		return "redirect:/user/show_contact/0";
	}

	// Update contact Data
	@PostMapping("/{cId}/update")
	public String contactData(@PathVariable("cId") Integer cId, Model model) {
		model.addAttribute("title", "Update Contacts");
		Contact contact = contactRepository.findById(cId).get();
		model.addAttribute("contact", contact);
		return "normal/updata_form";
	}

	// process update contact
	@PostMapping("/process_update")
	public String updateContact(@ModelAttribute Contact contact, @RequestParam("profileImage") MultipartFile file,
			Principal principal, HttpSession session) {

		// get old contact detail
		Contact oldContactDetails = this.contactRepository.findById(contact.getcId()).get();
		try {
			if (!file.isEmpty()) {

				// deleting old contact detail image
				File deleteFile = new ClassPathResource("static/images").getFile();
				File confirmDelete=new File(deleteFile, oldContactDetails.getImage());
				confirmDelete.delete();
				// adding new contact detail image
				File saveFile = new ClassPathResource("static/images").getFile();
				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				contact.setImage(file.getOriginalFilename());
			}

			User user = userRepository.getUserByUserName(principal.getName());
			contact.setUser(user);
			contactRepository.save(contact);
			session.setAttribute("message", new com.smart.helper.Message("Update Successfuly", "success"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/user/"+contact.getcId()+"/contact";
	}
	
	//View Your Profile
	@GetMapping("/profile")
	public String yourProfile() {
		
		return "normal/profile";
	}
	
	
	// adding atendance
	
	@GetMapping("/addAttendance")
	public String addAttendance(Model model) {
		model.addAttribute("title", "Add Attendance");
		model.addAttribute("attendance", new Attendance());
		return "normal/AddAttendance";
	}
	
//	@Transactional
	@PostMapping("/process_attendance")
	public String processAttendance(@ModelAttribute("attendance") Attendance attendance ,Principal principal) {
		String name = principal.getName();
		User user = userRepository.getUserByUserName(name);
		attendance.setUser(user);
		user.getAttendance().add(attendance);
		userRepository.save(user);
//		attendanceRepository.save(attendance);
		return "normal/AttendanceList";
	}
	
	@GetMapping("/getAttendanceList")
	public String attendanceList( Model model,Principal principal) {
		
		String userName = principal.getName(); // this pass the login user email id
		User user = userRepository.getUserByUserName(userName);
		List<Attendance> attendance = this.attendanceRepository.findAllAttendance(user.getId()); // this is use to get
		model.addAttribute("title", " Attendance List");
		model.addAttribute("attendance", attendance);
		return "normal/AttendanceList";
	}
	
	
	// Adding the project whole process handlers
	@GetMapping("/addProject")
	public String addProjct(Model model) {
		model.addAttribute("title", "Add Project");
		model.addAttribute("project", new Project());
		return "normal/Add_Project";
	}
	
	@PostMapping("/process_project")
	public String processProject(@ModelAttribute ("project") Project project) {
		projectRepository.save(project);
		return "normal/Project_List";
	}
	
	@GetMapping("/getProjectList")
	public String getProjectList(Model model) {
	List<Project> project=	projectRepository.getAllProjectList();
	model.addAttribute("project", project);
	model.addAttribute("title", " Attendance List");
		return "normal/Project_List";
	}
              	
	
	//payroll form
	
	@GetMapping("/getPayrollFrom")
	public String getPayrollForm(Model model) {
		model.addAttribute("title", "Add Payroll");
		model.addAttribute("payRoll", new Payroll());
		return "normal/payrollForm";
	}
	
	@PostMapping("/addingPayroll")
	public String addingPayroll(@ModelAttribute ("payRoll") Payroll payRoll,Principal principal) {
		String name = principal.getName();
		User user = userRepository.getUserByUserName(name);
		payRoll.setUser(user);
		user.getPayroll().add(payRoll);
		userRepository.save(user);
		return "normal/payrollForm";
	}
	
	@GetMapping("/getpayrollList")
	public String getPayrollList(Model model, Principal principal) {
		String userName = principal.getName(); // this pass the login user email id
		User user = userRepository.getUserByUserName(userName);
		List<Payroll> payroll = payrollRepository.findAllPayrollList(user.getId());
		model.addAttribute("title", "Payroll List");
		model.addAttribute("payroll", payroll);
		return "normal/PayrollList";
	}
	
	//list of Emplyoee Department Wise
	@RequestMapping("/emplyoeeIt")
	public String ItEmplyoeeList(Model model,Principal principal) {
		String userName = principal.getName(); // this pass the login user email id
		User user = userRepository.getUserByUserName(userName);
		List<Contact> itEmplyoee = departmentRepository.getListOFItEmplyoee(user);
		model.addAttribute("heading","IT Emplyoee List");
		model.addAttribute("EmplyoeeList",itEmplyoee);
		return "normal/department_wise_list";
		
	}
	@RequestMapping("/emplyoeeSales")
	public String salesEmplyoeeList(Model model,Principal principal) {
		String userName = principal.getName(); // this pass the login user email id
		User user = userRepository.getUserByUserName(userName);
		List<Contact> salesEmplyoee = departmentRepository.getListOFSalesEmplyoee(user);
		model.addAttribute("heading","Sales Emplyoee List");
		model.addAttribute("EmplyoeeList",salesEmplyoee);
		return "normal/department_wise_list";
		
	}
	
	@RequestMapping("/emplyoeeMarketing")
	public String MarketingEmplyoeeList(Model model,Principal principal) {
		String userName = principal.getName(); // this pass the login user email id
		User user = userRepository.getUserByUserName(userName);
		List<Contact> marketingEmplyoee = departmentRepository.getListOFMarketingEmplyoee(user);
		model.addAttribute("heading","Marketing Emplyoee List");
		model.addAttribute("EmplyoeeList",marketingEmplyoee);
		return "normal/department_wise_list";
		
	}
	
	@RequestMapping("/emplyoeeProduction")
	public String productionEmplyoeeList(Model model,Principal principal) {
		String userName = principal.getName(); // this pass the login user email id
		User user = userRepository.getUserByUserName(userName);
		List<Contact> productionEmplyoee = departmentRepository.getListOFProductionEmplyoee(user);
		model.addAttribute("heading","Production Emplyoee List");
		model.addAttribute("EmplyoeeList",productionEmplyoee);
		return "normal/department_wise_list";
		
	}
	
	@RequestMapping("/emplyoeePurchase")
	public String purchaseEmplyoeeList(Model model,Principal principal) {
		String userName = principal.getName(); // this pass the login user email id
		User user = userRepository.getUserByUserName(userName);
		List<Contact> productionEmplyoee = departmentRepository.getListOFPurchaseEmplyoee(user);
		model.addAttribute("heading","Purchase Emplyoee List");
		model.addAttribute("EmplyoeeList",productionEmplyoee);
		return "normal/department_wise_list";
		
	}
	
	@RequestMapping("/sendEmail")
	public String sendEmail(Model model,Principal principal) {
		String userName = principal.getName(); // this pass the login user email id
		User user = userRepository.getUserByUserName(userName);
		List<Contact> productionEmplyoee = departmentRepository.getListOFPurchaseEmplyoee(user);
		model.addAttribute("heading","Purchase Emplyoee List");
		model.addAttribute("EmplyoeeList",productionEmplyoee);
		return "normal/email_form";
		
	}
	
	@PostMapping("/process_sendEmail")
	public String processEmail(@RequestParam("toEmail") String toEmail, @RequestParam("subject") String subject,
			@RequestParam("message") String message , HttpSession session) {
       
		this.emailService.sendEmail(message, subject, toEmail, "kartikdemo5@gmail.com");
		session.setAttribute("message", new com.smart.helper.Message("Email Send Successfullly", "success"));
		return "normal/email_form";
	}
	
	// adding leave data handler
	
	@GetMapping("/add_leave")
	public String AddEmplyoeeLeave() {
		
		return "normal/add_leave";
	}
	
	
	
	@PostMapping("/process_leave")
	public String processLeave(@ModelAttribute ("leave") Leave leave,Principal principal) {
		
		String name = principal.getName();
		User user = userRepository.getUserByUserName(name);
		leave.setUser(user);
		user.getLeave().add(leave);
		userRepository.save(user);
		return  "normal/add_leave";
			
	}
	
	 @GetMapping("/leave_list")
	 public String allLeaveList(Principal principal,Model model) {
			String name = principal.getName();
			User user = userRepository.getUserByUserName(name);
			List<Leave> leaveList = leaveRepository.findAllLeave(user.getId());
			model.addAttribute("leave",leaveList);
		 return "normal/leave_List";
	 }
	// change Password 
		@GetMapping("/changePassword")
		public String openSetting() {
			
			return "normal/Setting";
		}

		// change password
	     @GetMapping("/changePasswordProcess") 
		public String changePassword(@RequestParam("oldPassword") String oldPassword,
				@RequestParam("NewPassword") String NewPassword, Principal principal,HttpSession session) {
			String userName = principal.getName(); // this pass the login user email id
			User user = userRepository.getUserByUserName(userName);
			System.err.println("oldPassword"+oldPassword);
			System.err.println("NewPassword"+NewPassword);
			
			System.out.println(user.getPassword());
			if (bcPasswordEncoder.matches( oldPassword,user.getPassword())) {

				user.setPassword(bcPasswordEncoder.encode(NewPassword));
	            userRepository.save(user);
	            System.err.println("enter correct password");
	            session.setAttribute("message", new com.smart.helper.Message("Password Changed Successfuly", "success"));
	            return "redirect:/user/index";
	            
			}
			else {
				session.setAttribute("message", new com.smart.helper.Message("Password Not Match", "danger"));
				System.err.println("enter wrong password");
				return "normal/ChangePassword";
			}
			
		}
	
}
