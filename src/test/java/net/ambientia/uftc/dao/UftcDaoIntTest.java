package net.ambientia.uftc.dao;

import junit.framework.Assert;
import net.ambientia.uftc.domain.Challenge;
import net.ambientia.uftc.domain.SportEvent;
import net.ambientia.uftc.domain.User;

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
public class UftcDaoIntTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private UftcDao uftcDao;

	@Autowired
	private SportEventDao sportEventDao;

	private Challenge challenge;
	private SportEvent sportEvent;

	@Before
	public void before() {

		challenge = new Challenge();
		sportEvent = new SportEvent();
	}

	@Test
	public void shouldReturnOneUser_whenAddingToUftcList() {
		int userCountBeforeTest = uftcDao.getById(1).getUsers().size();
		uftcDao.adduser(1, new User());
		Assert.assertEquals(userCountBeforeTest +1, uftcDao.getById(1).getUsers().size());
	}

	@Test
	public void uftcShouldHaveSportEvent_whenSportEventIsAdded() {
		sportEvent.setTitle("testtitle");
		sportEventDao.add(1, sportEvent);
		// Assert.assertEquals(1, uftcDao.getById(1).getSportEvents().size());
		Assert.assertEquals("testtitle", uftcDao.getById(1).getSportEvents()
				.get(0).getTitle());

	}

}
