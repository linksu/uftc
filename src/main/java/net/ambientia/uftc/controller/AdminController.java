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
	
	@RequestMapping(value = "/user/add", method = RequestMethod.GET)
	public String getAdd(Model model) {
		logger.debug("Received request to show add page");
		model.addAttribute("userInstance", new User());
		return "user/add";
	}

	
	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
	public String add(@ModelAttribute("userInstance") User user, Model model) {
		logger.debug("Received request to add new user");
		Uftc uftc = uftcService.getById(1);
		userService.setUserUftc(user, uftc);
		if (userService.isValid(user)) {
			//user.setAuthority("ROLE_USER");
			userService.add(user);
			return "redirect:/";
		} else {
			setupErrorModel(model, user);
			return "user/add";
		}
	}
	
	@RequestMapping(value = "/admin/show", method = RequestMethod.GET)
	public String getUserInfo(@RequestParam("userId") int id, Model model, Principal principal) {
		logger.debug("Received request to show add page");
		
		User user = userService.getById(id);

		List<Workout> workouts = workoutService.getAllByUser(user);
		model.addAttribute("userInstance", user);
		model.addAttribute("workouts",workouts);

		return "user/show";
	}
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String admin(Locale locale, Model model) {

		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String name = auth.getName();
		User user = userService.getUserByUsername(name);
		List<SportEvent> sportEventList = sportEventService.getAll();
		List<User> userList = userService.getAll();

		model.addAttribute("sportEventInstance", new SportEvent());
		model.addAttribute("sportEventList", sportEventList);
		model.addAttribute("userInstance", user);
		model.addAttribute("pointFactorTypeEnum", PointFactorType.values());
		model.addAttribute("userList", userList);
		return "uftc/admin";
	}

	/**@RequestMapping(value = "/admin/userlist", method = RequestMethod.GET)
	public String getUsers(
			@RequestParam(value = "from", defaultValue = "0") int from,
			@RequestParam(value = "to", defaultValue = "0") int to,
			@RequestParam(value = "count", defaultValue = "3") int count,
			@RequestParam(value = "r", defaultValue = "false") boolean reversed,
			Model model) {
		logger.debug("Received request to show all users");
		List<User> users = userService.getAll();
		List<User> trimmedUserList;
		count = clamp(count,0,users.size());
		if(!reversed){
			from = clamp(from, 0, users.size());
			to = from + count;
			to = clamp(to, from, users.size());
			trimmedUserList = users.subList(from, to);
			from = from + count;
		} else {
			from = clamp(from - count, 0, users.size());
			to = from - count;
			to = clamp(to, from - count, users.size());
			trimmedUserList = users.subList(to, from);
			from = from - count;
		}
		
		from = clamp(from, 0, users.size());
		
		
		
		model.addAttribute("from", from);
		model.addAttribute("to", to);
		model.addAttribute("count", count);
		model.addAttribute("userList", trimmedUserList);
		return "uftc/userlist";
	}**/
	
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