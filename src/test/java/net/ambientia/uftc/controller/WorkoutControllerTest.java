package net.ambientia.uftc.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.stub;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.attribute.HashAttributeSet;

import net.ambientia.uftc.dao.WorkoutDao;
import net.ambientia.uftc.domain.Workout;
import net.ambientia.uftc.domain.Workout.FieldTypes;
import net.ambientia.uftc.domain.Workout;
import net.ambientia.uftc.service.ChallengeSportEventService;
import net.ambientia.uftc.service.UserService;
import net.ambientia.uftc.service.WorkoutService;
import net.ambientia.uftc.service.UftcService;
import net.ambientia.uftc.service.WorkoutService;

import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
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

public class WorkoutControllerTest {
	
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private AnnotationMethodHandlerAdapter adapter;
	private WorkoutController controller;
	private Workout workout;
	private WorkoutService workoutService;
	private WorkoutDao workoutDao;

	
	@Before
	public void before() {
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		response.setOutputStreamAccessAllowed(true);
		adapter = new AnnotationMethodHandlerAdapter();
		
		workout = new Workout();
		
		
		controller = new WorkoutController();
		workoutService = mock(WorkoutService.class);
		controller.setWorkoutService(workoutService);
		workoutDao = mock(WorkoutDao.class);
		workoutService.setWorkoutDao(workoutDao);
		workoutService = mock(WorkoutService.class);
		controller.setWorkoutService(workoutService);
		controller.setChallengeSportEventsService(mock(ChallengeSportEventService.class));
		
		
		workoutDao.setSessionFactory(mock(SessionFactory.class));
		
	}
	
	@Test
	public void addGetShouldReturnModelOfAddPage() throws Exception {
		request.setRequestURI("/workout/add");
		request.setMethod("GET");
		request.setParameter("userId", "1");
		ModelAndView mav = adapter.handle(request, response, controller);
		Assert.assertNotNull(mav.getModel().get("workoutInstance"));
	}
	
	@Test
	public void infoShouldReturnModelOfWorkout() throws Exception {
		request.setRequestURI("/workout/info");
		request.addParameter("workoutId", "1");
		request.setMethod("GET");
		when(workoutService.getById(Mockito.anyInt())).thenReturn(new Workout());
		ModelAndView mav = adapter.handle(request, response, controller);
		Assert.assertNotNull(mav.getModel().get("workoutInstance"));
	}
	
	@Test
	public void addPostShouldCallWorkoutServiceAddMethodOnce_whenIsValidReturnsTrue() throws Exception {

		controller.setUserService(mock(UserService.class));
		request.setRequestURI("/workout/add");
		request.setMethod("POST");
		request.setParameter("userId", "1");
		
		when(workoutService.isValid(Mockito.any(Workout.class))).thenReturn(true);
		adapter.handle(request, response, controller);
		
		verify(workoutService, times(1)).add(1,Mockito.any(Workout.class));
		
	}
	
	@Test
	public void addPostShouldHaveWorkoutInstanceAndErrorList_whenIsValidReturnsFalse() throws Exception {
		
		controller.setUserService(mock(UserService.class));
		request.setRequestURI("/workout/add");
		request.setMethod("POST");
		request.setParameter("userId", "1");
		EnumSet<FieldTypes> fieldTypeErrorList = EnumSet
				.noneOf(FieldTypes.class);
		
		when(workoutService.isValid(Mockito.any(Workout.class))).thenReturn(false);
		when(workoutService.getValidationErrorList(Mockito.any(Workout.class))).thenReturn(fieldTypeErrorList);
		ModelAndView mav = adapter.handle(request, response, controller);
		
		Assert.assertNotNull(mav.getModel().get("workoutInstance"));
		Assert.assertNotNull(mav.getModel().get("errors"));

	}
	
	
	@Test
	public void listShouldReturnWorkoutList() throws Exception {

		request.setRequestURI("/workout/list");
		request.setMethod("GET");
		
		ModelAndView mav = adapter.handle(request, response, controller);

		System.out.println(mav.getModelMap());
		Assert.assertNotNull(mav.getModel().get("workouts"));
		
	}
	
