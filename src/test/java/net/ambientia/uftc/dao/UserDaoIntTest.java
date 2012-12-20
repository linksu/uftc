package net.ambientia.uftc.dao;

import static org.mockito.Mockito.mock;

import java.util.List;

import net.ambientia.uftc.domain.User;
import net.ambientia.uftc.domain.Workout;

import org.junit.After;
import org.junit.Assert;
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
public class UserDaoIntTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private WorkoutDao workoutDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public UserDao getUserDao() {
		return this.userDao;
	}

	@Autowired
	private UftcDao uftcDao;

	public void setUftcDao(UftcDao uftcDao) {
		this.uftcDao = uftcDao;
	}

	public UftcDao getUftcDao() {
		return this.uftcDao;
	}

	@Before
	public void before() {
		
		System.out.println("BEFORE BEFORE");
		User user = new User();
		user.setFirstName("Testi");
		user.setLastName("Mies");
		user.setUsername("TestiMies");
		userDao.add(new Integer(1), user);
	}

	@After
	public void after() {

		System.out.println("AFTER AFTER");
		List<User> userList = userDao.getAll();
		for (int i = 0; i < userList.size(); i++) {
			userDao.delete(userList.get(i));
		}

	}

	@Test
	public void userDaoShouldNotBeNull_afterSettingIt() {
		setUserDao(mock(UserDao.class));
		Assert.assertNotNull(getUserDao());
	}

	@Test
	public void listShouldBeEmptyAfterDelete_deleteLastUser() throws Exception {
		List<User> userList = userDao.getAll();
		User user = userList.get(0);
		userDao.delete(user);
		Assert.assertEquals(new Integer(0), userDao.count());
	}

	@Test
	public void uftcListShouldBeEmptyAfterDelete_deleteLastUser()
			throws Exception {
		List<User> userList = userDao.getAll();
		User user = userList.get(0);
		userDao.delete(user);
		Assert.assertEquals(0, uftcDao.getById(1).getUsers().size());
	}

	@Test
	public void userNameShouldMatchs_whenAddingNewUser() throws Exception {
		User user = new User();
		user.setFirstName("Testi");
		user.setLastName("Mies");
		user.setUsername("pynttvi");
		userDao.add(new Integer(1), user);
		List<User> userList = userDao.getAll();
		User lastUser = userList.get(userList.size() - 1);
		Assert.assertEquals("pynttvi", lastUser.getUsername());
	}

	@Test
	public void updateShouldWork_When_newUsernameIsSetToUser() throws Exception {
		User user = new User();
		user.setFirstName("Testi");
		user.setLastName("Mies");
		user.setUsername("pynttvi");
		userDao.add(new Integer(1), user);

		Assert.assertEquals("pynttvi", userDao.getAll().get(1).getUsername());

		user = userDao.getAll().get(1);
		user.setUsername("ville");
		userDao.update(user);

		Assert.assertEquals("ville", userDao.getAll().get(1).getUsername());
	}

	@Test
	public void getByIdShouldReturnUser_whenLastUserOnDBisCalled()
			throws Exception {

		List<User> userList = userDao.getAll();
		User user = userList.get(userList.size() - 1);
		System.out.println("VIIMENEN" + user.getId());
		User myuser = new User();
		myuser.setFirstName("Testi");
		myuser.setLastName("Mies");
		myuser.setUsername("pynttvi");

		userDao.add(new Integer(1), myuser);
		Assert.assertEquals("pynttvi", userDao.getById(user.getId() + 1)
				.getUsername());

	}

	@Test
	public void shouldGetAllProperties_afterSettingAllPropsAndSave() {
		User user = userDao.getAll().get(0);
		user.setFirstName("firstName");
		user.setLastName("lastName");
		user.setUsername("userName");

		

		userDao.update(user);
		user = userDao.getAll().get(0);

		Assert.assertEquals("firstName", user.getFirstName());
		Assert.assertEquals("lastName", user.getLastName());
		Assert.assertEquals("userName", user.getUsername());
	}

	@Test
	public void shouldPersistWorkout_afterUserIsSaved_whenUsingUserDao(){
		User user = userDao.getAll().get(0);
		Workout workout = new Workout();
//		workout.setName("TestWorkOut");
		workout.setRepetition(1);
		user.getWorkouts().add(workout);
		workout.setUser(user);
		userDao.save(user);
		Integer workoutCount = userDao.getAll().get(0).getWorkouts().size();
		Assert.assertEquals(new Integer(1), workoutCount);
	}
	@Test
	public void shouldPersistWorkout_afterUserIsSaved_whenUsingWorkoutDao(){
		List<Workout> workouts = workoutDao.getAll();
		Assert.assertEquals(0, workouts.size());
		
		User user = userDao.getAll().get(0);
		Workout workout = new Workout();
//		workout.setName("TestWorkOut");
		workout.setRepetition(1);
		workout.setUser(user);
		user.getWorkouts().add(workout);
		userDao.save(user);
		workouts = workoutDao.getAll();
		Assert.assertEquals(true, workouts.contains(workout));
	}	
	
	@Test
	public void shouldDeleteUserWorkoutRefence_whenWorkoutIsDeletedFromUser(){
		User user = userDao.getAll().get(0);
		Workout workout = new Workout();
//		workout.setName("TestWorkOut");
		workout.setRepetition(1);
		workout.setUser(user);
		user.addWorkout(workout);
		userDao.save(user);

		user = null;
		user = userDao.getAll().get(0);

		user.getWorkouts().remove(workout);
		userDao.save(user);
		Assert.assertEquals(0, userDao.getAll().get(0).getWorkouts().size());
	}	
	@Test
	public void shouldDeleteWorkout_whenWorkoutIsDeletedFromUser(){
		User user = userDao.getAll().get(0);
		Workout workout = new Workout();
//		workout.setName("TestWorkOut");
		workout.setRepetition(1);
		workout.setUser(user);
		user.getWorkouts().add(workout);
		userDao.save(user);
		
		user = null;
		user = userDao.getAll().get(0);

		Assert.assertEquals(1, workoutDao.getAll().size());
		user.getWorkouts().clear();
		userDao.save(user);
		List<Workout> workouts = workoutDao.getAll();
		
		Assert.assertEquals(0, workouts.size());
	}	
	@Test
	public void shouldDeleteReferencedWorkouts_whenUserIsDeleted(){
		User user = userDao.getAll().get(0);
		Workout workout = new Workout();
//		workout.setName("TestWorkOut");
		workout.setRepetition(1);
		workout.setUser(user);
		user.getWorkouts().add(workout);
		userDao.save(user);
		user = null;
		user = userDao.getAll().get(0);
		Assert.assertEquals(1, user.getWorkouts().size());
		userDao.delete(user);
		List<Workout> workouts = workoutDao.getAll();		
		Assert.assertEquals(0, workouts.size());
	}
	
	@Test
	public void userShouldBeDeletedFromUftc_whenUserIsDeleted(){
		Assert.assertEquals(new Integer(1), userDao.count());
		User user = userDao.getAll().get(0);
		userDao.delete(user);
		Assert.assertEquals(false, uftcDao.getById(1).getUsers().contains(user));
	}
	
	
}
