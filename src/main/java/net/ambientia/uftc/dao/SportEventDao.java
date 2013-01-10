package net.ambientia.uftc.dao;

import java.util.List;

import net.ambientia.uftc.domain.SportEvent;
import net.ambientia.uftc.domain.Uftc;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class SportEventDao extends DaoBase<SportEvent> {

	private Session session = getCurrentSession();

	@Transactional(propagation = Propagation.NEVER)
	public List<SportEvent> getAll() {
		Query query = session.createQuery("FROM SportEvent");
		return query.list();
	}

	public SportEvent getById(int id) {
		SportEvent sportEvent = (SportEvent) session.get(SportEvent.class, id);
		return sportEvent;
	}

	public Integer add(Integer parentId, SportEvent sportEvent) {
		sportEvent.setUftc((Uftc) session.get(Uftc.class, parentId));
		return (Integer) session.save(sportEvent);
	}

	public void delete(SportEvent sportEvent) {
		Uftc uftc = sportEvent.getUftc();
		uftc.getSportEvents().remove(sportEvent);
		session.delete(sportEvent);
	}

	public void update(SportEvent sportEvent) {
		try {
			session.update(sportEvent);
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

	public Integer count() {
		return getAll().size();
	}

	// @Override
	// public void save(SportEvent object) {
	// Session session = sessionFactory.getCurrentSession();
	// session.saveOrUpdate(object);
	//
	// }

}
