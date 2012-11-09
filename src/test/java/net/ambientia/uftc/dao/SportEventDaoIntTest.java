package net.ambientia.uftc.dao;

import junit.framework.Assert;
import net.ambientia.uftc.domain.PointFactorType;
import net.ambientia.uftc.domain.SportEvent;

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
public class SportEventDaoIntTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private SportEventDao sportEventDao;
	
	@Autowired
	private UftcDao uftcDao;

	private SportEvent sportEvent;
	
	@Before
	public void before() {
		sportEvent = new SportEvent();
		sportEvent.setTitle("TestSportevent");
		
	}

	@After
	public void after() {
		
	}
	
	@Test
	public void shouldReturnSizeOne_afterAddingNewSportEvent(){
		sportEventDao.add(1,sportEvent);
		Assert.assertEquals(new Integer(1),sportEventDao.count());	
	}
	
	@Test
	public void shouldReturnSizeZero_whenDeletingLastSportEvent(){
		sportEventDao.add(1,sportEvent);
		sportEventDao.delete(sportEvent);
		Assert.assertEquals(new Integer(0),sportEventDao.count());	
	}
	
	@Test
	public void uftcSportEventListShouldReturnSizeZero_whenDeletingLastSportEvent(){
		sportEventDao.add(1,sportEvent);
		sportEventDao.delete(sportEvent);
		Assert.assertEquals(0,uftcDao.getById(1).getSportEvents().size());	
	}
	
	
	
	@Test
	public void shouldReturnSizeOne_whenUpdatingExistingEntity(){
		sportEvent.setTitle("title");
		sportEventDao.add(1,sportEvent);
		sportEvent = null;
		sportEvent = sportEventDao.getAll().get(0);
		sportEvent.setTitle("title2");
		sportEventDao.update(sportEvent);
		Assert.assertEquals(new Integer(1), sportEventDao.count());
	}
	
	@Test
	public void shouldReturnUpdatedTitle_whenQueryingUpdatedEntity(){
		sportEvent.setTitle("title");
		sportEventDao.add(1,sportEvent);
		sportEvent = null;
		sportEvent = sportEventDao.getAll().get(0);
		sportEvent.setTitle("title2");
		sportEventDao.update(sportEvent);
		Assert.assertEquals("title2", sportEventDao.getAll().get(0).getTitle());
	}	
	
	@Test
	public void setPointFactorType_ShouldReturnSetType(){
		sportEvent.setPointFactorType(PointFactorType.Kilometer);
		Assert.assertEquals(PointFactorType.Kilometer, sportEvent.getPointFactorType());
	}
	
	@Test
	public void SetPointFactory_ShouldReturnSetValue(){
		sportEvent.setPointFactor(4);
		Assert.assertEquals(new Integer(4), sportEvent.getPointFactor());
	}
	
	@Test
	public void shoulReturnChallengeEntityCount_whenCallingCount(){
		sportEventDao.add(1,sportEvent);
		Assert.assertEquals(new Integer(1), sportEventDao.count());
	}
	
	@Test
	public void shouldDeleteSportEventFromUftc_whenSportEventIsDeleted(){
		sportEventDao.add(1, sportEvent);
		Assert.assertEquals(1, uftcDao.getById(1).getSportEvents().size());
		sportEventDao.delete(sportEvent);
		Assert.assertEquals(false, uftcDao.getById(1).getSportEvents().contains(sportEvent));
	
	}
}
