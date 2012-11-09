package net.ambientia.uftc.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.stub;

import java.text.ParseException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.HashAttributeSet;

import net.ambientia.uftc.dao.ChallengeDao;
import net.ambientia.uftc.domain.Challenge;
import net.ambientia.uftc.domain.Challenge.FieldTypes;
import net.ambientia.uftc.service.ChallengeService;
import net.ambientia.uftc.service.UftcService;

import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;


import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

public class ChallengeControllerTest {
	
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private AnnotationMethodHandlerAdapter adapter;
	private ChallengeController controller;
	private Challenge challenge;
	private ChallengeService challengeService;
	private ChallengeDao challengeDao;

	
	@Before
	public void before() {
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		response.setOutputStreamAccessAllowed(true);
		adapter = new AnnotationMethodHandlerAdapter();
		
		challenge = new Challenge();
		challenge.setTitle("testtitle");
		
		controller = new ChallengeController();
		challengeService = mock(ChallengeService.class);
		controller.setChallengeService(challengeService);
		challengeDao = mock(ChallengeDao.class);
		challengeService.setChallengeDao(challengeDao);
		
		challengeDao.setSessionFactory(mock(SessionFactory.class));
		
	}
	
	@Test
	public void addGetShouldReturnModelOfAddPage() throws Exception {
		request.setRequestURI("/challenge/add");
		request.setMethod("GET");
		ModelAndView mav = adapter.handle(request, response, controller);
		Assert.assertNotNull(mav.getModel().get("challengeInstance"));
	}
	
	@Test
	public void addPostShouldCallChallengeServiceAddMethodOnce_whenIsValidReturnsTrue() throws Exception {
		
		controller.setUftcService(mock(UftcService.class));
		controller.setUftcService(mock(UftcService.class));
		request.setRequestURI("/challenge/add");
		request.setMethod("POST");
		
		when(challengeService.isValid(Mockito.any(Challenge.class))).thenReturn(true);
		adapter.handle(request, response, controller);
		
		verify(challengeService, times(1)).add(Mockito.any(Challenge.class));
		
	}
	
	@Test
	public void addPostShouldHaveChallengeInstanceAndErrorList_whenIsValidReturnsFalse() throws Exception {
		
		controller.setUftcService(mock(UftcService.class));
		request.setRequestURI("/challenge/add");
		request.setMethod("POST");
		EnumSet<FieldTypes> fieldTypeErrorList = EnumSet
				.noneOf(FieldTypes.class);
		
		when(challengeService.isValid(Mockito.any(Challenge.class))).thenReturn(false);
		when(challengeService.getValidationErrorList(Mockito.any(Challenge.class))).thenReturn(fieldTypeErrorList);
		ModelAndView mav = adapter.handle(request, response, controller);
		
		Assert.assertNotNull(mav.getModel().get("challengeInstance"));
		Assert.assertNotNull(mav.getModel().get("errors"));

	}
	
	
	@Test
	public void listShouldReturnChallengeList() throws Exception {

		request.setRequestURI("/challenge/list");
		request.setMethod("GET");
		
		ModelAndView mav = adapter.handle(request, response, controller);

		System.out.println(mav.getModelMap());
		Assert.assertNotNull(mav.getModel().get("challenges"));
		
	}
	
	@Test
	public void editGetShouldReturnModelOfChallenge() throws Exception {
		request.setRequestURI("/challenge/edit");
		request.setParameter("challengeId", "1");
		request.setMethod("GET");
		when(challengeService.getById(1)).thenReturn(challenge);
		ModelAndView mav = adapter.handle(request, response, controller);
		Assert.assertNotNull(mav.getModel().get("challengeInstance"));
		
	}
	
	
	@Test
	public void setOptimisticLockErrorModelShouldThreeValuesToModel() {
		Model model = mock(Model.class);
		controller.setupOptimisticLockErrorModel(model, challenge);
		verify(model, times(3)).addAttribute(Mockito.anyString(),Mockito.any(Challenge.class));

	}
	
	@Test
	public void setupErrorModelHaveChallengeInstanceAndErrorListsetupErrorModel() throws Exception {
		
		controller.setUftcService(mock(UftcService.class));
		request.setRequestURI("/challenge/add");
		request.setMethod("POST");
		EnumSet<FieldTypes> fieldTypeErrorList = EnumSet
				.noneOf(FieldTypes.class);
		

		when(challengeService.getValidationErrorList(Mockito.any(Challenge.class))).thenReturn(fieldTypeErrorList);
		ModelAndView mav = adapter.handle(request, response, controller);
		
		Assert.assertNotNull(mav.getModel().get("challengeInstance"));
		Assert.assertNotNull(mav.getModel().get("errors"));

	}
	
	@Test
	public void updateShouldReturnLinkBackToChallengeEdit_whenEntityIsLocked() throws Exception {
		
		Challenge editedChallenge = mock(Challenge.class);
		Challenge persistentChallenge = mock(Challenge.class);
		editedChallenge.setId(1);
		Model model = mock(Model.class);
		
		controller.setUftcService(mock(UftcService.class));
		
		when(editedChallenge.getId()).thenReturn(1);
		when(editedChallenge.getVersion()).thenReturn(new Integer(1));
		when(persistentChallenge.getVersion()).thenReturn(new Integer(2));
		when(challengeService.getById(Mockito.anyInt())).thenReturn(persistentChallenge);
		when (challengeService.entityIsLocked(Mockito.any(Challenge.class))).thenReturn(true);
		
		Assert.assertEquals("challenge/edit",controller.update(editedChallenge, model));
			
	}
	
	@Test
	public void updateShouldReturnLinkBackToChallengeEdit_whenEntityIsNotValid() throws Exception {
		
		Challenge editedChallenge = mock(Challenge.class);
		Challenge persistentChallenge = mock(Challenge.class);
		Model model = mock(Model.class);
		
		when(challengeService.entityIsLocked(Mockito.any(Challenge.class))).thenReturn(false);
		when(challengeService.getById(Mockito.anyInt())).thenReturn(persistentChallenge);
		when(challengeService.isValid(Mockito.any(Challenge.class))).thenReturn(false);
		when(editedChallenge.getId()).thenReturn(1);
		when(challengeService.setNewPropertiesToExistingChallenge(editedChallenge)).thenReturn(editedChallenge);
		controller.update(editedChallenge, model);

		Assert.assertEquals("challenge/edit",controller.update(editedChallenge, model));
			
	}
	
	@Test
	public void updateShouldCallSaveMethod_whenChallengeIsValid() throws ParseException {

		Challenge editedChallenge = mock(Challenge.class);
		Challenge persistentChallenge = mock(Challenge.class);
		Model model = mock(Model.class);
		
		when(challengeService.entityIsLocked(Mockito.any(Challenge.class))).thenReturn(false);
		when(challengeService.getById(Mockito.anyInt())).thenReturn(persistentChallenge);
		when(challengeService.isValid(Mockito.any(Challenge.class))).thenReturn(true);
		when(editedChallenge.getId()).thenReturn(1);
		when(challengeService.setNewPropertiesToExistingChallenge(editedChallenge)).thenReturn(editedChallenge);
		controller.update(editedChallenge, model);

		verify(challengeService, times(1)).save(Mockito.any(Challenge.class));
		
	}

}
