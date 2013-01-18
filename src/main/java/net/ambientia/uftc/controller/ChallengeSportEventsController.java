package net.ambientia.uftc.controller;

import java.text.ParseException;
import java.util.List;

import net.ambientia.uftc.domain.Challenge;
import net.ambientia.uftc.domain.ChallengeSportEvent;
import net.ambientia.uftc.domain.PointFactorType;
import net.ambientia.uftc.service.ChallengeSportEventService;

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
	
	private static final Logger logger = LoggerFactory
			.getLogger(ChallengeSportEventsController.class);

	
	@RequestMapping(value = "/challengeSportEvent/show", method = RequestMethod.GET)
	public String show(@RequestParam("challengeId") int challengeId, Model model) {
		logger.debug("Received request to show list challenge's sportevents page");
		
		List<ChallengeSportEvent> events = challengeSportEventService.getAllByChallengeId(challengeId);
		
		model.addAttribute("challengeSportEvents", events);
		
		return "challengeSportEvent/show";
	}
	
	@RequestMapping(value = "/challengeSportEvent/edit", method = RequestMethod.GET)
	public String edit(@RequestParam("id") int challengeSportEventId, Model model) {
		logger.debug("Received request to show list challenge's sportevents page");
		
		ChallengeSportEvent challengeSportEvent = challengeSportEventService.getById(challengeSportEventId);
		
		
		model.addAttribute("challengeSportEventInstance", challengeSportEvent);
		model.addAttribute("pointFactorTypeEnum", PointFactorType.values());
		
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
