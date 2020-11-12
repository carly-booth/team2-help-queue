package com.project.queue.rest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.queue.persistence.domain.Ticket;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)

@AutoConfigureMockMvc

public class TicketIntegrationTest {
	
	@Autowired
	private MockMvc mockMVC;

	@Autowired
	private ObjectMapper mapper;
	
	@Test
	void testCreateTicketWithAssert() throws Exception {
		Ticket newTicket = new Ticket("Nick", "kjdfd","");
		String newTicketAsJSON = this.mapper.writeValueAsString(newTicket);

		RequestBuilder request = MockMvcRequestBuilders.post("/create").content(newTicketAsJSON)
				.contentType(MediaType.APPLICATION_JSON);

		ResultMatcher checkStatus = status().is(201);

		Ticket expectedTicket = new Ticket(newTicket.getName(), newTicket.getProblemDescription(), newTicket.getSolution());

		// checks the status and then returns the whole result of the request
		MvcResult result = this.mockMVC.perform(request).andExpect(checkStatus).andReturn();

		// Extracts response body as a String (will be a JSON String with this app)
		String responseBody = result.getResponse().getContentAsString();
		
		// Uses the ObjectMapper to convert the JSON String back to a Dog
		Ticket actualTicket = this.mapper.readValue(responseBody, Ticket.class);

		assertThat(actualTicket).isEqualToComparingOnlyGivenFields(expectedTicket,"name","problemDescription","solution");
	}
	


}
