package net.ambientia.uftc.controller;

import java.security.Principal;
import java.util.EnumSet;

import net.ambientia.uftc.domain.Uftc;
import net.ambientia.uftc.domain.User;
import net.ambientia.uftc.domain.User.FieldTypes;
import net.ambientia.uftc.service.ChallengeSportEventService;
import net.ambientia.uftc.service.UftcService;
import net.ambientia.uftc.service.UserService;
import net.ambientia.uftc.service.WorkoutService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UftcService uftcService;

	@Autowired
	private WorkoutService workoutService;
	
	@Autowired
	private ChallengeSportEventService challengeSportEventService;

	private static final Logger logger = LoggerFactory
			.getLogger(UserController.class);
	
	@RequestMapping(value = "/registerSucceeded", method = RequestMethod.GET)
	public String getRegisterSucceeded(Model model, Principal principal) {
		logger.debug("Received request to show register succeeded page");
		return "uftc/registerSucceeded";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String getRegisterUser(Model model, Principal principal) {
		logger.debug("Received request to show register page");
		model.addAttribute("userInstance", new User());
		return "uftc/register";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerUser(@ModelAttribute("userInstance") User user, Model model) {
		logger.debug("Received request to add new user");
		Uftc uftc = uftcService.getById(1);
		userService.setUserUftc(user, uftc);
		if (userService.isValid(user)) {
			if(user.getAuthority().equals(User.CHALLENGER)) {
				user.setEnabled(false);
			}
			userService.add(user);
			return "redirect:/registerSucceeded";
		} else {
			setupErrorModel(model, user);
			return "uftc/register";
		}
	}

	@RequestMapping(value = "/user/edit", method = RequestMethod.GET)
	public String edit(@RequestParam("userId") int userId, Model model, Principal principal) {
		
		User currentUser = userService.getUserByUsername(principal.getName());
		User user = userService.getById(userId);

		if(!currentUser.getId().equals(user.getId()) && !currentUser.getAuthority().equals(User.ADMIN)){
			return "redirect:/denied";
		}
		
		model.addAttribute("loggedInUser", currentUser);
		model.addAttribute("userInstance", user);
		return "user/edit";
	}

	@RequestMapping(value = "/user/edit", method = RequestMethod.POST)
	public String update(@ModelAttribute("userInstance") User user, Model model, Principal principal) {
		logger.debug("Received request to update user");
		
		User currentUser = userService.getUserByUsername(principal.getName());
		
		if(!currentUser.getId().equals(user.getId()) && !currentUser.getAuthority().equals(User.ADMIN)){
			return "redirect:/denied";
		}
		
		if(userService.entityIsLocked(user)){
			setupOptimisticLockErrorModel(model, user);
			return "user/edit";
		}
		User editedUser = userService.setNewPropertiesToExistingUser(user);
		if (!editedUser.getFirstName().isEmpty() && !editedUser.getLastName().isEmpty()) {
			userService.save(editedUser);
			
			if (currentUser.getAuthority().equals(User.ADMIN))
				return "redirect:/admin/userShow?userId=" + editedUser.getId();
			else
				return "redirect:/user/show?userId=" + editedUser.getId();
			
		} else {
			setupErrorModel(model, user);
			return "user/edit";
		}
	}
	

	private void setupErrorModel(Model model, User editedUser) {

		EnumSet<FieldTypes> errorsList = userService.getValidationErrorList(editedUser);
		String errors = "";
		for (FieldTypes fieldTypes : errorsList) {
			errors = errors + fieldTypes.toString();
		}
		model.addAttribute("userInstance", editedUser);
		model.addAttribute("errors", errors);

				
	}

	public void setupOptimisticLockErrorModel(Model model, User user) {
		model.addAttribute("optimisticLockingError", true);
		model.addAttribute("userInstance", user);
		model.addAttribute("errors", userService.getValidationErrorList(user));
	}

	@RequestMapping(value = "/user/show", method = RequestMethod.GET)
	public String getInfo(@RequestParam("userId") int userId, Model model, Principal principal) {
		logger.debug("Received request to show add page");
		
		User currentUser = userService.getUserByUsername(principal.getName());
		User user = userService.getById(userId);
		
		if(!currentUser.getId().equals(user.getId()) && !currentUser.getAuthority().equals(User.ADMIN))
		{
			// Attempted to show wrong user data
			return "redirect:/denied";			
		}
		
		model.addAttribute("loggedInUser", currentUser);
		model.addAttribute("user", user);

		return "user/show";
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UftcService getUftcService() {
		return uftcService;
	}

	public void setUftcService(UftcService uftcService) {
		this.uftcService = uftcService;
	}

	public WorkoutService getWorkoutService() {
		return workoutService;
	}

	public void setWorkoutService(WorkoutService workoutService) {
		this.workoutService = workoutService;
	}

	public UserService getUserService() {
		return userService;
	}

}
