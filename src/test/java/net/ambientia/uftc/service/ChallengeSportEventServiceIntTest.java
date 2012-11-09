package net.ambientia.uftc.service;

import java.text.ParseException;

import junit.framework.Assert;
import net.ambientia.uftc.domain.Challenge;
import net.ambientia.uftc.domain.ChallengeSportEvent;
import net.ambientia.uftc.domain.PointFactorType;

import org.hibernate.SessionFactory;
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
public class ChallengeSportEventServiceIntTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private ChallengeSportEventService challengeSportEventService;

	@Autowired
	private ChallengeService challengeService;

	private SessionFactory sessionFactory;

	@Test
	public void addChallengeSportEvent() {
		challengeSportEventService.add(1, createChallengeSportEvent());
		Assert.assertEquals("Kävely", challengeSportEventService.getAll().get(0).getTitle());

	}

	@Test
	public void deleteChallengeSportEvent() {

		ChallengeSportEvent challengeSportEvent = createChallengeSportEvent();
		challengeSportEventService.add(1, challengeSportEvent);
		challengeSportEventService.delete(challengeSportEvent);
		Assert.assertSame(0, challengeSportEventService.getAll().size());

	}

	@Test
	public void setNewPropertiesToChallengeSportEvent() {
		challengeSportEventService.add(1, createChallengeSportEvent());
		ChallengeSportEvent challengeSportEvent = challengeSportEventService
				.getById(1);
		challengeSportEvent.setTitle("Test Title");

		try {
			challengeSportEventService
					.setNewPropertiesToExistingChallenge(challengeSportEvent);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertEquals("Test Title",challengeSportEventService.getById(1).getTitle());
	}

	private ChallengeSportEvent createChallengeSportEvent() {
		ChallengeSportEvent challengeSportEvent = new ChallengeSportEvent();
		challengeSportEvent.setChallenge(challengeService.getById(1));
		challengeSportEvent.setPointFactor(30);
		challengeSportEvent.setPointFactorType(PointFactorType.Hours);
		challengeSportEvent.setTitle("Kävely");
		return challengeSportEvent;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
