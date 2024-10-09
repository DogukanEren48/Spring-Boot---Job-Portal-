package com.jobportal.Job.Portal.controller;

import java.util.Objects;
import java.util.Optional;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jobportal.Job.Portal.entity.RecruiterProfile;
import com.jobportal.Job.Portal.entity.Users;
import com.jobportal.Job.Portal.repository.IUserRepository;
import com.jobportal.Job.Portal.service.RecruiterProfileService;
import com.jobportal.Job.Portal.util.FileUploadUtil;



@Controller
@RequestMapping("/recruiter-profile")
public class RecruiterProfileController {
	
	private final IUserRepository userRepository;
	
	private final RecruiterProfileService recruiterProfileService;
	

	public RecruiterProfileController(IUserRepository userRepository,RecruiterProfileService recruiterProfileService) {
		
		this.userRepository = userRepository;
		this.recruiterProfileService = recruiterProfileService;
	}

	@GetMapping("/")
	public String recruiterProfile(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUsername = authentication.getName();
			Users users = userRepository.findByEmail(currentUsername).orElseThrow(()->new UsernameNotFoundException("Could not" + "found user"));
			Optional<RecruiterProfile> recruiterProfile = recruiterProfileService.getOne(users.getUserId());
			
			if(!recruiterProfile.isEmpty())
				model.addAttribute("profile", recruiterProfile.get());
		}
		return "recruiter_profile";
	}

	@PostMapping("/addNew")
	public String addNew(RecruiterProfile recruiterProfile,@RequestParam("image")
	MultipartFile multipartfile, Model model) {
		
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		if(!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUsername = authentication.getName();
			Users users = userRepository.findByEmail(currentUsername).orElseThrow(()->new UsernameNotFoundException("Could not" + "found user"));
			recruiterProfile.setUserId(users);
			recruiterProfile.setUserAccountId(users.getUserId());
		}
		model.addAttribute("profile", recruiterProfile);
		String fileName = "";
		if(!multipartfile.getOriginalFilename().equals("")) {
			fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartfile.getOriginalFilename()));
			recruiterProfile.setProfilePhoto(fileName);
		}
		RecruiterProfile savedUser = recruiterProfileService.addNew(recruiterProfile);
		
		String uploadDir = "photos/recruiter/"+savedUser.getUserAccountId();
		try {
			FileUploadUtil.saveFile(uploadDir, fileName, multipartfile);
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return "redirect:/dashboard/";
	}
}
