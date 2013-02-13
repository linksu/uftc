package net.ambientia.uftc.controller;

import java.security.Principal;
import java.text.ParseException;
import java.util.List;

import net.ambientia.uftc.domain.Challenge;
import net.ambientia.uftc.domain.User;
import net.ambientia.uftc.domain.Workout;
import net.ambientia.uftc.service.ChallengeService;
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
public class ChallengeController {

	@Autowired
	private ChallengeService challengeService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private UftcService uftcService;
	
	@Autowired
	private WorkoutService workoutService;

	private static final Logger logger = LoggerFactory
			.getLogger(ChallengeController.class);

	@RequestMapping(value = "/challenge/list", method = RequestMethod.GET)
	public String showChallengeList(Model model, Principal principal) {
		logger.debug("Received request to show challenge list page");
		
		User currentUser = userService.getUserByUsername(principal.getName());
		List<Challenge> challenges = challengeService.getAll();
		
		model.addAttribute("challenges", challenges);
		model.addAttribute("userChallenges", currentUser.getChallenges());
		model.addAttribute("loggedInUser", currentUser);
		return "challenge/list";
	}
	
	@RequestMapping(value = "/challenge/add", method = RequestMethod.GET)
	public String showChallengeAdd(Model model, Principal principal) {
		logger.debug("Received request to show challenge add page");
		
		User currentUser = userService.getUserByUsername(principal.getName());

		model.addAttribute("challengeInstance", new Challenge());
		model.addAttribute("loggedInUser", currentUser);
		
		return "challenge/add";
	}

	@RequestMapping(value = "/challenge/add", method = RequestMethod.POST)
	public String addChallenge(@ModelAttribute("challengeInstance") Challenge challenge,
			Model model, Principal principal) {
		logger.debug("Received request to add new challenge");
		
		User currentUser = userService.getUserByUsername(principal.getName());
		
		challenge.setOwner(currentUser);
		
		if (challengeService.isValid(challenge)) {
			challengeService.add(challenge);
			return "redirect:/challenge/list";

		} else {
			model.addAttribute("challengeInstance", challenge);
			model.addAttribute("errors",
					challengeService.getValidationErrorList(challenge));
			return "challenge/add";

		}
	}
	
	@RequestMapping(value = "/challenge/edit", method = RequestMethod.GET)
			public String showChallengeEdit(@RequestParam("challengeId") int challengeId,
					Model model, Principal principal) {
		logger.debug("Received request to show challenge edit page");
		
		Challenge challenge = challengeService.getById(challengeId);
		User currentUser = userService.getUserByUsername(principal.getName());
		
		if (!currentUser.getId().equals(challenge.getOwner().getId()) && !currentUser.getAuthority().equals(User.ADMIN)) {
			// Attempted to show wrong challenge data
			return "redirect:/challenge/show?challengeId=" + challengeId;
		}
		
		model.addAttribute("challengeInstance", challenge);
		model.addAttribute("loggedInUser", currentUser);
		
		return "challenge/edit";
	}

	@RequestMapping(value = "/challenge/update", method = RequestMethod.POST)
	public String updateChallenge(
			@ModelAttribute("challengeInstance") Challenge challenge,
			Model model, Principal principal) throws ParseException {
		logger.debug("Received request to update challenge");
		
		User currentUser = userService.getUserByUsername(principal.getName());
		Challenge oldChallenge = challengeService.getById(challenge.getId());
		
		if(!currentUser.getId().equals(oldChallenge.getOwner().getId()) && !currentUser.getAuthority().equals(User.ADMIN)) {
			// Attempted to edit wrong challenge data
			return "redirect:/challenge/show?challengeId=" + challenge.getId();
		}
		
		oldChallenge = null;
		
		if (challengeService.entityIsLocked(challenge)) {
			setupOptimisticLockErrorModel(model, challenge);
			return "challenge/edit";
		}
				
		Challenge editedChallenge = challengeService
				.setNewPropertiesToExistingChallenge(challenge);
		
		if (!challengeService.isValid(editedChallenge)) {
			challengeService.save(editedChallenge);
			return "redirect:/challenge/show?challengeId="
					+ editedChallenge.getId();
		} else {
			setupErrorModel(model, challenge);
			return "challenge/edit";
		}
	}

