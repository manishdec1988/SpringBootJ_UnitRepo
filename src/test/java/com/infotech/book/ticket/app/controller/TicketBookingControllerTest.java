package com.infotech.book.ticket.app.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infotech.book.ticket.app.entities.Ticket;
import com.infotech.book.ticket.app.service.TicketBookingService;

import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Stories;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TicketBookingController.class, secure = false)
public class TicketBookingControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TicketBookingService ticketBookingService;

	@Test
	@Stories("Store WS-801")
	@Description("GIVEN Ticket Booking controller is up and running, " + "WHEN user provides PassengerName,SourceStation and all mandatory details "
			+ "THEN the Ticket is created successfully in Database.")
	public void validate_testCreateTicketController() throws Exception {

		Ticket mockTicket = new Ticket();
		mockTicket.setTicketId(1);
		mockTicket.setPassengerName("Manish Bingel");
		mockTicket.setSourceStation("Kolkata");
		mockTicket.setDestStation("Delhi");
		mockTicket.setBookingDate(new Date());
		mockTicket.setEmail("Manish.s2017@gmail.com");

		String inputInJson = this.mapToJson(mockTicket);

		String URI = "/api/tickets/create";

		Mockito.when(ticketBookingService.createTicket(Mockito.any(Ticket.class))).thenReturn(mockTicket);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON)
				.content(inputInJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		String outputInJson = response.getContentAsString();

		assertThat(outputInJson).isEqualTo(inputInJson);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	@Stories("Store WS-801")
	@Description("GIVEN Ticket Booking controller is up and running, " + "WHEN user provides TicketId "
			+ "THEN the Ticket is returned  successfully from Database.")
	public void validate_testGetTicketByIdController() throws Exception {
		Ticket mockTicket = new Ticket();
		mockTicket.setTicketId(1);
		mockTicket.setPassengerName("Manish Bingel");
		mockTicket.setSourceStation("Kolkata");
		mockTicket.setDestStation("Delhi");
		mockTicket.setBookingDate(new Date());
		mockTicket.setEmail("Manish.s2017@gmail.com");

		Mockito.when(ticketBookingService.getTicketById(Mockito.anyInt())).thenReturn(mockTicket);

		String URI = "/api/tickets/ticketId/1";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(mockTicket);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
	}

	@Test
	@Stories("Store WS-801")
	@Description("GIVEN Ticket Booking controller is up and running, " + "WHEN user provides TicketId "
			+ "THEN the Booked Ticket are returned  successfully from Database.")
	public void validate_testGetAllBookedTicketsController() throws Exception {

		Ticket mockTicket1 = new Ticket();
		mockTicket1.setTicketId(1);
		mockTicket1.setPassengerName("Manish Bingel");
		mockTicket1.setSourceStation("Kolkata");
		mockTicket1.setDestStation("Delhi");
		mockTicket1.setBookingDate(new Date());
		mockTicket1.setEmail("Manish.s2017@gmail.com");

		Ticket mockTicket2 = new Ticket();
		mockTicket2.setPassengerName("Sean Murphy");
		mockTicket2.setSourceStation("Kolkata");
		mockTicket2.setDestStation("Mumbai");
		mockTicket2.setBookingDate(new Date());
		mockTicket2.setEmail("sean.s2017@gmail.com");

		List<Ticket> ticketList = new ArrayList<>();
		ticketList.add(mockTicket1);
		ticketList.add(mockTicket2);

		Mockito.when(ticketBookingService.getAllBookedTickets()).thenReturn(ticketList);

		String URI = "/api/tickets/alltickets";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expectedJson = this.mapToJson(ticketList);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
	}

	@Test
	@Stories("Store WS-801")
	@Description("GIVEN Ticket Booking controller is up and running, " + "WHEN user provides emailId "
			+ "THEN the Booked Ticket are returned  successfully from Database.")
	public void validate_testGetTicketByEmailController() throws Exception {
		Ticket mockTicket = new Ticket();
		mockTicket.setTicketId(1);
		mockTicket.setPassengerName("Manish Bingel");
		mockTicket.setSourceStation("Kolkata");
		mockTicket.setDestStation("Delhi");
		mockTicket.setBookingDate(new Date());
		mockTicket.setEmail("Manish.s2017@gmail.com");

		String expectedJson = this.mapToJson(mockTicket);

		Mockito.when(ticketBookingService.getTicketByEmail(Mockito.anyString())).thenReturn(mockTicket);

		String URI = "/api/tickets/email/Manish.s2017@gmail.com";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);

	}

	/**
	 * Maps an Object into a JSON String. Uses a Jackson ObjectMapper.
	 */
	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
}
