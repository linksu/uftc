package net.ambientia.uftc.controller;

import net.ambientia.uftc.domain.SportEvent;
import net.ambientia.uftc.domain.Uftc;
import net.ambientia.uftc.service.SportEventService;
import net.ambientia.uftc.service.UftcService;
import net.ambientia.uftc.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SportEventController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private SportEventService sportEventService;

	@Autowired
	private UftcService uftcService;

	private static final Logger logger = LoggerFactory
			.getLogger(SportEventController.class);

	@RequestMapping(value = "/sportEvent/add", method = RequestMethod.POST)
	public String add(
			@ModelAttribute("sportEventInstance") SportEvent sportEvent,
			Model model) {
		logger.debug("Received request to add new sportEvent");
	
		Uftc uftc = uftcService.getById(1);
		sportEventService.setSportEventUftc(sportEvent, uftc);

		if (sportEventService.isValid(sportEvent)) {	
			sportEventService.add(sportEvent);
			//return "redirect:/sportEvent/list";
			return "redirect:/admin";					
		} else {
			setupErrorModel(model,sportEvent);
			model.addAttribute("sportEventInstance", sportEvent);
			model.addAttribute("errors",
					sportEventService.getValidationErrorList(sportEvent));
			return "sportEvent/add";
		}
	}

	@RequestMapping(value = "/sportEvent/edit", method = RequestMethod.POST)
	public String update(@ModelAttribute("sportEventInstance") SportEvent sportEvent, Model model) {
		logger.debug("Received request to update sportEvent");
		if(isEntityIsLocked(sportEvent)){
			setupOptimisticLockErrorModel(model, sportEvent);
			return "/admin";
		}
		SportEvent editedSportEvent = sportEventService.setNewPropertiesToExistingSportEvent(sportEvent);
		if(sportEventService.isValid(editedSportEvent)){
			sportEventService.save(editedSportEvent);
			//return "redirect:/sportEvent/info?sportEventId=" + editedSportEvent.getId();
			return "redirect:/admin";
		}else{
			setupErrorModel(model,sportEvent);
			//return "sportEvent/edit";
			return "/admin";
		}
	}
	
	private void setupErrorModel(Model model, SportEvent editedSportEvent) {
		model.addAttribute("sportEventInstance", editedSportEvent);
		model.addAttribute("errors",
				sportEventService.getValidationErrorList(editedSportEvent));		
	}

	private void setupOptimisticLockErrorModel(Model model, SportEvent sportEvent) {
		model.addAttribute("optimisticLockingError", true);
		model.addAttribute("sportEventInstance", sportEvent);
		model.addAttribute("errors",sportEventService.getValidationErrorList(sportEvent));				
	}

	private boolean isEntityIsLocked(SportEvent editedSportEvent) {
		SportEvent persistentSportEvent = sportEventService.getById(editedSportEvent.getId());
		if (persistentSportEvent.getVersion() > editedSportEvent.getVersion())
			return true;
		return false;
	}

	public void setSportEventService(SportEventService sportEventService) {
		this.sportEventService = sportEventService;
	}
	
	public UftcService getUftcService() {
		return uftcService;
	}

	public void setUftcService(UftcService uftcService) {
		this.uftcService = uftcService;
	}

}
