package net.ambientia.uftc.dao;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;
import net.ambientia.uftc.domain.Challenge;
import net.ambientia.uftc.domain.User;
import net.ambientia.uftc.domain.Workout;


import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
@TransactionConfiguration
public class ChallengeDaoIntTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private ChallengeDao challengeDao;

	@Autowired
	private UftcDao uftcDao;

	private Challenge challenge;

	@Before
	public void before() throws ParseException {
		challenge = new Challenge();
		challenge.setStartTimeString("18.06.2012");
		challenge.setEndTimeString("18.06.2012");
		challenge.setTitle("Titteli");
	}
	
	@After
	public void after() {
		List<Challenge> challengeList = challengeDao.getAll();
		for (int i = 0; i < challengeList.size(); i++) {
			challengeDao.delete(challengeList.get(i));
		}
	}
	
	@Test
	public void uftcShouldHaveChallenge_whenChallengeIsAdded() {
		challengeDao.add(1,challenge);
		Assert.assertTrue(uftcDao.getById(1).getChallenges().contains(challenge));
	}

	@Test
	public void shouldReturnSizeOne_afterAddingNewChallenge() {
		challengeDao.add(1,challenge);
		Assert.assertEquals(new Integer(1), challengeDao.count());
	}

	@Test
	public void shouldHaveChallengeSportEvents_afterAddingNewChallenge() {
		challengeDao.add(1,challenge);
		Assert.assertNotNull(challengeDao.getAll().get(0)
				.getChallengeSportEvents());
	}

	@Test
	public void shouldReturnSizeZero_afterDeletingLastChallenge() {
		challengeDao.add(1,challenge);
		challengeDao.delete(challenge);
		Assert.assertEquals(new Integer(0), challengeDao.count());
	}

	@Test
	public void deleteThisTest(){
		List<Integer> list = new ArrayList<Integer>();
		Assert.assertFalse(list.contains(new Integer(2)));
	}
	
	
	@Test
	public void shouldReturnSizeOne_whenUpdatingExistingEntity() {
		challenge.setTitle("title");
		challengeDao.add(1,challenge);
		challenge = null;
		challenge = challengeDao.getAll().get(0);
		challenge.setTitle("title2");
		challengeDao.save(challenge);
		Assert.assertEquals(new Integer(1), challengeDao.count());
	}

	@Test
	public void shouldReturnUpdatedTitle_whenQueryingUpdatedEntity() {
		challenge.setTitle("title");
		challengeDao.add(1,challenge);
		challenge = null;
		challenge = challengeDao.getAll().get(0);
		challenge.setTitle("title2");
		challengeDao.save(challenge);
		Assert.assertEquals("title2", challengeDao.getAll().get(0).getTitle());
	}


	@Test
	public void shouldReturnChallengeEntityCount_whenCallingCount() {
		challengeDao.add(1,challenge);
		Assert.assertEquals(new Integer(1), challengeDao.count());
	}
	
	@Test
	public void deletingChallengetShouldRemoveitFromUftcChallengeList() {
		challengeDao.add(1, challenge);
		Assert.assertEquals(1, uftcDao.getById(1).getChallenges().size());
		
		challengeDao.delete(challenge);
		Assert.assertEquals(false, uftcDao.getById(1).getChallenges().contains(challenge));
	}
	
	@Test
	public void isEntityLockedShouldReturnTrue_whenPersistentChallengeVersionIsHigherThanEditedChallengeVersion(){
		ChallengeDao mockedDao = mock(ChallengeDao.class);
		Challenge editedChallenge = mock(Challenge.class);
		Challenge persistentChallenge = mock(Challenge.class);
		
		when(editedChallenge.getId()).thenReturn(1);
		when(mockedDao.getById(Mockito.anyInt())).thenReturn(persistentChallenge);
		when(editedChallenge.getVersion()).thenReturn(1);
		when(persistentChallenge.getVersion()).thenReturn(2);
		
	}

	@Test(expected = HibernateException.class)
	@Ignore
	public void shouldHandleException_ifProblemWithSessionFactory() {
		SessionFactory mockSessionFactory = mock(SessionFactory.class);
		challengeDao.setSessionFactory(mockSessionFactory);
		when(mockSessionFactory.getCurrentSession()).thenThrow(
				new HibernateException(""));
		challengeDao.delete(new Challenge());

	}

}
