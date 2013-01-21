package net.ambientia.uftc.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.ambientia.uftc.domain.Challenge;
import net.ambientia.uftc.domain.SportEvent;
import net.ambientia.uftc.domain.Uftc;
import net.ambientia.uftc.domain.User;
import net.ambientia.uftc.domain.Workout;
import net.ambientia.uftc.service.ChallengeSportEventService;
import net.ambientia.uftc.service.SportEventService;
import net.ambientia.uftc.service.UftcService;
import net.ambientia.uftc.service.UserService;

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
public class SportEventController {
	
	@Autowired
	private UserService userService;

	@Resource(name = "sportEventService")
	private SportEventService sportEventService;

	@Resource(name = "uftcService")
	private UftcService uftcService;

	private static final Logger logger = LoggerFactory
			.getLogger(SportEventController.class);

	@RequestMapping(value = "/sportEvent/add", method = RequestMethod.GET)
	public String getAdd(Model model, Principal principal) {
		User currentUser = userService.getUserByUsername(principal.getName());
		logger.debug("Received request to show add page");
		model.addAttribute("uftcIdList", getListOfUftcIds());
		model.addAttribute("sportEventInstance", new SportEvent());
		model.addAttribute("loggedInUser", currentUser);

		return "sportEvent/add";
	}

	private List<Integer> getListOfUftcIds() {
		List<Integer> ids = new ArrayList<Integer>();
		List<Uftc> uftcList = uftcService.getAll();
		for (Uftc uftc : uftcList) {
			ids.add(uftc.getId());
		}
		return ids;
	}
	
	private List<Integer> getListOfSportEventIds(){
		List<Integer> ids = new ArrayList<Integer>();
		List<SportEvent> sportEventList = sportEventService.getAll();
		for (SportEvent sportEvent : sportEventList){
			ids.add(sportEvent.getId());
		}
		return ids;
	}

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
	
	@RequestMapping(value = "/sportEvent/list", method = RequestMethod.GET)
	public String getSportEvents(Model model, Principal principal) {
		logger.debug("Received request to show all sportEvents");
		User currentUser = userService.getUserByUsername(principal.getName());

		List<SportEvent> sportEvents = sportEventService.getAll();

		model.addAttribute("sportEvents", sportEvents);
		model.addAttribute("loggedInUser", currentUser);
		return "sportEvent/list";
	}
	
	@RequestMapping(value = "/sportEvent/edit", method = RequestMethod.GET)
	public String getEdit(@RequestParam("sportEventId") int sportEventId, Model model, Principal principal) {

		User currentUser = userService.getUserByUsername(principal.getName());
		SportEvent sportEvent = sportEventService.getById(sportEventId);
		Hibernate.initialize(sportEvent);	
		//Hibernate.initialize(user.getWorkouts());
//		List<SportEvent> sportEventList = (List<SportEvent>) sportEventService.getById(sportEventId);
		model.addAttribute("uftcIdList", getListOfUftcIds());
		model.addAttribute("sportEventInstance", getListOfSportEventIds());
		model.addAttribute("loggedInUser", currentUser);
		return "sportEvent/edit";
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

	@RequestMapping(value = "/sportEvent/delete", method = RequestMethod.GET)
	public String delete(
			@RequestParam(value = "id", required = true) SportEvent sportEvent,
			Model model, Principal principal) {
		User currentUser = userService.getUserByUsername(principal.getName());
		logger.debug("Received request to delete existing sportEvent");
		sportEventService.delete(sportEvent);
		model.addAttribute("uftcList", uftcService.getAll());
		model.addAttribute("loggedInUser", currentUser);

		return "sportEvent/list";
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
