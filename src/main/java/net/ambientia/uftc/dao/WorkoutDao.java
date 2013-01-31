package net.ambientia.uftc.dao;

import java.util.List;

import net.ambientia.uftc.domain.Challenge;
import net.ambientia.uftc.domain.User;
import net.ambientia.uftc.domain.Workout;

import org.hibernate.PropertyValueException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("workoutDao")
@Transactional
public class WorkoutDao extends DaoBase<Workout> {

//	@Autowired
//	private SessionFactory sessionFactory;
	
	@Override
	// @Transactional(propagation = Propagation.NEVER)
	public List<Workout> getAll() {
		Query query = getCurrentSession().createQuery("FROM Workout");
		return query.list();
	}

	public List<Workout> getAllWorkoutsOfUser(User user) {
		Query query = getCurrentSession()
				.createQuery("FROM Workout WHERE userId = :userId");
		query.setParameter("userId", user.getId());
		List<Workout> workouts = query.list();
		return workouts;
	}
	
	public List<Workout> getAllWorkoutsOfUserByUserAndChallenge(User user, Challenge challenge) {
		Query query = getCurrentSession()
				.createSQLQuery(
						"SELECT * FROM Workout where ChallengeSportEvent IN (SELECT id FROM ChallengeSportEvent WHERE challengeId = :challengeId) AND userId = :userId")
				.addEntity(Workout.class);
		query.setParameter("userId", user.getId());
		query.setParameter("challengeId", challenge.getId());
		List<Workout> workouts = query.list();
		return workouts;
	}

	@Override
	public Integer add(Integer userId, Workout workout)
			throws PropertyValueException {
		User user = (User) getCurrentSession().get(User.class, userId);
		workout.setUser(user);
		Integer workoutId = (Integer) getCurrentSession().save(workout);
		user.getWorkouts().add(workout);
		getCurrentSession().saveOrUpdate(user);
		return workoutId;
	}

	public Workout getById(int id) {
		Workout workout = (Workout) getCurrentSession().get(Workout.class, id);
		return workout;
	}

	public void delete(Workout workout) {
		User user = workout.getUser();
		user.getWorkouts().remove(workout);
		getCurrentSession().delete(workout);
	}

	public Integer count() {
		return getAll().size();
	}

	public boolean entityIsLocked(Workout editedWorkout) {
		Workout persistentWorkout = getById(editedWorkout.getId());
		if (persistentWorkout.getVersion() > editedWorkout.getVersion())
			return true;
		return false;
	}
}
