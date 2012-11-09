package net.ambientia.uftc.dao;

import java.util.List;

import net.ambientia.uftc.domain.Challenge;
import net.ambientia.uftc.domain.ChallengeSportEvent;
import net.ambientia.uftc.domain.Uftc;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("challengeSportEventsDao")
@Transactional
public class ChallengeSportEventDao extends DaoBase<ChallengeSportEvent> {

	private SessionFactory sessionFactory;
	
	private Session session;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	@Transactional(propagation = Propagation.NEVER)
	public List<ChallengeSportEvent> getAll() {

		Session session = sessionFactory.getCurrentSession();

		Query query = session.createQuery("FROM ChallengeSportEvent");

		return query.list();
	}



	@Override
	public void delete(ChallengeSportEvent challengeSportEvent) {
		session = getCurrentSession();
		
		Challenge challenge = challengeSportEvent.getChallenge();
		challenge.getChallengeSportEvents().remove(challengeSportEvent);		
		
		session.delete(challengeSportEvent);
		
	}

	@Override
	public Integer add(Integer challengeId, ChallengeSportEvent challengeSportEvent) {
		session = getCurrentSession();
		challengeSportEvent.setChallenge((Challenge) session.get(Challenge.class, challengeId));
		return (Integer) session.save(challengeSportEvent);	
	}


	public ChallengeSportEvent getById(int challengeSportEventId) {
		Session session = getCurrentSession();
		 ChallengeSportEvent challengeSportEvent = (ChallengeSportEvent) session.get(ChallengeSportEvent.class, challengeSportEventId);
		 return challengeSportEvent;
	}
	
	


}
