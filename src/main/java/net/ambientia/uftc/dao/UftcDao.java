package net.ambientia.uftc.dao;

import java.util.List;

import net.ambientia.uftc.domain.Challenge;
import net.ambientia.uftc.domain.Uftc;
import net.ambientia.uftc.domain.User;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UftcDao extends DaoBase<Uftc> {

//	@Autowired
//	private SessionFactory sessionFactory;

	@Override
	@Transactional(propagation = Propagation.NEVER)
	public List<Uftc> getAll() {
		Query query = getCurrentSession().createQuery("FROM Uftc");
		return query.list();
	}

	@Override
	public Integer add(Integer parentId, Uftc object) {
		// TODO Auto-generated method stub
		return null;
	}

	// @Override
	// public void save(Uftc uftc){
	// Session session = getCurrentSession();
	// session.saveOrUpdate(uftc);
	// }

	@Override
	public void delete(Uftc object) {
		// TODO Auto-generated method stub

	}

	public Integer count() {
		// TODO Auto-generated method stub
		return null;
	}

	public Uftc getById(int id) {
		Uftc uftc = new Uftc();
		uftc = (Uftc) getCurrentSession().load(Uftc.class, id);
		return uftc;
	}

	public void addChallenge(Integer uftcId, Challenge challenge) {
		Uftc uftc = (Uftc) getCurrentSession().get(Uftc.class, uftcId);
		List<Challenge> challenges = uftc.getChallenges();
		challenges.add(challenge);
		uftc.setChallenges(challenges);
		getCurrentSession().save(uftc);
	}

	public void adduser(Integer uftcId, User user) {
		Uftc uftc = (Uftc) getCurrentSession().get(Uftc.class, uftcId);
		List<User> users = uftc.getUsers();
		users.add(user);
		uftc.setUsers(users);
		save(uftc);
	}

	// public SessionFactory getSessionFactory() {
	// return sessionFactory;
	// }
	//
	// @Autowired
	// public void setSessionFactory(SessionFactory sessionFactory) {
	// this.sessionFactory = sessionFactory;
	// }
}
