package net.ambientia.uftc.controller;

import net.ambientia.uftc.service.ChallengeSportEventService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class ChallengeSportEventsController {
	
	private static final Logger logger = LoggerFactory
			.getLogger(ChallengeSportEventsController.class);

	
	@RequestMapping(value = "/challengeSportEvents/show", method = RequestMethod.GET)
	public String show(Model model) {
		logger.debug("Received request to show page");

		// Create new Person and add to model
		// This is the formBackingOBject
//		model.addAttribute("challengeSportEventsAttribute", new ChallengeSportEvents());

		// This will resolve to /WEB-INF/jsp/addpage.jsp
		return "challengeSportEvents/show";
	}

	public void setChallengeSportEventsService(
			ChallengeSportEventService challengeSportEventsService) {
	}

}
