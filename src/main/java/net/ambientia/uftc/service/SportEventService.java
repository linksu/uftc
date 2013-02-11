package net.ambientia.uftc.service;

import java.util.EnumSet;
import java.util.List;

import net.ambientia.uftc.dao.SportEventDao;
import net.ambientia.uftc.domain.PointFactorType;
import net.ambientia.uftc.domain.SportEvent;
import net.ambientia.uftc.domain.SportEvent.FieldTypes;
import net.ambientia.uftc.domain.Uftc;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SportEventService {
	@Autowired
	private SportEventDao sportEventDao;
	
	private SessionFactory sessionFactory;

	@Transactional(propagation = Propagation.NEVER)
	public List<SportEvent> getAll() {
		return sportEventDao.getAll();
	}
	
	public SportEvent getById(int id){
		return sportEventDao.getById(id);
	}

	public void add(SportEvent sportEvent) {
		sportEventDao.add(1, sportEvent);
	}

	public void save(SportEvent sportEvent) {
		sportEventDao.save(sportEvent);
	}
	
	public void delete(SportEvent sportEvent){
		sportEventDao.delete(sportEvent);
	}
	
	public void update(SportEvent sportEvent){
		sportEventDao.update(sportEvent);
	}

	public void setSportEventDao(SportEventDao sportEventDao) {
		this.sportEventDao = sportEventDao;
	}

	public boolean isValid(SportEvent sportEvent) {
		return sportEvent.validate().size() == 0;
	}

	public EnumSet<FieldTypes> getValidationErrorList(SportEvent sportEvent) {
		return sportEvent.validate();
	}
	
	public void setSportEventUftc(SportEvent sportEvent, Uftc uftc) {
		sportEvent.setUftc(uftc);
	}
	
	public void setPointFactorType(SportEvent sportEvent, PointFactorType pointFactorType) {
		sportEvent.setPointFactorType(pointFactorType);
		
	}
	public void setPointFactor(SportEvent sportEvent, Integer pointFactor) {
		sportEvent.setPointFactor(pointFactor);
	}
	
	public SportEvent setNewPropertiesToExistingSportEvent(SportEvent sportEvent) {
		SportEvent persistedSportEvent = getById(sportEvent.getId());
		persistedSportEvent.setTitle(sportEvent.getTitle());
		persistedSportEvent.setPointFactorType(sportEvent.getPointFactorType());
		persistedSportEvent.setPointFactor(sportEvent.getPointFactor());	
		return persistedSportEvent;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
}
