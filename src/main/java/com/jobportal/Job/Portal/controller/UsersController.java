package com.jobportal.Job.Portal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.jobportal.Job.Portal.entity.Users;
import com.jobportal.Job.Portal.entity.UsersType;
import com.jobportal.Job.Portal.service.UsersService;
import com.jobportal.Job.Portal.service.UsersTypeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class UsersController {
	
	private final UsersTypeService usersTypeService;
	private final UsersService usersService;
	
	
	@Autowired
	public UsersController(UsersTypeService usersTypeService, UsersService usersService) {
		this.usersTypeService = usersTypeService;
		this.usersService = usersService;
	}
	
	@GetMapping("/register")
	public String register(Model model) {
		List<UsersType> usersTypes = usersTypeService.getAll();
		model.addAttribute("getAllTypes", usersTypes);
		model.addAttribute("user", new Users());
		return "register";
	}
	
	@PostMapping("/register/new")
	   public String userRegistration(@Valid Users users) {
	    /*   Optional<Users> optionalUsers = usersService.getUserByEmail(users.getEmail());

	       if(optionalUsers.isPresent()) {
	           model.addAttribute("error", "email already in use");
	           List<UsersType> usersTypes = usersTypeService.getAll();
	           model.addAttribute("getAllTypes", usersTypes);
	           model.addAttribute("user",new Users());
	           return "register";
	       }*/
	       usersService.addNew(users);
	       return "redirect:/dashboard/";
	   }
	
	@GetMapping("/login")
	public String login() {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
	        return "redirect:/dashboard/"; // Eğer zaten kimlik doğrulandıysa, dashboard'a yönlendir
	    }
	    return "login"; // Kimlik doğrulaması yapılmadıysa giriş sayfasını göster
	}
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication!=null) {
			new SecurityContextLogoutHandler().logout(request, response, authentication);
		}
		return "redirect:/";
	}
}