	@RequestMapping(value = "/challenge/show", method = RequestMethod.GET)
	public String showChallenge(@RequestParam("challengeId") int challengeId,
			Model model, Principal principal) {
		logger.debug("Received request to show challenge info page");

		User currentUser = userService.getUserByUsername(principal.getName());
		
		Challenge challenge = challengeService.getById(challengeId);
		List<User> challengeUsers = challengeService.getUsers(challenge);
		List<User> notApprovedUsers = challengeService.getUsersWaitingForApproval(challenge);
		
		if(currentUser.getId().equals(challenge.getOwner().getId()) || currentUser.getAuthority().equals(User.ADMIN)) {
			model.addAttribute("challengeOwner", true);
		}
		
		if(challengeService.challengeContainsUser(challenge, currentUser)) {
			model.addAttribute("challengeParticipant", true);
			List<Workout> workouts = workoutService.getAllByUserAndChallenge(currentUser, challenge);
			model.addAttribute("workouts",workouts);
		}
		
		if (challengeService.challengeContainsAwaitingUser(challenge, currentUser)) {
			model.addAttribute("awaitingParticipant", true);
		}
		
		
		model.addAttribute("usersWithPoints", workoutService.calculateUsersTotalPoints(challengeUsers, challenge));
		model.addAttribute("challenge", challenge);
		model.addAttribute("challengeUsers", challengeUsers);
		model.addAttribute("notApprovedUsers", notApprovedUsers);
		model.addAttribute("loggedInUser", currentUser);
		
		return "challenge/show";
	}
	
	@RequestMapping (value = "/challenge/userWorkout", method = RequestMethod.GET)
	public String showUserWorkout(@RequestParam("challengeId") int challengeId, @RequestParam("userId") int id,
			Model model, Principal principal){
		logger.debug("Received request to show user workout page");
		
		User user = userService.getById(id);
		User currentUser = userService.getUserByUsername(principal.getName());
		Challenge challenge = challengeService.getById(challengeId);
		
		if(challengeService.challengeContainsUser(challenge, currentUser) || currentUser.getAuthority().equals(User.ADMIN)) {
			model.addAttribute("user", user);
			List<Workout> workouts = workoutService.getAllByUserAndChallenge(user, challenge);
			model.addAttribute("workouts",workouts);
		}else{
			return "redirect:/challenge/show?challengeId=" + challengeId;
		}
		
		model.addAttribute("loggedInUser", currentUser);
		model.addAttribute("challengeInstance", challenge);

		return "challenge/userWorkout";
	}
	

	@RequestMapping(value = "/challenge/join", method = RequestMethod.GET)
	public String askToJoinToChallenge(@RequestParam("challengeId") int challengeId, Model model,
			Principal principal) {
		logger.debug("Received request to join challenge");
		
		User currentUser = userService.getUserByUsername(principal.getName());
		Challenge challenge = challengeService.getById(challengeId);
		
		if(currentUser.getAuthority().equals(User.ADMIN)) {
			return "redirect:/denied";
		}

		if (!challengeService.challengeContainsUser(challenge, currentUser) && !challengeService.challengeContainsAwaitingUser(challenge, currentUser)) {
			challenge.getNotApprovedUsers().add(currentUser);
			challengeService.save(challenge);
		}

		return "redirect:/challenge/show?challengeId=" + challengeId;
	}
	
	@RequestMapping(value = "/challenge/accept", method = RequestMethod.GET)
	public String acceptUserToChallenge(@RequestParam("challengeId") int challengeId, 
			@RequestParam("userId") int userId, Model model,
			Principal principal) {
		logger.debug("Received request to accept user to challenge");
		
		User currentUser = userService.getUserByUsername(principal.getName());
		Challenge challenge = challengeService.getById(challengeId);
		User acceptedUser = userService.getById(userId);

		if (currentUser.getId().equals(challenge.getOwner().getId())
				&& !challengeService.challengeContainsUser(challenge, acceptedUser)
				&& challengeService.challengeContainsAwaitingUser(challenge, acceptedUser)) {
			
			challengeService.removeChallengeAwaitingUser(challenge, acceptedUser);
			challenge.getUsers().add(acceptedUser);

			challengeService.save(challenge);
		}

		return "redirect:/challenge/show?challengeId=" + challengeId;
	}

	public void setupOptimisticLockErrorModel(Model model, Challenge challenge) {
		model.addAttribute("optimisticLockingError", true);
		model.addAttribute("challengeInstance", challenge);
		model.addAttribute("errors",
				challengeService.getValidationErrorList(challenge));
	}

	public void setupErrorModel(Model model, Challenge editedChallenge) {
		model.addAttribute("challengeInstance", editedChallenge);
		model.addAttribute("errors",
				challengeService.getValidationErrorList(editedChallenge));
	}
}
