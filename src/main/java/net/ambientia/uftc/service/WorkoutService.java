package net.ambientia.uftc.service;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import net.ambientia.uftc.dao.ChallengeDao;
import net.ambientia.uftc.dao.ChallengeSportEventDao;
import net.ambientia.uftc.dao.UserDao;
import net.ambientia.uftc.dao.WorkoutDao;
import net.ambientia.uftc.domain.Challenge;
import net.ambientia.uftc.domain.User;
import net.ambientia.uftc.domain.Workout;
import net.ambientia.uftc.domain.Workout.FieldTypes;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("workoutService")
@Transactional
public class WorkoutService {

	// protected static Logger logger = Logger.getLogger("service");

	@Autowired
	private WorkoutDao workoutDao;
	
	@Autowired
	private ChallengeSportEventDao challengeSportEventDao;
	
	@Autowired
	private ChallengeDao challengeDao;
	
	private SessionFactory sessionFactory;

	public List<Workout> getAll() {
		return countPointsToWorkoutList(workoutDao.getAll());
	}
	
	public Workout getById(int id) {
		Workout workout = workoutDao.getById(id);
		Integer repetition = workout.getRepetition();
		Integer pointFactor = challengeSportEventDao.getById(workout.getChallengeSportEvent().getId()).getPointFactor();
		workout.setPoints(repetition*pointFactor);
		return workout;
	}

	public void add(Integer userId,Workout workout) {
		Challenge challenge = challengeDao.getById(workout.getChallengeSportEvent().getChallenge().getId());
		challenge.setTotalPoints(challenge.getTotalPoints()+workout.getRepetition()*challengeSportEventDao.getById(workout.getChallengeSportEvent().getId()).getPointFactor());
		challengeDao.save(challenge);
		workoutDao.add(userId, workout);
	}
	
	public void edit(Workout workout) {

		workoutDao.save(workout);			
	}

	public void save(Workout workout){
		workoutDao.save(workout);
	}
		
	
	public void setWorkoutDao(WorkoutDao workoutDao) {
		this.workoutDao = workoutDao;
	}

	public boolean isValid(Workout workout) {
		return workout.validate().size() == 0;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	public EnumSet<FieldTypes> getValidationErrorList(Workout workout) {
		return workout.validate();
	}
	
	public List<Workout> countPointsToWorkoutList(List<Workout> workoutList) {
		List<Workout> listWithPoints = new ArrayList<Workout>();
		
		for (Workout workout : workoutList) {
			Integer repetition = workout.getRepetition();
			Integer pointFactor = challengeSportEventDao.getById(workout.getChallengeSportEvent().getId()).getPointFactor();
			workout.setPoints(repetition*pointFactor);
			
			listWithPoints.add(workout);
		}
		return listWithPoints;
	}



	public Workout setNewPropertiesToExistingWorkout(Workout workout) {
		Workout persistedWorkout = getById(workout.getId());
		//persistedWorkout.setName(workout.getName());
		persistedWorkout.setChallengeSportEventId(workout.getChallengeSportEventId());
		persistedWorkout.setRepetition(workout.getRepetition());
		return persistedWorkout;
	}	

	public List<Workout> getAllByUser(User user){
		return countPointsToWorkoutList(workoutDao.getAllWorkoutsOfUser(user));
	}

	public boolean entityIsLocked(Workout workout) {
		
		return workoutDao.entityIsLocked(workout);
	}



}
