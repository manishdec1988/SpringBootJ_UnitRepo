package com.infotech.book.ticket.app.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.infotech.book.ticket.app.dao.TicketBookingDao;
import com.infotech.book.ticket.app.entities.Ticket;

import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Stories;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TicketBookingServiceTest {

	@Autowired
	private TicketBookingService ticketBookingService;

	@MockBean
	private TicketBookingDao ticketBookingDao;

	@Test
	@Stories("Store WS-111")
	@Description("GIVEN Ticket Ticket service is up and running, "
			+ "WHEN user wanted to create the details in new booked ticket, "
			+ "THEN user is successfully updated the emailId for booked ticket.")
	public void validate_testCreateTicketService() {

		Ticket ticket = new Ticket();
		ticket.setTicketId(1);
		ticket.setPassengerName("Manish Bingel");
		ticket.setSourceStation("Kolkata");
		ticket.setDestStation("Delhi");
		ticket.setBookingDate(new Date());
		ticket.setEmail("Manish.s2017@gmail.com");

		Mockito.when(ticketBookingDao.save(ticket)).thenReturn(ticket);

		assertThat(ticketBookingService.createTicket(ticket)).isEqualTo(ticket);

	}

	@Test
	@Stories("Store WS-222")
	@Description("GIVEN Ticket Ticket service is up and running, "
			+ "WHEN user wanted to get the details in new booked ticket, "
			+ "THEN user is successfully get the booked ticket.")
	public void validate_testGetTicketByIdService() {
		Ticket ticket = new Ticket();
		ticket.setTicketId(1);
		ticket.setPassengerName("Manish Bingel");
		ticket.setSourceStation("Kolkata");
		ticket.setDestStation("Delhi");
		ticket.setBookingDate(new Date());
		ticket.setEmail("Manish.s2017@gmail.com");

		Mockito.when(ticketBookingDao.findOne(1)).thenReturn(ticket);
		assertThat(ticketBookingService.getTicketById(1)).isEqualTo(ticket);
	}

	@Test
	@Stories("Store WS-333")
	@Description("GIVEN Ticket Ticket service is up and running, " + "WHEN user wanted to see all the booked ticket, "
			+ "THEN user is successfully see all the booked ticket.")
	public void validate_testGetAllBookedTicketsService() {
		Ticket ticket1 = new Ticket();
		ticket1.setPassengerName("Manish Bingel");
		ticket1.setSourceStation("Kolkata");
		ticket1.setDestStation("Delhi");
		ticket1.setBookingDate(new Date());
		ticket1.setEmail("Manish.s2017@gmail.com");

		Ticket ticket2 = new Ticket();
		ticket2.setPassengerName("Sean Murphy");
		ticket2.setSourceStation("Kolkata");
		ticket2.setDestStation("Mumbai");
		ticket2.setBookingDate(new Date());
		ticket2.setEmail("sean.s2017@gmail.com");

		List<Ticket> ticketList = new ArrayList<>();
		ticketList.add(ticket1);
		ticketList.add(ticket2);

		Mockito.when(ticketBookingDao.findAll()).thenReturn(ticketList);

		assertThat(ticketBookingService.getAllBookedTickets()).isEqualTo(ticketList);
	}

	@Test
	@Stories("Store WS-444")
	@Description("GIVEN Ticket Ticket service is up and running, " + "WHEN user wanted to delete the booked ticket, "
			+ "THEN user is successfully deleted the booked ticket.")
	public void validate_testDeleteTicketService() {
		Ticket ticket = new Ticket();
		ticket.setTicketId(1);
		ticket.setPassengerName("Manish Bingel");
		ticket.setSourceStation("Kolkata");
		ticket.setDestStation("Delhi");
		ticket.setBookingDate(new Date());
		ticket.setEmail("Manish.s2017@gmail.com");

		Mockito.when(ticketBookingDao.findOne(1)).thenReturn(ticket);
		Mockito.when(ticketBookingDao.exists(ticket.getTicketId())).thenReturn(false);
		assertFalse(ticketBookingDao.exists(ticket.getTicketId()));
	}

	@Test
	@Stories("Store WS-555")
	@Description("GIVEN Ticket Ticket service is up and running, "
			+ "WHEN user wanted to update all the booked ticket, "
			+ "THEN user is successfully updated the booked ticket.")
	public void validate_testUpdateTicketService() {
		Ticket ticket = new Ticket();
		ticket.setTicketId(1);
		ticket.setPassengerName("Manish Bingel");
		ticket.setSourceStation("Kolkata");
		ticket.setDestStation("Delhi");
		ticket.setBookingDate(new Date());
		ticket.setEmail("Manish.s2017@gmail.com");

		Mockito.when(ticketBookingDao.findOne(1)).thenReturn(ticket);

		ticket.setEmail("Manish.s2000@gmail.com");
		Mockito.when(ticketBookingDao.save(ticket)).thenReturn(ticket);

		assertThat(ticketBookingService.updateTicket(1, "Manish.s2017@gmail.com")).isEqualTo(ticket);

	}

	@Test
	@Stories("Store WS-666")
	@Description("GIVEN Ticket Ticket service is up and running, " + "WHEN user wanted to see TicketByEmail , "
			+ "THEN user is successfully see all the booked ticket for that email.")
	public void validate_testGetTicketByEmailService() {

		Ticket ticket = new Ticket();
		ticket.setTicketId(1);
		ticket.setPassengerName("Manish Bingel");
		ticket.setSourceStation("Kolkata");
		ticket.setDestStation("Delhi");
		ticket.setBookingDate(new Date());
		ticket.setEmail("Manish.s2017@gmail.com");

		Mockito.when(ticketBookingDao.findByEmail("Manish.s2017@gmail.com")).thenReturn(ticket);

		assertThat(ticketBookingService.getTicketByEmail("Manish.s2017@gmail.com")).isEqualTo(ticket);
	}
}
