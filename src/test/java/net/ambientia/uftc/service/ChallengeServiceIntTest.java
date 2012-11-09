package net.ambientia.uftc.service;


import java.text.ParseException;
import java.util.Date;

import net.ambientia.uftc.dao.UftcDao;
import net.ambientia.uftc.dao.UserDao;
import net.ambientia.uftc.domain.Challenge;
import net.ambientia.uftc.domain.PointFactorType;
import net.ambientia.uftc.domain.SportEvent;
import net.ambientia.uftc.domain.Uftc;
import net.ambientia.uftc.domain.User;

import org.hibernate.Session;
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
public class ChallengeServiceIntTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private ChallengeService challengeService;
	
	@Autowired
	private UftcDao uftcDao;
	
	@Autowired
	private UserDao userDao;
	
	private Challenge challenge;
	
	
	@Before
	public void before() throws ParseException{
		
		challenge = challengeService.getAll().get(0);
		challenge.setUftc(uftcDao.getById(1));
	
	}
	
	@Test
	public void validatedChallengeShouldGoToDatabase() {
		Challenge testChallenge = createChallenge("Testichallenge");
		
		challengeService.isValid(testChallenge);
		challengeService.add(testChallenge);
		Assert.assertEquals("Testichallenge", challengeService.getAll().get(1).getTitle());
	}
	
	@Test
	public void challengeShouldBeDeleted(){
		challengeService.add(challenge);
		challengeService.delete(challenge);
		Assert.assertEquals(0, challengeService.getAll().size());
	}
	
	@Test
	public void shouldReturnUserFieldTypeEnumLargerThanZero_whenEntityHasValidationErrors(){
		Challenge challenge = new Challenge();
		Assert.assertTrue(challengeService.getValidationErrorList(challenge).size() > 0);
	}
	
	@Test
	public void challengeShouldBeEditedInUftc_whenChallengeIsSaved() {
		challenge.setTitle("Titteli2");
		challengeService.save(challenge);
		Assert.assertEquals("Titteli2", challengeService.getAll().get(0).getTitle());
		
	}
	
	@Test
	public void setNewPropertiesShouldEditChallenge() throws ParseException {
		challenge.setTitle("Titteli2");
		challengeService.setNewPropertiesToExistingChallenge(challenge);
		Assert.assertEquals("Titteli2", challengeService.getAll().get(0).getTitle());
		
	}
	
	@Test
	public void challengeShouldHaveDefaultSportEvents_whenChallengeIsAdded(){
		Challenge myChallenge = new Challenge();
		myChallenge.setTitle("TestChallenge");
		myChallenge.setTotalPoints(500);
		Uftc uftc = uftcDao.getById(1);
		myChallenge.setUftc(uftc);
		
		challengeService.add(myChallenge);
		Assert.assertEquals("TestChallenge",challengeService.getAll().get(1).getTitle());
		//Assert.assertNotNull(challengeService.getById(2).getChallengeSportEvents().get(0).getTitle());
		
		
	}
	
	@Test
	public void testChallengeIsActive() throws ParseException{
		Challenge myChallenge = challengeService.getById(1);		
		myChallenge.setStartTime("28.08.2012");
		myChallenge.setEndTime("29.08.2012");
		challengeService.save(myChallenge);
		Assert.assertTrue(challengeService.getById(1).isActive());
	}
	
	@Test
	public void testChallengeIsNotActive() throws ParseException{
		Challenge myChallenge = challengeService.getById(1);		
		myChallenge.setStartTime("28.08.2012");
		myChallenge.setEndTime("28.08.2012");
		challengeService.save(myChallenge);
		Assert.assertFalse(challengeService.getById(1).isActive());
	}
	
	@Test
	public void testGetUsers() {
		User user = userDao.getById(1);
		Challenge myChallenge = challengeService.getById(1);
		myChallenge.getUsers().add(user);
		challengeService.save(myChallenge);
		Assert.assertSame(1,challengeService.getUsers(myChallenge).size());
	}
	
	
	
	
	private Challenge createChallenge(String challengeTitle) {
		Challenge challenge = new Challenge();
		challenge.setTitle(challengeTitle);
		challenge.setTotalPoints(500); 
		challenge.setUftc(uftcDao.getById(1));
		
	return challenge;
		
	}

}
