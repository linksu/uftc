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

import net.ambientia.uftc.dao.UserDao;
import net.ambientia.uftc.domain.User;
import net.ambientia.uftc.domain.User.FieldTypes;
import net.ambientia.uftc.domain.Workout;
import net.ambientia.uftc.service.UserService;
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

public class UserControllerTest {
	
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private AnnotationMethodHandlerAdapter adapter;
	private UserController controller;
	private User user;
	private UserService userService;
	private UserDao userDao;
	private WorkoutService workoutService;

	
	@Before
	public void before() {
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		response.setOutputStreamAccessAllowed(true);
		adapter = new AnnotationMethodHandlerAdapter();
		
		user = new User();
		user.setUsername("user");
		
		controller = new UserController();
		userService = mock(UserService.class);
		controller.setUserService(userService);
		userDao = mock(UserDao.class);
		userService.setUserDao(userDao);
		workoutService = mock(WorkoutService.class);
		controller.setWorkoutService(workoutService);
		
		
		userDao.setSessionFactory(mock(SessionFactory.class));
		
	}
	
	@Test
	public void addGetShouldReturnModelOfAddPage() throws Exception {
		request.setRequestURI("/user/add");
		request.setMethod("GET");
		ModelAndView mav = adapter.handle(request, response, controller);
		Assert.assertNotNull(mav.getModel().get("userInstance"));
	}
	
	@Test
	public void infoShouldReturnModelOfUser() throws Exception {
		request.setRequestURI("/user/show");
		request.addParameter("userId", "1");
		request.setMethod("GET");
		when(userService.getById(Mockito.anyInt())).thenReturn(new User());
		ModelAndView mav = adapter.handle(request, response, controller);
		Assert.assertNotNull(mav.getModel().get("userInstance"));
	}
	
	@Test
	public void addPostShouldCallUserServiceAddMethodOnce_whenIsValidReturnsTrue() throws Exception {

		controller.setUftcService(mock(UftcService.class));
		request.setRequestURI("/user/add");
		request.setMethod("POST");
		
		when(userService.isValid(Mockito.any(User.class))).thenReturn(true);
		adapter.handle(request, response, controller);
		
		verify(userService, times(1)).add(Mockito.any(User.class));
		
	}
	
	@Test
	public void addPostShouldHaveUserInstanceAndErrorList_whenIsValidReturnsFalse() throws Exception {
		
		controller.setUftcService(mock(UftcService.class));
		request.setRequestURI("/user/add");
		request.setMethod("POST");
		EnumSet<FieldTypes> fieldTypeErrorList = EnumSet
				.noneOf(FieldTypes.class);
		
		when(userService.isValid(Mockito.any(User.class))).thenReturn(false);
		when(userService.getValidationErrorList(Mockito.any(User.class))).thenReturn(fieldTypeErrorList);
		ModelAndView mav = adapter.handle(request, response, controller);
		
		Assert.assertNotNull(mav.getModel().get("userInstance"));
		Assert.assertNotNull(mav.getModel().get("errors"));

	}
	
	
	@Test
	public void listShouldReturnUserList() throws Exception {

		request.setRequestURI("/user/list");
		request.setMethod("GET");
		
		ModelAndView mav = adapter.handle(request, response, controller);

		System.out.println(mav.getModelMap());
		Assert.assertNotNull(mav.getModel().get("users"));
		
	}
	
	@Test
	public void editGetShouldReturnModelOfUser() throws Exception {
		
		request.setRequestURI("/user/edit");
		request.setParameter("userId", "1");
		request.setMethod("GET");
		when(userService.getById(1)).thenReturn(user);
		
		when(workoutService.getAllByUser(Mockito.any(User.class))).thenAnswer(new Answer<List<Workout>>() {
			public List<Workout> answer(InvocationOnMock invocation) {
				 List<Workout> workouts = new ArrayList<Workout>();
				 workouts.add(new Workout());
				 return workouts;
			}
		});
		
		
		ModelAndView mav = adapter.handle(request, response, controller);
		Assert.assertNotNull(mav.getModel().get("userInstance"));
		
	}
	
	
	@Test
	public void setOptimisticLockErrorModelShouldThreeValuesToModel() {
		Model model = mock(Model.class);
		controller.setupOptimisticLockErrorModel(model, user);
		verify(model, times(3)).addAttribute(Mockito.anyString(),Mockito.any(User.class));

	}
	
	@Test
	public void setupErrorModelHaveUserInstanceAndErrorListsetupErrorModel() throws Exception {
		
		controller.setUftcService(mock(UftcService.class));
		request.setRequestURI("/user/add");
		request.setMethod("POST");
		EnumSet<FieldTypes> fieldTypeErrorList = EnumSet
				.noneOf(FieldTypes.class);
		

		when(userService.getValidationErrorList(Mockito.any(User.class))).thenReturn(fieldTypeErrorList);
		ModelAndView mav = adapter.handle(request, response, controller);
		
		Assert.assertNotNull(mav.getModel().get("userInstance"));
		Assert.assertNotNull(mav.getModel().get("errors"));

	}
	
	@Test
	public void updateShouldReturnLinkBackToUserEdit_whenEntityIsLocked() throws Exception {
		
		User editedUser = mock(User.class);
		User persistentUser = mock(User.class);
		editedUser.setId(1);
		Model model = mock(Model.class);
		
		controller.setUftcService(mock(UftcService.class));
		
		when(editedUser.getId()).thenReturn(1);
		when(editedUser.getVersion()).thenReturn(new Integer(1));
		when(persistentUser.getVersion()).thenReturn(new Integer(2));
		when(userService.getById(Mockito.anyInt())).thenReturn(persistentUser);
		when (userService.entityIsLocked(Mockito.any(User.class))).thenReturn(true);
		
		Assert.assertEquals("user/edit",controller.update(editedUser, model));
			
	}
	
	@Test
	public void updateShouldReturnLinkBackToUserEdit_whenEntityIsNotValid() throws Exception {
		
		User editedUser = mock(User.class);
		User persistentUser = mock(User.class);
		Model model = mock(Model.class);
		
		when(userService.entityIsLocked(Mockito.any(User.class))).thenReturn(false);
		when(userService.getById(Mockito.anyInt())).thenReturn(persistentUser);
		when(userService.isValid(Mockito.any(User.class))).thenReturn(false);
		when(editedUser.getId()).thenReturn(1);
		when(userService.setNewPropertiesToExistingUser(editedUser)).thenReturn(editedUser);
		controller.update(editedUser, model);

		Assert.assertEquals("user/edit",controller.update(editedUser, model));
			
	}
	
	@Test
	public void updateShouldCallSaveMethod_whenUserIsValid() throws ParseException {

		User editedUser = mock(User.class);
		User persistentUser = mock(User.class);
		Model model = mock(Model.class);
		
		when(userService.entityIsLocked(Mockito.any(User.class))).thenReturn(false);
		when(userService.getById(Mockito.anyInt())).thenReturn(persistentUser);
		when(userService.isValid(Mockito.any(User.class))).thenReturn(true);
		when(editedUser.getId()).thenReturn(1);
		when(userService.setNewPropertiesToExistingUser(editedUser)).thenReturn(editedUser);
		controller.update(editedUser, model);

		verify(userService, times(1)).save(Mockito.any(User.class));
		
	}

}
