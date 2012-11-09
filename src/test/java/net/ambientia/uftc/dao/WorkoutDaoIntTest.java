package net.ambientia.uftc.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.ambientia.uftc.domain.Challenge;
import net.ambientia.uftc.domain.User;
import net.ambientia.uftc.domain.Workout;

import static org.junit.Assert.*;

import org.hibernate.PropertyValueException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.annotations.Nullability;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.ExpectedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
@TransactionConfiguration
public class WorkoutDaoIntTest extends
AbstractTransactionalJUnit4SpringContextTests {
	
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

	@Autowired
	private WorkoutDao workoutDao;

	@Autowired
	private ChallengeDao challengeDao;

	@Autowired
	private UserDao userDao;
	
	private Workout workout;

	private Challenge challenge;

	private User user;

	@Before
	public void before() {
		
		user = new User();
		user.setFirstName("Testi");
		user.setLastName("Mies");
		user.setUsername("TestiMies");
		userDao.add(1, user);
		
		workout = new Workout();
		workout.setName("TestWorkOut");
		workout.setRepetition(2);
		Integer userId = userDao.getAll().get(0).getId();
		workoutDao.add(userId, workout);
	}

//	@After
//	public void after() {
//		List<Workout> workoutList = workoutDao.getAll();
//		for (int i = 0; i < workoutList.size(); i++) {
//			workoutDao.delete(workoutList.get(i));
//		}
//		List<User> userList = userDao.getAll();
//		for (int i = 0; i < userList.size(); i++) {
//			userDao.delete(userList.get(i));
//		}
//	}	
	
	@Test
	public void userTableNotEmpty() {
		assertNotSame(0,userDao.count());
	}
	
	@Test
	public void workoutTableNotEmpty() {
		assertNotSame(0,workoutDao.count());
	}
	
	
	@Test
	public void workoutUserNotNull() {
		assertNotNull(workoutDao.getAll().get(0).getUser());
		
	}
	
	@Test
	public void usersWorkoutTableNotEmpty() {
		assertNotSame(0, userDao.getAll().get(0).getWorkouts().size());
	}


	@Test
	public void deletingWorkoutShouldRemoveitFromUserWorkoutList() {
		Integer userID = workout.getUser().getId();
		workoutDao.delete(workout);
		Assert.assertEquals(0, userDao.getById(userID).getWorkouts().size());
	}



	@Test
	public void createNewWorkoutAndSaveAndDeleteIt_getAllShouldResultZero() {
		List<Workout> workoutList = workoutDao.getAll();
		Workout workout = workoutList.get(workoutList.size() - 1);
		User user = workout.getUser();
		user.getWorkouts().remove(workout);
		workoutDao.delete(workout);
		Assert.assertEquals(new Integer(0), workoutDao.count());
	}
	
	@Test
	public void createNewWorkoutAndSaveIt_getAllShouldResultOne() {
		workout.setName("Suorite");
//		workoutDao.add(1, workout);
		List<Workout> workoutList = workoutDao.getAll();
		Workout lastWorkout = workoutList.get(0);
		Assert.assertEquals("Suorite", lastWorkout.getName());
	}

	@Test
	public void createNewWorkoutAndSaveItThenChangeNameAndSaveAgain_getAllShouldResultOne() {
		workout.setName("name");
	//	workoutDao.add(1, workout);
		workout = null;
		workout = workoutDao.getAll().get(0);
		workout.setName("name2");
		workoutDao.save(workout);
		Assert.assertEquals(new Integer(1), workoutDao.count());
	}

	@Test
	public void shouldReturnUpdatedTitle_whenQueryingUpdatedEntity() {
		workout.setName("name");
	//	workoutDao.add(1, workout);
		workout = null;
		workout = workoutDao.getAll().get(0);
		workout.setName("name2");
		workoutDao.save(workout);
		Assert.assertEquals("name2", workoutDao.getAll().get(0).getName());
	}


	@Test
	public void createChallengeUserAndWorkoutThenDeleteChallenge_workoutCountShouldResultOne() throws ParseException {
		challenge = new Challenge();
		challenge.setStartTime("18.06.2012");
		challenge.setEndTime("18.06.2012");
		challenge.setTitle("Titteli");
		user = new User();
		user.setFirstName("Testi");
		user.setLastName("Mies");
		user.setUsername("TestiMies");
		challengeDao.add(1, challenge);
		userDao.add(1, user);
		challengeDao.delete(challenge);
		Assert.assertSame(1, workoutDao.count());
	}

	@Test
	public void createChallengeAndUserThenDeleteChallenge_userCountShouldResultOne() throws ParseException {
		challenge = new Challenge();
		challenge.setStartTime("18.06.2012");
		challenge.setEndTime("18.06.2012");
		challenge.setTitle("Titteli");
		user = new User();
		user.setFirstName("Testi");
		user.setLastName("Mies");
		user.setUsername("TestiMies");
		
		challengeDao.add(1, challenge);
		userDao.add(1, user);
		challengeDao.delete(challenge);
		Assert.assertSame(2, userDao.count());
	}
	

	
	@Test(expected = PropertyValueException.class)
	@Ignore
	public void addingWorkoutShouldCauseException_whenThereIsNoUser() throws Exception{
		Workout myWorkout = new Workout();
			workoutDao.add(53, myWorkout);
	}

}
