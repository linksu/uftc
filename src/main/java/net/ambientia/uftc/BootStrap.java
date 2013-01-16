package net.ambientia.uftc;

import java.util.ArrayList;
import java.util.List;

import net.ambientia.uftc.domain.AdminUser;
import net.ambientia.uftc.domain.Challenge;
import net.ambientia.uftc.domain.ChallengeSportEvent;
import net.ambientia.uftc.domain.ChallengerUser;
import net.ambientia.uftc.domain.PointFactorType;
import net.ambientia.uftc.domain.SportEvent;
import net.ambientia.uftc.domain.Uftc;
import net.ambientia.uftc.domain.User;
import net.ambientia.uftc.domain.Workout;
import net.ambientia.uftc.service.ChallengeService;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;

@Component
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
public class BootStrap {

	private SessionFactory sessionFactory;
	private static final int ADMIN = 3;
	private static final int CHALLENGER = 2;
	private static final int USER = 1;
	
	

	
	public void runBootStrap() {

		System.out.println("BOOTSTRAP KÄYNNISTETTY");
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Uftc uftc = addUftc(session);
		addSportEvent(session, uftc);
		Challenge challenge = addChallenge(session, "BootstrapChallenge");
		ChallengeSportEvent sportEvent = addChallengeSportEvent(session,challenge,"Juoksu");
		addChallengeSportEvent(session,challenge,"Kävely");
		User user = addUser(session, "testi", ADMIN);
		User user2 = addUser(session, "testi2", CHALLENGER);
		User user3 = addUser(session, "testi3", USER);
		User user4 = addUser(session, "testi4", USER);
		User user5 = addUser(session, "testi5", USER);
		User user6 = addUser(session, "testi6", USER);
		User user7 = addUser(session, "testi7", USER);
		User user8 = addUser(session, "testi8", USER);
		User user9 = addUser(session, "testi9", USER);
		addWorkout(session,sportEvent, user3);
		session.getTransaction().commit();


		session.close();

	}
	
	
	private void addSportEvent(Session session, Uftc uftc){
		SportEvent sportEvent = new SportEvent();
		sportEvent.setPointFactor(2);
		sportEvent.setPointFactorType(PointFactorType.Minutes);
		sportEvent.setTitle("Juoksu");
		sportEvent.setUftc(uftc);
		session.save(sportEvent);	
	}
	
	private ChallengeSportEvent addChallengeSportEvent(Session session, Challenge challenge,String title){
		ChallengeSportEvent challengeSportEvent = new ChallengeSportEvent();
		challengeSportEvent.setPointFactor(2);
		challengeSportEvent.setPointFactorType(PointFactorType.Minutes);
		challengeSportEvent.setTitle(title);
		challengeSportEvent.setChallenge(challenge);
		session.save(challengeSportEvent);	
		
		return challengeSportEvent;
	}
	
	private void addWorkout(Session session, ChallengeSportEvent sportEvent, User user){
		Workout workout = new Workout();
		workout.setRepetition(2);
		workout.setUser(user);
//		workout.setName("Juoksu");
		workout.setChallengeSportEvent(sportEvent);
		
		session.save(workout);
		
	}


	private User addUser(Session session, String userName, int userLevel) {
		User user = new User();
		
		switch (userLevel) {
		case ADMIN:
			this.addAdmin(session, userName);
			break;
			
		case CHALLENGER:
			this.addChallenger(session, userName);
			break;
			
		case USER:
			user.setUsername(userName);
			user.setPassword(userName);
			user.setFirstName(userName);
			user.setLastName("tlast");
			user.setEnabled(true);
			Uftc uftc = (Uftc) session.get(Uftc.class, 1);
			user.setUftc(uftc);
			Challenge challenge = (Challenge) session.get(Challenge.class, 1);
			//user.getChallenges().add(challenge);
			session.save(user);
			uftc.getUsers().add(user);
			break;

		default:
			break;
		}
		//Authorities auth = new Authorities();
		//auth.setAuthority("ROLE_ADMIN");
		//auth.setUser(user);
		//user.getAuthorities().add(auth);
		//session.save(auth);
		
		
		
		return user;
	}
	
	private ChallengerUser addChallenger(Session session, String userName) {
		User user = new ChallengerUser();
		user.setUsername(userName);
		user.setPassword(userName);
		
		//Authorities auth = new Authorities();
		//auth.setAuthority("ROLE_ADMIN");
		//auth.setUser(user);
		//user.getAuthorities().add(auth);
		//session.save(auth);
		
		user.setFirstName(userName);
		user.setLastName("tlast");
		user.setEnabled(true);
		Uftc uftc = (Uftc) session.get(Uftc.class, 1);
		user.setUftc(uftc);
		Challenge challenge = (Challenge) session.get(Challenge.class, 1);
		//user.getChallenges().add(challenge);
		session.save(user);
		uftc.getUsers().add(user);
		
		return (ChallengerUser) user;
	}
	
	private AdminUser addAdmin(Session session, String userName) {
		User user = new AdminUser();
		user.setUsername(userName);
		user.setPassword(userName);
		
		//Authorities auth = new Authorities();
		//auth.setAuthority("ROLE_ADMIN");
		//auth.setUser(user);
		//user.getAuthorities().add(auth);
		//session.save(auth);
		
		user.setFirstName(userName);
		user.setLastName("tlast");
		user.setEnabled(true);
		Uftc uftc = (Uftc) session.get(Uftc.class, 1);
		user.setUftc(uftc);
		Challenge challenge = (Challenge) session.get(Challenge.class, 1);
		//user.getChallenges().add(challenge);
		session.save(user);
		uftc.getUsers().add(user);
		
		return (AdminUser) user;
	}
	
	private Challenge addChallenge(Session session, String challengeTitle) {
		Challenge challenge = new Challenge();
		ChallengeService challengeService = new ChallengeService();
		challenge.setTitle(challengeTitle);
		challenge.setTotalPoints(500);
		Uftc uftc = (Uftc) session.get(Uftc.class, 1);
		challenge.setUftc(uftc);
		
		uftc.getChallenges().add(challenge);
		session.save(challenge);
		
		return challenge;
		
	}


	private Uftc addUftc(Session session) {
		Uftc uftc = new Uftc();
		session.save(uftc);
		return uftc;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
