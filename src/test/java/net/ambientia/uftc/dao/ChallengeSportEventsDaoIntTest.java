package net.ambientia.uftc.dao;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import net.ambientia.uftc.domain.Challenge;
import net.ambientia.uftc.domain.ChallengeSportEvent;
import net.ambientia.uftc.domain.User;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.mysql.jdbc.NotImplemented;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
@TransactionConfiguration
public class ChallengeSportEventsDaoIntTest extends AbstractTransactionalJUnit4SpringContextTests{
	
	@Autowired
	private ChallengeDao challengeDao;
	
	private Challenge challenge;
	
	
	@Autowired
	private ChallengeSportEventDao challengeSportEventsDao;
	
	
	
	@Before
	public void before() throws ParseException {
//		challenge = new Challenge();
//		challenge.setStartTime("18.06.2012");
//		challenge.setEndTime("18.06.2012");
//		challenge.setTitle("Titteli");
		
		challenge = challengeDao.getById(1);
	}

	@After
	public void after() {
		List<Challenge> challengeList = challengeDao.getAll();
		for (int i = 0; i < challengeList.size(); i++) {
			challengeDao.delete(challengeList.get(i));
		}

	}
	
	@Test
	public void challengeSportEventsShouldBeCreated_whenChallengeIsCreated() {
		challengeDao.add(1,challenge);
		Assert.assertNotNull(challengeDao.getChallengeSportEvents(challenge));		
	}
	
	@Test
	public void foreignKeyShouldMatchToChallenge_whenChallengeIsCreated(){
		challengeDao.add(1,challenge);
		Assert.assertEquals(challenge.getChallengeSportEvents().get(0).getId(),
				challengeSportEventsDao.getAll().get(0).getChallenge().getId());
	
	}
	
	@Test
	public void challengeSportEventsShouldBeDeleted_whenChallengeIsDeleted() {
		challengeDao.add(1,challenge);
		challengeDao.delete(challenge);
		
		Assert.assertSame(0, challengeSportEventsDao.count());
	}
	
	
	@Test(expected = NotImplemented.class)
	@Ignore
	public void shouldThrowException_ifUpdateMethodIsCalled() {
		SessionFactory mockSessionFactory = mock(SessionFactory.class);
		challengeSportEventsDao.setSessionFactory(mockSessionFactory);
		when(mockSessionFactory.getCurrentSession()).thenThrow(
				new Exception(""));
	//	challengeSportEventsDao.save(new ChallengeSportEvent(new Challenge()));
		
	}
	

}
