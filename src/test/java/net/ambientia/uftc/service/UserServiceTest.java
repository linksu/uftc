package net.ambientia.uftc.service;

import net.ambientia.uftc.dao.ChallengeDao;
import net.ambientia.uftc.dao.UftcDao;
import net.ambientia.uftc.domain.Challenge;
import net.ambientia.uftc.domain.User;

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
public class UserServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UftcDao uftcDao;
	
	@Autowired
	private ChallengeDao challengeDao;
	
	private User user;
	

	
	@Before
	public void before(){
		user = new User();
		user.setFirstName("Teppo");
		user.setLastName("Testimies");
		user.setUsername("testimiesteppo");
		userService.add(user);
	}
	
	
	@Test
	public void shouldNotBeNull_ever() {
		Assert.assertNotNull(userService);
	}
	
	@Test
	public void shouldReturnTrue_whenTheresValidationErrorsInUserEntity() {
		User user = new User();
		Assert.assertFalse(userService.isValid(user));		
	}
	
	@Test
	public void shouldReturnUserFieldTypeEnumLargerThanZero_whenEntityHasValidationErrors(){
		User user = new User();
		Assert.assertTrue(userService.getValidationErrorList(user).size() > 0);
	}
	
	@Test
	public void userShouldBeEditedInUftc_whenUftcIsEdited() {
		
		User user = userService.getAll().get(0);
		user.setUsername("newtestimies");
		
		Assert.assertEquals("newtestimies", userService.getAll().get(0).getUsername());
		Assert.assertEquals("newtestimies", uftcDao.getAll().get(0).getUsers().get(0).getUsername());
	}
	
	@Test
	public void testAddUserToChallenge(){
		Challenge challenge = challengeDao.getById(1);
		userService.addUserToChallenge(challenge,user);
		Assert.assertSame("Teppo", challengeDao.getById(1).getUsers().get(0).getFirstName());
		
	}


	public UftcDao getUftcDao() {
		return uftcDao;
	}


	public void setUftcDao(UftcDao uftcDao) {
		this.uftcDao = uftcDao;
	}
	
	

}
