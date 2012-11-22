package net.ambientia.uftc;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import net.ambientia.uftc.dao.ChallengeDao;
import net.ambientia.uftc.domain.Challenge;
import net.ambientia.uftc.domain.PointFactorType;
import net.ambientia.uftc.domain.SportEvent;
import net.ambientia.uftc.domain.User;
import net.ambientia.uftc.service.ChallengeService;
import net.ambientia.uftc.service.SportEventService;
import net.ambientia.uftc.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	@Autowired
	private ChallengeService challengeService;

	@Autowired
	private UserService userService;

	@Autowired
	private SportEventService sportEventService;

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {

		List<Challenge> challengeList = challengeService.getAll();

		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String name = auth.getName();
		User user = userService.getUserByUsername(name);
		List<User> userList = userService.getAll();
		model.addAttribute("challengeList", challengeList);
		model.addAttribute("userInstance", user);

		return "uftc/UFTC";
	}

	@RequestMapping(value = "/denied", method = RequestMethod.GET)
	public String denied(Locale locale, Model model) {

		return "uftc/denied";
	}
	public int clamp(int i, int low, int high){
		return Math.max(Math.min(i, high), low);
	}

}
