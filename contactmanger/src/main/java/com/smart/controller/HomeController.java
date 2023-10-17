package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.dao.UserRepository;
import com.smart.entity.Contact;
import com.smart.entity.User;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("title", "Home - smart contact manager");
		return "home";
	}

	@GetMapping("/about")
	public String about(Model model) {
		model.addAttribute("title", "About - Smart contact manager");
		return "about";
	}

	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("title", "Register - Smart contact manager");
		model.addAttribute("user", new User());
		return "signup";
	}

//	Register handler {We will save all the form data into db
//	@ModelAttribute("user") User user, use of this to bind the parameter
	@PostMapping("/do_register")
	public String registerUser(@ModelAttribute("user") User user,
			@RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model,
			HttpSession session) {
//		Condition for agreement
		try {
			if (!agreement) {
				System.out.println("Please select Terms & Condition");
				throw new Exception("Please select Terms & Condition");
			}
			user.setRole("User");  
			user.setEnabled(true);
			user.setIamageUrl("default.png");
//			Storing all the form  value in "theresultUser "
			User resultUser = this.userRepository.save(user);
			model.addAttribute("user", new User());
			session.setAttribute("message", new Message("User is register successfully!!", "alert-success"));
			return "signup";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user", user);
//			Here we are using session class method which take 2 parameter  
			session.setAttribute("message", new Message("Something went wrong!!" + e.getMessage(), "alert-danger") );
			return "signup";
		}

	}

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("title", "Login - Smart contact manager");
		return "login";
	}

	@GetMapping("/test")
	@ResponseBody
	public String test() {
		User user = new User();
		user.setName("Suraj Yadav");
		user.setEmail("suraj.yadav@gmail.com");

		Contact contact = new Contact();
		user.getContact().add(contact);

		userRepository.save(user);
		return "Working";
	}

}
