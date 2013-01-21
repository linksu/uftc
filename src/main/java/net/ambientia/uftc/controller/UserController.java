package net.ambientia.uftc.controller;

import java.security.Principal;
import java.util.EnumSet;
import java.util.List;

import javax.annotation.Resource;

import net.ambientia.uftc.domain.Uftc;
import net.ambientia.uftc.domain.User;
import net.ambientia.uftc.domain.User.FieldTypes;
import net.ambientia.uftc.domain.Workout;
import net.ambientia.uftc.service.ChallengeSportEventService;
import net.ambientia.uftc.service.UftcService;
import net.ambientia.uftc.service.UserService;
import net.ambientia.uftc.service.WorkoutService;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "uftcService")
	private UftcService uftcService;

	@Resource(name = "workoutService")
	private WorkoutService workoutService;
	
	@Resource(name = "challengeSportEventService")
	private ChallengeSportEventService challengeSportEventService;

	private static final Logger logger = LoggerFactory
			.getLogger(UserController.class);
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String getAdd(Model model, Principal principal) {
		User currentUser = userService.getUserByUsername(principal.getName());
		logger.debug("Received request to show register page");
		model.addAttribute("userInstance", new User());
		model.addAttribute("loggedInUser", currentUser);
		return "uftc/register";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String add(@ModelAttribute("userInstance") User user, Model model) {
		logger.debug("Received request to add new user");
		Uftc uftc = uftcService.getById(1);
		userService.setUserUftc(user, uftc);
		if (userService.isValid(user)) {
			//user.setAuthority("ROLE_USER");
			if(user.getAuthority().equals("ROLE_CHALLENGER")) {
				user.setEnabled(false);
			}
			userService.add(user);
			return "redirect:/";
		} else {
			setupErrorModel(model, user);
			return "uftc/register";
		}
	}

	@RequestMapping(value = "/user/list", method = RequestMethod.GET)
	public String getUsers(Model model, Principal principal) {
		logger.debug("Received request to show all users");
		User currentUser = userService.getUserByUsername(principal.getName());
		
		List<User> users = userService.getAll();

		model.addAttribute("users", users);
		model.addAttribute("loggedInUser", currentUser);
		return "user/list";
	}

	@RequestMapping(value = "/user/edit", method = RequestMethod.GET)
	public String edit(@RequestParam("userId") int userId, Model model, Principal principal) {
		
		User currentUser = userService.getUserByUsername(principal.getName());
		User user = userService.getById(userId);
		Hibernate.initialize(user);
		// Hibernate.initialize(user.getWorkouts());
		// List<Workout> workoutList = userService.getWorkouts(user);
		List<Workout> workoutList = workoutService.getAllByUser(user);
		model.addAttribute("loggedInUser", currentUser);
		model.addAttribute("workoutList", workoutList);
		return "user/edit";
	}

	@RequestMapping(value = "/user/edit", method = RequestMethod.POST)
	public String update(@ModelAttribute("userInstance") User user, Model model) {
		logger.debug("Received request to update user");
		if(userService.entityIsLocked(user)){
			setupOptimisticLockErrorModel(model, user);
			return "user/edit";
		}
		User editedUser = userService.setNewPropertiesToExistingUser(user);
		if (!editedUser.getFirstName().isEmpty() && !editedUser.getLastName().isEmpty()) {
			userService.save(editedUser);
			return "redirect:/user/edit?userId=" + editedUser.getId();
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

	private boolean isEntityIsLocked(User editedUser) {
		User persistentUser = userService.getById(editedUser.getId());
		if (persistentUser.getVersion() > editedUser.getVersion())
			return true;
		return false;
	}

	@RequestMapping(value = "/user/show", method = RequestMethod.GET)
	public String getInfo(@RequestParam("userId") int id, Model model, Principal principal) {
		logger.debug("Received request to show add page");
		
		User currentUser = userService.getUserByUsername(principal.getName());
		
		if(currentUser == null || !currentUser.equals(currentUser.getUsername()))
		{
			// Attempted to show wrong user data
			return "redirect:/";			
		}
		List<Workout> workouts = workoutService.getAllByUser(currentUser);
		model.addAttribute("loggedInUser", currentUser);
		model.addAttribute("workouts",workouts);

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
