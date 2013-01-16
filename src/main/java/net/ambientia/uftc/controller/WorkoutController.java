package net.ambientia.uftc.controller;

import java.security.Principal;
import java.util.List;

import javax.annotation.Resource;

import net.ambientia.uftc.domain.Challenge;
import net.ambientia.uftc.domain.ChallengeSportEvent;
import net.ambientia.uftc.domain.PointFactorType;
import net.ambientia.uftc.domain.User;
import net.ambientia.uftc.domain.Workout;
import net.ambientia.uftc.service.ChallengeService;
import net.ambientia.uftc.service.ChallengeSportEventService;
import net.ambientia.uftc.service.UserService;
import net.ambientia.uftc.service.WorkoutService;

import org.hibernate.Hibernate;
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
public class WorkoutController {

	@Resource(name = "workoutService")
	private WorkoutService workoutService;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "challengeSportEventsService")
	private ChallengeSportEventService challengeSportEventsService;
	
	@Autowired
	private ChallengeService challengeService;

	private static final Logger logger = LoggerFactory
			.getLogger(WorkoutController.class);

	@RequestMapping(value = "/workout/add", method = RequestMethod.GET)
	public String getAdd(@RequestParam("challengeId") Integer challengeId, Model model,
			Principal principal) {
		logger.debug("Received request to show add new workout page");

		String currentUser = principal.getName();
		User user = userService.getUserByUsername(currentUser);
		if (user == null || !currentUser.equals(user.getUsername()) || challengeId < 0) {
			// Attempted to show wrong user data
			return "redirect:/";
		}
				
		model.addAttribute("workoutInstance", new Workout());
		model.addAttribute("userInstance", user);
		model.addAttribute("challengeSportEventsList",
				challengeService.getById(challengeId).getChallengeSportEvents());
		model.addAttribute("pointFactorTypeEnum", PointFactorType.values());

		return "workout/add";
	}

	private List<ChallengeSportEvent> getChallengeSportEvents() {
		List<ChallengeSportEvent> challengeSportEvents = challengeSportEventsService
				.getAll();
		return challengeSportEvents;
	}

	@RequestMapping(value = "/workout/add", method = RequestMethod.POST)
	public String add(@ModelAttribute("workoutInstance") Workout workout, Principal principal, Model model) {
		logger.debug("Received request to add new workout");

		String currentUser = principal.getName();
		User user = userService.getUserByUsername(currentUser);
		workout.setUser(user);

		if (workoutService.isValid(workout)) {
			workoutService.add(user.getId(), workout);
			return "redirect:/user/show?userId=" + user.getId();
		} else {
			setupErrorModel(model, workout);
			return "workout/add";
		}
	}

	@RequestMapping(value = "/workout/edit", method = RequestMethod.GET)
	public String getEdit(@RequestParam("workoutId") int workoutId,
			Model model, Principal principal) {

		String currentUser = principal.getName();
		User user = workoutService.getById(workoutId).getUser();
		if (user == null || !currentUser.equals(user.getUsername())) {
			// Attempted to show wrong user data
			return "redirect:/";
		} else {
			Workout workout = workoutService.getById(workoutId);
			Hibernate.initialize(workoutId);
			model.addAttribute("workoutInstance", workout);
			model.addAttribute("challengeSportEventsList",
					getChallengeSportEvents());
			model.addAttribute("pointFactorTypeEnum", PointFactorType.values());
			model.addAttribute("userInstance", user);
			return "workout/edit";
		}
	}

	@RequestMapping(value = "/workout/edit", method = RequestMethod.POST)
	public String update(@ModelAttribute("workoutInstance") Workout workout,
			Model model) {
		
		logger.debug("Received request to update workout");
		if (workoutService.entityIsLocked(workout)) {
			setupOptimisticLockErrorModel(model, workout);
			return "workout/edit";
		}
//		workout.setName(challengeSportEventsService.getById(
//				workout.getChallengeSportEventId()).getTitle());
		Workout editedWorkout = workoutService
				.setNewPropertiesToExistingWorkout(workout);
		if (workoutService.isValid(editedWorkout)) {
			User user = workoutService.getById(workout.getId()).getUser();
			workout.setUser(user);
			workoutService.save(editedWorkout);
			return "redirect:/user/show?userId=" + user.getId();
		} else {
			setupErrorModel(model, workout);
			return "workout/edit";
		}
	}

	@RequestMapping(value = "/workout/list", method = RequestMethod.GET)
	public String getWorkouts(Model model) {
		logger.debug("Received request to show all workouts");

		List<Workout> workouts = workoutService.getAll();

		model.addAttribute("workouts", workouts);
		return "workout/list";
	}

	@RequestMapping(value = "/workout/info", method = RequestMethod.GET)
	public String getInfo(@RequestParam("workoutId") int id, Model model) {
		logger.debug("Received request to show add page");
		Workout workout = workoutService.getById(id);

		model.addAttribute("workoutInstance", workout);

		return "workout/info";
	}

	public void setWorkoutService(WorkoutService workoutService) {
		this.workoutService = workoutService;
	}

	public void setupErrorModel(Model model, Workout editedWorkout) {
		model.addAttribute("workoutInstance", editedWorkout);
		model.addAttribute("errors",
				workoutService.getValidationErrorList(editedWorkout));
	}

	public void setupOptimisticLockErrorModel(Model model, Workout workout) {
		model.addAttribute("optimisticLockingError", true);
		model.addAttribute("workoutInstance", workout);
		model.addAttribute("errors",
				workoutService.getValidationErrorList(workout));
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public WorkoutService getWorkoutService() {
		return workoutService;
	}

	public ChallengeSportEventService getChallengeSportEventsService() {
		return challengeSportEventsService;
	}

	public void setChallengeSportEventsService(
			ChallengeSportEventService challengeSportEventsService) {
		this.challengeSportEventsService = challengeSportEventsService;
	}
}
