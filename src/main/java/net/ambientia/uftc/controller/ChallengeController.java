package net.ambientia.uftc.controller;

import java.security.Principal;
import java.text.ParseException;
import java.util.List;

import net.ambientia.uftc.dao.UserDao;
import net.ambientia.uftc.domain.Challenge;
import net.ambientia.uftc.domain.Uftc;
import net.ambientia.uftc.domain.User;
import net.ambientia.uftc.service.ChallengeService;
import net.ambientia.uftc.service.UftcService;

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
public class ChallengeController {

	@Autowired
	private ChallengeService challengeService;

	@Autowired
	private UftcService uftcService;

	@Autowired
	private UserDao userDao;

	private Challenge challenge;

	private static final Logger logger = LoggerFactory
			.getLogger(ChallengeController.class);

	@RequestMapping(value = "/challenge/list", method = RequestMethod.GET)
	public String getAdd(Model model) {
		logger.debug("Received request to list add page");

		List<Challenge> challenges = challengeService.getAll();
		model.addAttribute("challengeInstance", new Challenge());
		model.addAttribute("challenges", challenges);
		return "challenge/list";
	}

	@RequestMapping(value = "/challenge/list", method = RequestMethod.POST)
	public String add(@ModelAttribute("challengeInstance") Challenge challenge,
			Model model) {

		logger.debug("Received request to add new challenge");
		if (challengeService.isValid(challenge)) {
			challengeService.add(challenge);
			return "redirect:/challenge/list";

		} else {
			model.addAttribute("challengeInstance", challenge);
			model.addAttribute("errors",
					challengeService.getValidationErrorList(challenge));
			return "challenge/list";

		}
	}

	@RequestMapping(value = "/challenge/edit", method = RequestMethod.GET)
	public String edit(@RequestParam("challengeId") int challengeId, Model model) {

		challenge = challengeService.getById(challengeId);
		Hibernate.initialize(challenge);
		model.addAttribute("challengeInstance", challenge);

		return "challenge/edit";
	}

	@RequestMapping(value = "/challenge/update", method = RequestMethod.POST)
	public String update(
			@ModelAttribute("challengeInstance") Challenge challenge,
			Model model) throws ParseException {
		logger.debug("Received request to update user");
		if (challengeService.entityIsLocked(challenge)) {
			setupOptimisticLockErrorModel(model, challenge);
			return "challenge/edit";
		}
		Challenge editedChallenge = challengeService
				.setNewPropertiesToExistingChallenge(challenge);
		if (challengeService.isValid(editedChallenge)) {
			challengeService.save(editedChallenge);
			return "redirect:/challenge/info?challengeId="
					+ editedChallenge.getId();
		} else {
			setupErrorModel(model, challenge);
			return "challenge/edit";
		}
	}

	@RequestMapping(value = "/challenge/show", method = RequestMethod.GET)
	public String getChallenge(@RequestParam("challengeId") int challengeId,
			Model model, Principal principal) {
		logger.debug("Received request to list all challenges");

		Challenge challenge = challengeService.getById(challengeId);
		List<User> challengeUsers = challengeService.getUsers(challenge);
		List<User> allUsers = userDao.getAll();
		String username = principal.getName();

		User currentUser = null;
		for (User user : allUsers) {
			if (user.getUsername().equals(username))
				currentUser = user;
		}

		model.addAttribute("challengeInstance", challenge);
		model.addAttribute("challengeUsers", challengeUsers);
		model.addAttribute("user", currentUser);
		return "challenge/show";
	}

	@RequestMapping(value = "/challenge/join", method = RequestMethod.POST)
	public String addUserToChallenge(@RequestParam("userId") int userId,
			@RequestParam("challengeId") int challengeId, Model model,
			Principal principal) {
		User user = userDao.getById(userId);
		Challenge challenge = challengeService.getById(challengeId);

		if (user.getUsername().equals(principal.getName())
				&& challengeService.challengeContainsUser(challenge, user) == false) {
			challenge.getUsers().add(user);
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

	public void setChallengeService(ChallengeService challengeService) {
		this.challengeService = challengeService;
	}

	public UftcService getUftcService() {
		return uftcService;
	}

	public void setUftcService(UftcService uftcService) {
		this.uftcService = uftcService;
	}

	public Challenge getChallenge() {
		return challenge;
	}

	public void setChallenge(Challenge challenge) {
		this.challenge = challenge;
	}

}
