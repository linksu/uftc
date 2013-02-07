package net.ambientia.uftc.controller;

import java.security.Principal;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;

import net.ambientia.uftc.HomeController;
import net.ambientia.uftc.domain.PointFactorType;
import net.ambientia.uftc.domain.SportEvent;
import net.ambientia.uftc.domain.Uftc;
import net.ambientia.uftc.domain.User;
import net.ambientia.uftc.domain.Workout;
import net.ambientia.uftc.domain.User.FieldTypes;
import net.ambientia.uftc.service.SportEventService;
import net.ambientia.uftc.service.UftcService;
import net.ambientia.uftc.service.UserService;
import net.ambientia.uftc.service.WorkoutService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UftcService uftcService;
	
	@Autowired
	private SportEventService sportEventService;
	
	@Autowired
	private WorkoutService workoutService;
	
	private static final Logger logger = LoggerFactory
			.getLogger(AdminController.class);
	
	@RequestMapping(value = "/admin/userActivate", method = RequestMethod.GET)
	public String changeUserEnabledStatus(@RequestParam("userId") int id, Principal principal) {
		logger.debug("Received request to change user enabled status");

		User currentUser = userService.getUserByUsername(principal.getName());
		
		if(!currentUser.getAuthority().equals("ROLE_ADMIN") || currentUser.getId().equals(id))
		{
			// Not an admin or trying to act on own account
			return "redirect:/admin";
		}
		
		User user = userService.getById(id);

		if (user.isEnabled()) {
			user.setEnabled(false);
		} else {
			user.setEnabled(true);
		}

		userService.save(user);

		return "redirect:/admin";
	}
	
	
	@RequestMapping(value = "/admin/userAdd", method = RequestMethod.GET)
	public String getAdd(Model model, Principal principal) {
		logger.debug("Received request to show add user page");
		
		User currentUser = userService.getUserByUsername(principal.getName());
		
		if(!currentUser.getAuthority().equals("ROLE_ADMIN"))
		{
			// Not an admin
			return "redirect:/";
		}
		
		model.addAttribute("userInstance", new User());
		model.addAttribute("loggedInUser", currentUser);
		return "admin/userAdd";
	}

	
	@RequestMapping(value = "/admin/userAdd", method = RequestMethod.POST)
	public String add(@ModelAttribute("userInstance") User user, Model model, Principal principal) {
		logger.debug("Received request to add new user");
		
		User currentUser = userService.getUserByUsername(principal.getName());
		
		if(!currentUser.getAuthority().equals("ROLE_ADMIN"))
		{
			// Not an admin
			return "redirect:/";
		}		
		
		Uftc uftc = uftcService.getById(1);
		userService.setUserUftc(user, uftc);
		
		if (userService.isValid(user)) {
			if(user.getAuthority().equals("ROLE_CHALLENGER"))
			{
				user.setEnabled(false);
			}
			userService.add(user);
			return "redirect:/admin";
		} else {
			setupErrorModel(model, user);
			return "admin/userAdd";
		}
	}
	
	@RequestMapping(value = "/admin/userShow", method = RequestMethod.GET)
	public String getUserInfo(@RequestParam("userId") int id, Model model, Principal principal) {
		logger.debug("Received request to show user page");
		
		User currentUser = userService.getUserByUsername(principal.getName());
		
		if(!currentUser.getAuthority().equals("ROLE_ADMIN"))
		{
			// Not an admin
			return "redirect:/";
		}
		
		User user = userService.getById(id);

		model.addAttribute("user", user);
		model.addAttribute("loggedInUser", currentUser);

		return "admin/userShow";
	}
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String admin(Locale locale, Model model, Principal principal) {
		logger.debug("Received request to show admin page");
		
		
		User currentUser = userService.getUserByUsername(principal.getName());
		
		if(!currentUser.getAuthority().equals("ROLE_ADMIN"))
		{
			// Not an admin
			return "redirect:/";
		}
		
		List<SportEvent> sportEventList = sportEventService.getAll();
		List<User> userList = userService.getAll();

		model.addAttribute("sportEventInstance", new SportEvent());
		model.addAttribute("sportEventList", sportEventList);
		model.addAttribute("loggedInUser", currentUser);
		model.addAttribute("pointFactorTypeEnum", PointFactorType.values());
		model.addAttribute("userList", userList);
		
		return "admin/admin";
	}

	protected void setupErrorModel(Model model, User editedUser) {

		EnumSet<FieldTypes> errorsList = userService.getValidationErrorList(editedUser);
		String errors = "";
		for (FieldTypes fieldTypes : errorsList) {
			errors = errors + fieldTypes.toString();
		}
		model.addAttribute("userInstance", editedUser);
		model.addAttribute("errors", errors);

				
	}
}
