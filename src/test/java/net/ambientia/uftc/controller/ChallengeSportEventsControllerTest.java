package net.ambientia.uftc.controller;

import static org.mockito.Mockito.mock;
import net.ambientia.uftc.domain.ChallengeSportEvent;
import net.ambientia.uftc.service.ChallengeSportEventService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

public class ChallengeSportEventsControllerTest {
	
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private AnnotationMethodHandlerAdapter adapter;
	private ChallengeSportEventsController controller;
	private ChallengeSportEventService challengeSportEventsService;

	@Before
	public void before() {
		controller = new ChallengeSportEventsController();
		challengeSportEventsService = mock(ChallengeSportEventService.class);
	}
	
	
	@Test
	@Ignore
	public void show() throws Exception {
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		response.setOutputStreamAccessAllowed(true);

		adapter = new AnnotationMethodHandlerAdapter();

		request.setRequestURI("/challengeSportEvents/show");
		request.setMethod("GET");
		
		ModelAndView mav = adapter.handle(request, response, controller);
		System.out.println(mav.getModelMap());
		Assert.assertNotNull(mav.getModel().get("challengeSportEventsAttribute"));
		
	}
	
	
}
