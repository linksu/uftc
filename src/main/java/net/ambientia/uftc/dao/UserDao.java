package net.ambientia.uftc.dao;

import java.util.List;

import net.ambientia.uftc.domain.Uftc;
import net.ambientia.uftc.domain.User;
import net.ambientia.uftc.domain.Workout;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserDao extends DaoBase<User> {

//	@Autowired
//	private SessionFactory sessionFactory;
		
	@Override
	public List<User> getAll() {
		Query query = getCurrentSession().createQuery("FROM User");
		return query.list();
	}

	@Override
	public Integer add(Integer parentId, User user) {
		user.setUftc((Uftc) getCurrentSession().get(Uftc.class, parentId));
		return (Integer) getCurrentSession().save(user);
	}

	public void addWorkoutToList(User user, Workout workout) {
		user.getWorkouts().add(workout);
	}

	public User getById(int id) {
		User user = (User) getCurrentSession().get(User.class, id);
		return user;
	}

	@Override
	public void delete(User user) {
		Uftc uftc = user.getUftc();
		uftc.getUsers().remove(user);
		getCurrentSession().delete(user);
	}

	public void update(User user) {
		try {
			getCurrentSession().update(user);
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

	public Integer count() {
		return getAll().size();
	}

	public List<Workout> getInitializedWorkouts(User user) {
		List<Workout> workout = user.getWorkouts();
		// Hibernate.initialize(workout);
		return workout;
	}

	public boolean entityIsLocked(User editedUser) {
		User persistentUser = getById(editedUser.getId());
		if (persistentUser.getVersion() > editedUser.getVersion())
			return true;
		return false;
	}
}
