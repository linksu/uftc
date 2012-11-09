package net.ambientia.uftc.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import net.ambientia.uftc.dao.ChallengeDao;
import net.ambientia.uftc.dao.UftcDao;
import net.ambientia.uftc.domain.Challenge;
import net.ambientia.uftc.domain.ChallengeSportEvent;
import net.ambientia.uftc.domain.SportEvent;
import net.ambientia.uftc.domain.Uftc;
import net.ambientia.uftc.domain.User;
import net.ambientia.uftc.domain.Challenge.FieldTypes;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("challengeService")
@Transactional
public class ChallengeService {

	@Autowired
	private ChallengeDao challengeDao;

	@Autowired
	private UftcDao uftcDao;

	private SessionFactory sessionFactory;

	@Transactional(propagation = Propagation.NEVER)
	public List<Challenge> getAll() {
		return challengeDao.getAll();
	}

	@Transactional(propagation = Propagation.NEVER)
	public Challenge getById(int challengeId) {
		return challengeDao.getById(challengeId);

	}

	public void add(Challenge challenge) {
		Uftc uftc = uftcDao.getById(1);
		challenge.setUftc(uftc);
		addDefaultSportEvents(challenge);
		challengeDao.add(1, challenge);
	}

	public void delete(Challenge challenge) {
		challengeDao.delete(challenge);
	}

	public void save(Challenge challenge) {
		challengeDao.save(challenge);
	}

	public Challenge setNewPropertiesToExistingChallenge(Challenge challenge)
			throws ParseException {
		Challenge persistedChallenge = getById(challenge.getId());
		persistedChallenge.setTitle(challenge.getTitle());
		persistedChallenge.setStartTime(challenge.getStartTimeString());
		persistedChallenge.setEndTime(challenge.getEndTimeString());
		persistedChallenge.setUftc(challenge.getUftc());
		persistedChallenge.setChallengeSportEvents(challenge
				.getChallengeSportEvents());
		return persistedChallenge;
	}

	public void setChallengeDao(ChallengeDao challenge) {
		this.challengeDao = challenge;
	}

	public boolean isValid(Challenge challenge) {
		return challenge.validate().size() > 0;
	}

	public EnumSet<FieldTypes> getValidationErrorList(Challenge challenge) {
		return challenge.validate();
	}

	public boolean entityIsLocked(Challenge challenge) {
		return challengeDao.entityIsLocked(challenge);
	}

	public void addDefaultSportEvents(Challenge challenge) {
		// Hibernate.initialize(challenge);

		Uftc uftc = uftcDao.getById(challenge.getUftc().getId());
		// Hibernate.initialize(uftc);
		List<SportEvent> sportEvents = uftc.getSportEvents();
		List<ChallengeSportEvent> challengeSportEvents = new ArrayList<ChallengeSportEvent>();

		for (SportEvent sportEvent : sportEvents) {
			ChallengeSportEvent challengeSportEvent = new ChallengeSportEvent();
			challengeSportEvent.setChallenge(challenge);
			challengeSportEvent.setPointFactor(sportEvent.getPointFactor());
			challengeSportEvent.setPointFactorType(sportEvent
					.getPointFactorType());
			challengeSportEvent.setTitle(sportEvent.getTitle());
			challengeSportEvents.add(challengeSportEvent);
		}
		challenge.setChallengeSportEvents(challengeSportEvents);

	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public List getUsers(Challenge challenge) {
		Session session = challengeDao.getCurrentSession();
		return challenge.getUsers();
	}

	public boolean challengeContainsUser(Challenge challenge, User user) {
		List<User> userList = challenge.getUsers();
		for (User myUser : userList) {
			if (Integer.valueOf(myUser.getId())== Integer.valueOf(user.getId()))
				return true;
		}
		return false;
	}

}
