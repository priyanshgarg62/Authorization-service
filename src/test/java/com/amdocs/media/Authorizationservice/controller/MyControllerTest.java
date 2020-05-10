package com.amdocs.media.Authorizationservice.controller;

import org.junit.Test;
import org.junit.jupiter.api.Assumptions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import com.amdocs.media.Authorizationservice.config.KafkaProducer;
import com.amdocs.media.Authorizationservice.model.User;
import com.amdocs.media.Authorizationservice.model.UserDAO;
import com.amdocs.media.Authorizationservice.service.AuthService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = MyController.class) // , secure = false)
public class MyControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AuthService authService;

	@MockBean
	KafkaProducer kafkaSender;

	@Mock
	RestTemplate restTemplate;

	User user = new User();

	@Test
	public void retrieveUser() throws Exception {

		user.setUserName("Rahul");
		user.setPassword("Rahul213");
		Mockito.when(authService.getuser(Mockito.anyString())).thenReturn(user);

		String expected = "{userName:Rahul, password:Rahul213}";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/assignment/User/Rahul")
				.accept(MediaType.APPLICATION_JSON);

		Mockito.when(restTemplate.getForEntity("http://localhost:9091/assignment/User/Rahul", String.class))
				.thenReturn(new ResponseEntity<String>(expected, HttpStatus.OK));

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		System.out.println(result.getResponse().getContentAsString());

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

	}

	public UserDAO getDataSet() {
		UserDAO userDAO = new UserDAO();
		userDAO.setUserName("Rahul");
		userDAO.setPassword("Rahul213");
		userDAO.setAddress("INDIA");
		userDAO.setPhonenumber(96542927);
		userDAO.setTopic("POST");

		return userDAO;
	}

	@Test
	public void autheticateUserTestforPost() throws Exception {

		String expected = "PROFILE CREATED SUCCESSFULLY";
		String token = getDataSet().getTopic();

		Mockito.when(authService.validateUser(getDataSet())).thenReturn(true);

		Mockito.when(restTemplate.postForEntity("http://localhost:9091/assignment/login", getDataSet(), String.class))
				.thenReturn(new ResponseEntity<String>(expected, HttpStatus.OK));

		Assumptions.assumeTrue(token.equalsIgnoreCase("POST"));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/assignment/User").accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		System.out.println(result.getResponse());
	}

	@Test
	public void autheticateUserTestforPut() throws Exception {

		String expected = "Profile Updated";
		String token = "PUT";
		UserDAO user = getDataSet();
		user.setTopic("PUT");
		Mockito.when(authService.validateUser(getDataSet())).thenReturn(true);
		
		Mockito.when(restTemplate.postForEntity("http://localhost:9091/assignment/login", user, String.class))
		.thenReturn(new ResponseEntity<String>(expected, HttpStatus.OK));
		
		Assumptions.assumeTrue(token.equalsIgnoreCase("PUT"));

	}

}
