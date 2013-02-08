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

	@Autowired
	private WorkoutService workoutService;

	@Autowired
	private UserService userService;

	@Autowired
	private ChallengeSportEventService challengeSportEventService;
	
	@Autowired
	private ChallengeService challengeService;

	private static final Logger logger = LoggerFactory
			.getLogger(WorkoutController.class);

	@RequestMapping(value = "/workout/add", method = RequestMethod.GET)
	public String getAdd(@RequestParam("challengeId") Integer challengeId, Model model,
			Principal principal) {
		logger.debug("Received request to show add new workout page");

		User currentUser = userService.getUserByUsername(principal.getName());
		Challenge challenge = challengeService.getById(challengeId);
		
		if (!challengeService.challengeContainsUser(challenge, currentUser) || challengeId < 0) {
			// Attempted to show wrong user data
			return "redirect:/denied";
		}
				
		model.addAttribute("workoutInstance", new Workout());
		model.addAttribute("loggedInUser", currentUser);
		model.addAttribute("challengeSportEventsList",
				challenge.getChallengeSportEvents());
		model.addAttribute("pointFactorTypeEnum", PointFactorType.values());

		return "workout/add";
	}

	@RequestMapping(value = "/workout/add", method = RequestMethod.POST)
	public String add(@ModelAttribute("workoutInstance") Workout workout, Principal principal, Model model) {
		logger.debug("Received request to add new workout");

		User currentUser = userService.getUserByUsername(principal.getName());
		ChallengeSportEvent challengeSportEvent = challengeSportEventService.getById(workout.getChallengeSportEventId());
		Challenge challenge = challengeSportEvent.getChallenge();
		
		if (!challengeService.challengeContainsUser(challenge, currentUser) 
				|| challenge.getId() < 0) {
			// Attempted to show wrong user data
			return "redirect:/denied";
		}
		
		workout.setUser(currentUser);
		workout.setChallengeSportEvent(challengeSportEvent);

		if (workoutService.isValid(workout)) {
			workoutService.add(currentUser.getId(), workout);
			return "redirect:/challenge/show?challengeId=" + workout.getChallengeSportEvent().getChallenge().getId();
		} else {
			setupErrorModel(model, workout);
			return "workout/add";
		}
	}

	@RequestMapping(value = "/workout/edit", method = RequestMethod.GET)
	public String getEdit(@RequestParam("workoutId") int workoutId,
			Model model, Principal principal) {
		logger.debug("Received request to update workout page");
		
		User currentUser = userService.getUserByUsername(principal.getName());
		Workout workout = workoutService.getById(workoutId);
		Challenge challenge = workout.getChallengeSportEvent().getChallenge();
		
		if (!currentUser.getId().equals(workout.getUser().getId()) || !challengeService.challengeContainsUser(challenge, currentUser)) {
			// Attempted to edit wrong user data
			return "redirect:/denied";
		}
			
		model.addAttribute("workoutInstance", workout);
		model.addAttribute("challengeSportEventsList",
				challengeSportEventService.getAllByChallengeId(workout.getChallengeSportEvent().getChallenge().getId()));
		model.addAttribute("pointFactorTypeEnum", PointFactorType.values());
		model.addAttribute("loggedInUser", currentUser);

		return "workout/edit";
	}

	@RequestMapping(value = "/workout/edit", method = RequestMethod.POST)
	public String update(@ModelAttribute("workoutInstance") Workout workout,
			Model model, Principal principal) {
		logger.debug("Received request to update workout");
		
		User currentUser = userService.getUserByUsername(principal.getName());
		Workout oldWorkout = workoutService.getById(workout.getId());
		Challenge challenge = oldWorkout.getChallengeSportEvent().getChallenge();
	
		if(!currentUser.getId().equals(oldWorkout.getUser().getId()) || !challengeService.challengeContainsUser(challenge, currentUser)) {
			// Attempted to edit wrong challenge data
			return "redirect:/denied";
		}
		
		oldWorkout = null;
		
		if (workoutService.entityIsLocked(workout)) {
			setupOptimisticLockErrorModel(model, workout);
			return "workout/edit";
		}

		Workout editedWorkout = workoutService
				.setNewPropertiesToExistingWorkout(workout);
		if (workoutService.isValid(editedWorkout)) {
			workout.setUser(currentUser);
			workoutService.edit(editedWorkout);
			return "redirect:/challenge/show?challengeId=" + editedWorkout.getChallengeSportEvent().getChallenge().getId();
		} else {
			setupErrorModel(model, workout);
			return "workout/edit";
		}
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
		return challengeSportEventService;
	}

	public void setChallengeSportEventsService(
			ChallengeSportEventService challengeSportEventsService) {
		this.challengeSportEventService = challengeSportEventsService;
	}
}
