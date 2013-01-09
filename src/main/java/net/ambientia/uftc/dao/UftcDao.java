package net.ambientia.uftc.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.ambientia.uftc.domain.Challenge;
import net.ambientia.uftc.domain.Uftc;
import net.ambientia.uftc.domain.User;

@Repository("uftcDao")
@Transactional
public class UftcDao extends DaoBase<Uftc> {

	
	private SessionFactory sessionFactory;

	@Override
	@Transactional(propagation = Propagation.NEVER)
	public List<Uftc> getAll() {
		Session session = getCurrentSession();

		Query query = session.createQuery("FROM Uftc");

		return query.list();
	}

	@Override
	public Integer add(Integer parentId, Uftc object) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public void save(Uftc uftc){
//		Session session = sessionFactory.getCurrentSession();
//		session.saveOrUpdate(uftc);
//	}
	
	@Override
	public void delete(Uftc object) {
		// TODO Auto-generated method stub

	}

	
	public Integer count() {
		// TODO Auto-generated method stub
		return null;
	}

	public Uftc getById(int id) {

		Session session = sessionFactory.getCurrentSession();
		Uftc uftc = new Uftc();

		uftc = (Uftc) session.load(Uftc.class, id);

		return uftc;
	}
	

	public void addChallenge(Integer uftcId,Challenge challenge) {
		Session session = sessionFactory.getCurrentSession();
		
		Uftc uftc = (Uftc)session.get(Uftc.class, uftcId);
		List<Challenge> challenges = uftc.getChallenges();
		challenges.add(challenge);
		uftc.setChallenges(challenges);
		
		session.save(uftc);
	}
		
	public void adduser(Integer uftcId,User user) {
		Session session = sessionFactory.getCurrentSession();
		
		Uftc uftc = (Uftc)session.get(Uftc.class, uftcId);
		List<User> users = uftc.getUsers();
		users.add(user);
		uftc.setUsers(users);
		
		save(uftc);
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
