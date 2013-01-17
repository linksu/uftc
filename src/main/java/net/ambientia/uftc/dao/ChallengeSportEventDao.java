package net.ambientia.uftc.dao;

import java.util.List;

import net.ambientia.uftc.domain.Challenge;
import net.ambientia.uftc.domain.ChallengeSportEvent;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ChallengeSportEventDao extends DaoBase<ChallengeSportEvent> {

//	@Autowired
//	private SessionFactory sessionFactory;
	
	@Transactional(propagation = Propagation.NEVER)
	public List<ChallengeSportEvent> getAll() {
		Query query = getCurrentSession().createQuery("FROM ChallengeSportEvent");
		return query.list();
	}

	@Override
	public void delete(ChallengeSportEvent challengeSportEvent) {
		Challenge challenge = challengeSportEvent.getChallenge();
		challenge.getChallengeSportEvents().remove(challengeSportEvent);
		getCurrentSession().delete(challengeSportEvent);
	}

	@Override
	public Integer add(Integer challengeId,
			ChallengeSportEvent challengeSportEvent) {
		challengeSportEvent.setChallenge((Challenge) getCurrentSession().get(
				Challenge.class, challengeId));
		return (Integer) getCurrentSession().save(challengeSportEvent);
	}

	public ChallengeSportEvent getById(int challengeSportEventId) {
		ChallengeSportEvent challengeSportEvent = (ChallengeSportEvent) getCurrentSession()
				.get(ChallengeSportEvent.class, challengeSportEventId);
		return challengeSportEvent;
	}
	
	public List<ChallengeSportEvent> getAllByChallengeId(Integer challengeId) {
		Challenge challenge = (Challenge) getCurrentSession().get(
				Challenge.class, challengeId);
		Hibernate.initialize(challenge.getChallengeSportEvents());
		return challenge.getChallengeSportEvents();
	}
}