	@Test
	public void editGetShouldReturnModelOfWorkout() throws Exception {
		
		request.setRequestURI("/workout/edit");
		request.setParameter("workoutId", "1");
		request.setMethod("GET");
		when(workoutService.getById(1)).thenReturn(workout);
		
		ModelAndView mav = adapter.handle(request, response, controller);
		Assert.assertNotNull(mav.getModel().get("workoutInstance"));
		
	}
	
	
	@Test
	public void setOptimisticLockErrorModelShouldThreeValuesToModel() {
		Model model = mock(Model.class);
		controller.setupOptimisticLockErrorModel(model, workout);
		verify(model, times(3)).addAttribute(Mockito.anyString(),Mockito.any(Workout.class));

	}
	
	@Test
	public void setupErrorModelHaveWorkoutInstanceAndErrorListsetupErrorModel() throws Exception {
		
		controller.setUserService(mock(UserService.class));
		request.setRequestURI("/workout/add");
		request.setMethod("POST");
		request.setParameter("userId", "1");
		EnumSet<FieldTypes> fieldTypeErrorList = EnumSet
				.noneOf(FieldTypes.class);
		

		when(workoutService.getValidationErrorList(Mockito.any(Workout.class))).thenReturn(fieldTypeErrorList);
		ModelAndView mav = adapter.handle(request, response, controller);
		
		Assert.assertNotNull(mav.getModel().get("workoutInstance"));
		Assert.assertNotNull(mav.getModel().get("errors"));

	}
	
	@Test
	public void updateShouldReturnLinkBackToWorkoutEdit_whenEntityIsLocked() throws Exception {
		
		Workout editedWorkout = mock(Workout.class);
		Workout persistentWorkout = mock(Workout.class);
		editedWorkout.setId(1);
		Model model = mock(Model.class);
		
		controller.setUserService(mock(UserService.class));
		
		when(editedWorkout.getId()).thenReturn(1);
		when(editedWorkout.getVersion()).thenReturn(new Integer(1));
		when(persistentWorkout.getVersion()).thenReturn(new Integer(2));
		when(workoutService.getById(Mockito.anyInt())).thenReturn(persistentWorkout);
		when (workoutService.entityIsLocked(Mockito.any(Workout.class))).thenReturn(true);
		
		Assert.assertEquals("workout/edit",controller.update(editedWorkout, model));
			
	}
	
	@Test
	public void updateShouldReturnLinkBackToWorkoutEdit_whenEntityIsNotValid() throws Exception {
		
		Workout editedWorkout = mock(Workout.class);
		Workout persistentWorkout = mock(Workout.class);
		Model model = mock(Model.class);
		
		when(workoutService.entityIsLocked(Mockito.any(Workout.class))).thenReturn(false);
		when(workoutService.getById(Mockito.anyInt())).thenReturn(persistentWorkout);
		when(workoutService.isValid(Mockito.any(Workout.class))).thenReturn(false);
		when(editedWorkout.getId()).thenReturn(1);
		when(workoutService.setNewPropertiesToExistingWorkout(editedWorkout)).thenReturn(editedWorkout);
		controller.update(editedWorkout, model);

		Assert.assertEquals("workout/edit",controller.update(editedWorkout, model));
			
	}
	
	@Test
	public void updateShouldCallSaveMethod_whenWorkoutIsValid() throws ParseException {

		Workout editedWorkout = mock(Workout.class);
		Workout persistentWorkout = mock(Workout.class);
		Model model = mock(Model.class);
		
		when(workoutService.entityIsLocked(Mockito.any(Workout.class))).thenReturn(false);
		when(workoutService.getById(Mockito.anyInt())).thenReturn(persistentWorkout);
		when(workoutService.isValid(Mockito.any(Workout.class))).thenReturn(true);
		when(editedWorkout.getId()).thenReturn(1);
		when(workoutService.setNewPropertiesToExistingWorkout(editedWorkout)).thenReturn(editedWorkout);
		controller.update(editedWorkout, model);

		verify(workoutService, times(1)).save(Mockito.any(Workout.class));
		
	}
	
//	@Test
//	public void addGetShouldHaveListOfChallengeSportEvents() throws Exception{
//		request.setRequestURI("/workout/add");
//		request.setMethod("GET");
//		request.setParameter("userId", "1");
//		ModelAndView mav = adapter.handle(request, response, controller);
//		Assert.assertNotNull(mav.getModel().get("challengeSportEventList"));
//		
//	}

}
