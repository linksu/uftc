package net.ambientia.uftc;

import java.util.ArrayList;
import java.util.List;

import net.ambientia.uftc.domain.Authorities;
import net.ambientia.uftc.domain.Challenge;
import net.ambientia.uftc.domain.ChallengeSportEvent;
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

	
	

	
	public void runBootStrap() {

		System.out.println("BOOTSTRAP K�YNNISTETTY");
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Uftc uftc = addUftc(session);
		addSportEvent(session, uftc);
		Challenge challenge = addChallenge(session, "BootstrapChallenge");
		addChallengeSportEvent(session,challenge,"Juoksu");
		addChallengeSportEvent(session,challenge,"K�vely");
		User user = addUser(session, "testi");
		User user2 = addUser(session, "testi2");
		User user3 = addUser(session, "testi3");
		User user4 = addUser(session, "testi4");
		User user5 = addUser(session, "testi5");
		User user6 = addUser(session, "testi6");
		User user7 = addUser(session, "testi7");
		User user8 = addUser(session, "testi8");
		User user9 = addUser(session, "testi9");
		addWorkout(session, user);
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
	
	private void addChallengeSportEvent(Session session, Challenge challenge,String title){
		ChallengeSportEvent challengeSportEvent = new ChallengeSportEvent();
		challengeSportEvent.setPointFactor(2);
		challengeSportEvent.setPointFactorType(PointFactorType.Minutes);
		challengeSportEvent.setTitle(title);
		challengeSportEvent.setChallenge(challenge);
		session.save(challengeSportEvent);	
	}
	
	private void addWorkout(Session session, User user){
		Workout workout = new Workout();
		workout.setRepetition(2);
		workout.setUser(user);
		workout.setName("Juoksu");
		workout.setChallengeSportEventId(1);
		
		session.save(workout);
		
	}


	private User addUser(Session session, String userName) {
		User user = new User();
		user.setUsername(userName);
		user.setPassword(userName);
		user.setAuthority("ROLE_ADMIN");
		
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
		
		return user;
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
