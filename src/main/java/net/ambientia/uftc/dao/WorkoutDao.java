package net.ambientia.uftc.dao;

import java.util.List;

import net.ambientia.uftc.domain.User;
import net.ambientia.uftc.domain.Workout;

import org.hibernate.PropertyValueException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("workoutDao")
@Transactional
public class WorkoutDao extends DaoBase<Workout> {

	private Session session = getCurrentSession();

	@Override
	// @Transactional(propagation = Propagation.NEVER)
	public List<Workout> getAll() {
		Query query = session.createQuery("FROM Workout");
		return query.list();
	}

	public List<Workout> getAllWorkoutsOfUser(User user) {
		Query query = session
				.createQuery("FROM Workout WHERE userId = :userId");
		query.setParameter("userId", user.getId());
		List<Workout> workouts = query.list();
		return workouts;
	}

	@Override
	public Integer add(Integer userId, Workout workout)
			throws PropertyValueException {
		User user = (User) session.get(User.class, userId);
		workout.setUser(user);
		Integer workoutId = (Integer) session.save(workout);
		user.getWorkouts().add(workout);
		session.saveOrUpdate(user);
		return workoutId;
	}

	public Workout getById(int id) {
		Workout workout = (Workout) session.get(Workout.class, id);
		return workout;
	}

	public void delete(Workout workout) {
		User user = workout.getUser();
		user.getWorkouts().remove(workout);
		session.delete(workout);
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
