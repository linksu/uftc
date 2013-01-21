package net.ambientia.uftc.controller;

import java.security.Principal;
import java.text.ParseException;
import java.util.List;

import net.ambientia.uftc.domain.Challenge;
import net.ambientia.uftc.domain.ChallengeSportEvent;
import net.ambientia.uftc.domain.PointFactorType;
import net.ambientia.uftc.domain.User;
import net.ambientia.uftc.service.ChallengeService;
import net.ambientia.uftc.service.ChallengeSportEventService;
import net.ambientia.uftc.service.UserService;

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
public class ChallengeSportEventsController {
	
	@Autowired
	private ChallengeSportEventService challengeSportEventService;
	
	@Autowired
	private ChallengeService challengeService;
	
	@Autowired
	private UserService userService;
	
	private static final Logger logger = LoggerFactory
			.getLogger(ChallengeSportEventsController.class);

	
	@RequestMapping(value = "/challengeSportEvent/show", method = RequestMethod.GET)
	public String show(@RequestParam("challengeId") int challengeId, Model model, Principal principal) {
		logger.debug("Received request to show list challenge's sportevents page");
		
		User currentUser = userService.getUserByUsername(principal.getName());
		
		List<ChallengeSportEvent> events = challengeSportEventService.getAllByChallengeId(challengeId);
		
		model.addAttribute("challengeSportEvents", events);
		model.addAttribute("challengeId", challengeId);
		model.addAttribute("loggedInUser", currentUser);
		return "challengeSportEvent/show";
	}
	
	@RequestMapping(value = "/challengeSportEvent/add", method = RequestMethod.GET)
	public String showAddNew(@RequestParam("challengeId") int challengeId, Model model, Principal principal) {
		logger.debug("Received request to show list challenge's sportevents page");
		User currentUser = userService.getUserByUsername(principal.getName());
				
		model.addAttribute("challengeSportEventInstance", new ChallengeSportEvent());
		model.addAttribute("pointFactorTypeEnum", PointFactorType.values());
		model.addAttribute("challengeId", challengeId);
		model.addAttribute("loggedInUser", currentUser);
		
		return "challengeSportEvent/add";
	}
	
	@RequestMapping(value = "/challengeSportEvent/add", method = RequestMethod.POST)
	public String addSport(@ModelAttribute("challengeSportEventInstance") ChallengeSportEvent challengeSportEvent, 
			@RequestParam("challengeId") int challengeId,
			Model model, Principal principal) {
		logger.debug("Received request to add new challenge");
		
		String currentUser = principal.getName();	
		
		
		challengeSportEventService.add(challengeId, challengeSportEvent);
		
//		if (challengeService.isValid(challenge)) {
//			challengeService.add(challenge);
//			return "redirect:/challenge/list";
//
//		} else {
//			model.addAttribute("challengeInstance", challenge);
//			model.addAttribute("errors",
//					challengeService.getValidationErrorList(challenge));
//			return "challenge/add";
//
//		}
		return "redirect:/challengeSportEvent/show?challengeId=" + challengeId;
	}
	
	@RequestMapping(value = "/challengeSportEvent/edit", method = RequestMethod.GET)
	public String edit(@RequestParam("id") int challengeSportEventId, Model model, Principal principal) {
		logger.debug("Received request to show list challenge's sportevents page");
		
		User currentUser = userService.getUserByUsername(principal.getName());
		ChallengeSportEvent challengeSportEvent = challengeSportEventService.getById(challengeSportEventId);
		
		
		model.addAttribute("challengeSportEventInstance", challengeSportEvent);
		model.addAttribute("pointFactorTypeEnum", PointFactorType.values());
		model.addAttribute("loggedInUser", currentUser);
		
		return "challengeSportEvent/edit";
	}
	
	@RequestMapping(value = "/challengeSportEvent/edit", method = RequestMethod.POST)
	public String updateChallenge(
			@ModelAttribute("challengeSportEventInstance") ChallengeSportEvent challengeSportEvent,
			Model model) throws ParseException {
			logger.debug("Received request to update challengeSportEvent");
			
		ChallengeSportEvent editedSportEvent = challengeSportEventService
				.setNewPropertiesToExistingChallenge(challengeSportEvent);
//		if (challengeSportEventService.isValid(editedSportEvent)) {
			challengeSportEventService.save(editedSportEvent);
			return "redirect:/challengeSportEvent/show?challengeId="
					+ editedSportEvent.getChallenge().getId();
//		} else {
//			setupErrorModel(model, challenge);
//			return "challengeSportEvent/show";
//		}
	}

	
	
}
