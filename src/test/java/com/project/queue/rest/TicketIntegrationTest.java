package com.project.queue.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.queue.persistence.domain.Ticket;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)

@AutoConfigureMockMvc

@Sql(scripts = { "classpath:ticket-schema.sql",
		"classpath:ticket-data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("test")
public class TicketIntegrationTest {

	@Autowired
	private MockMvc mockMVC;

	@Autowired
	private ObjectMapper mapper;

	@Test
	void testCreateTicketWithAssert() throws Exception {
		Ticket newTicket = new Ticket("Nick", "kjdfd", "");
		String newTicketAsJSON = this.mapper.writeValueAsString(newTicket);

		RequestBuilder request = MockMvcRequestBuilders.post("/create").content(newTicketAsJSON)
				.contentType(MediaType.APPLICATION_JSON);

		ResultMatcher checkStatus = status().is(201);

		Ticket expectedTicket = new Ticket(newTicket.getName(), newTicket.getProblemDescription(),
				newTicket.getSolution());

		// checks the status and then returns the whole result of the request
		MvcResult result = this.mockMVC.perform(request).andExpect(checkStatus).andReturn();

		// Extracts response body as a String (will be a JSON String with this app)
		String responseBody = result.getResponse().getContentAsString();

		// Uses the ObjectMapper to convert the JSON String back to a Dog
		Ticket actualTicket = this.mapper.readValue(responseBody, Ticket.class);

		assertThat(actualTicket).isEqualToComparingOnlyGivenFields(expectedTicket, "name", "problemDescription",
				"solution");
	}

	@Test
	void testReadAllTickets() throws Exception {
		Ticket testTicket = new Ticket("Nick", "kjdfd", "");
		List<Ticket> testTickets = new ArrayList<>();
		testTickets.add(testTicket);
		String testTicketsAsJSON = this.mapper.writeValueAsString(testTickets);

		RequestBuilder request = get("/read");

		ResultMatcher checkStatus = status().is(200);

		ResultMatcher checkBody = content().json(testTicketsAsJSON);

		this.mockMVC.perform(request).andExpect(checkStatus);
	}

}
