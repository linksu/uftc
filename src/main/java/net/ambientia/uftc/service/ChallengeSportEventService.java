package net.ambientia.uftc.service;

import java.text.ParseException;
import java.util.EnumSet;
import java.util.List;

import net.ambientia.uftc.dao.ChallengeSportEventDao;
import net.ambientia.uftc.domain.Challenge;
import net.ambientia.uftc.domain.ChallengeSportEvent;
import net.ambientia.uftc.domain.ChallengeSportEvent.FieldTypes;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("challengeSportEventsService")
@Transactional
public class ChallengeSportEventService {
	@Autowired
	private ChallengeSportEventDao challengeSportEventDao;
	
	private SessionFactory sessionFactory;

	@Transactional(propagation = Propagation.NEVER)
	public List<ChallengeSportEvent> getAll() {
		return challengeSportEventDao.getAll();
	}

	public void add(Integer challengeId, ChallengeSportEvent challengeSportEvent) {
		challengeSportEventDao.add(challengeId, challengeSportEvent);
	}

	public void save(ChallengeSportEvent challengeSportEvent) {
		challengeSportEventDao.save(challengeSportEvent);
	}
	
	public void delete(ChallengeSportEvent challengeSportEvent) {
		challengeSportEventDao.delete(challengeSportEvent);
		
	}
	
	//@Transactional(propagation = Propagation.NEVER)
	public ChallengeSportEvent getById(int challengeSportEventId){
		
		return challengeSportEventDao.getById(challengeSportEventId);
		
	}

	public ChallengeSportEvent setNewPropertiesToExistingChallenge(ChallengeSportEvent challengeSportEvent) throws ParseException {
		ChallengeSportEvent persistedChallengeSportEvent = getById(challengeSportEvent.getId());
		persistedChallengeSportEvent.setChallenge(challengeSportEvent.getChallenge());
		persistedChallengeSportEvent.setPointFactor(challengeSportEvent.getPointFactor());
		persistedChallengeSportEvent.setPointFactorType(challengeSportEvent.getPointFactorType());
		persistedChallengeSportEvent.setTitle(challengeSportEvent.getTitle());
		
		return persistedChallengeSportEvent;
	}
	
	
	
	
	public void setChallengeSportEventDao(
			ChallengeSportEventDao challengeSportEventDao) {
		this.challengeSportEventDao = challengeSportEventDao;
	}

	public boolean validate(ChallengeSportEvent challengeSportEvent) {
		return challengeSportEvent.validate().size() == 0;
	}

	public EnumSet<FieldTypes> getValidationErrorList(
			ChallengeSportEvent challengeSportEvent) {
		return challengeSportEvent.validate();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
}
