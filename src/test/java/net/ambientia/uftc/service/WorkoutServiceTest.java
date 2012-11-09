package net.ambientia.uftc.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import junit.framework.Assert;

import net.ambientia.uftc.dao.UserDao;
import net.ambientia.uftc.dao.WorkoutDao;
import net.ambientia.uftc.domain.PointFactorType;
import net.ambientia.uftc.domain.SportEvent;
import net.ambientia.uftc.domain.Uftc;
import net.ambientia.uftc.domain.User;
import net.ambientia.uftc.domain.Workout;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
@TransactionConfiguration
public class WorkoutServiceTest extends
AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private WorkoutDao workoutDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private WorkoutService workoutService;
	
	@Autowired
	private SportEventService sportEventService;
	
	private User user;
	private Workout workout;
	
	@Before
	public void before() {
		
		user = new User();
		user.setFirstName("Testi");
		user.setLastName("Mies");
		user.setUsername("TestiMies");
		userDao.add(1, user);
		
		sportEventService.add(createSportEvent());

		workout = new Workout();
		workout.setName("TestWorkOut");
		workout.setRepetition(2);
		workout.setChallengeSportEventId(1);
		
		Integer userId = userDao.getAll().get(0).getId();
		workoutDao.add(userId, workout);
	}

	@After
	public void after() {
		List<Workout> workoutList = workoutDao.getAll();
		for (int i = 0; i < workoutList.size(); i++) {
			workoutDao.delete(workoutList.get(i));
		}
		List<User> userList = userDao.getAll();
		for (int i = 0; i < userList.size(); i++) {
			userDao.delete(userList.get(i));
		}
	}
	
	@Test
	public void workoutShouldBeEditedInParentClass_whenWorkoutIsUpdated() {
		Workout workout = workoutDao.getAll().get(0);
		User user = workout.getUser();
		workout.setName("editedWorkout");
		workoutService.edit(workout);
		assertEquals("editedWorkout", userDao.getById(user.getId()).getWorkouts().get(0).getName());
		assertEquals("editedWorkout", workoutDao.getById(workout.getId()).getName());
	}
	
	@Test
	public void getAllShouldReturnListOfWorkoutsWithPoints(){
		Assert.assertSame(4,workoutService.getAll().get(0).getPoints());
	}
	
	@Test
	public void getByIdShouldReturnWorkoutWithPoints(){
		Assert.assertSame(4,workoutService.getById(1).getPoints());
	}
	
	private SportEvent createSportEvent(){
		SportEvent sportEvent = new SportEvent();
		sportEvent.setPointFactor(1);
		sportEvent.setPointFactorType(PointFactorType.Minutes);
		sportEvent.setTitle("Juoksu");
		sportEvent.setUftc(new Uftc());
		
		return sportEvent;
	}


}
