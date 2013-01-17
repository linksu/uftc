package net.ambientia.uftc.controller;

import java.util.List;

import net.ambientia.uftc.domain.ChallengeSportEvent;
import net.ambientia.uftc.service.ChallengeSportEventService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
		
		return "challengeSportEvents/show";
	}

	public void setChallengeSportEventsService(
			ChallengeSportEventService challengeSportEventsService) {
	}

}
