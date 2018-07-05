package com.infotech.book.ticket.app.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.infotech.book.ticket.app.entities.Ticket;

import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Stories;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TicketBookingDaoTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private TicketBookingDao ticketBookingDao;

	@Test
	public void testSaveTicket() {
		Ticket ticket = getTicket();
		Ticket savedInDb = entityManager.persist(ticket);
		Ticket getFromDb = ticketBookingDao.findOne(savedInDb.getTicketId());
		assertThat(getFromDb).isEqualTo(savedInDb);
	}

	@Test
	@Stories("Store WS-601")
	@Description("GIVEN Ticket Booking service is up and running, " + "WHEN user provides TicketId, "
			+ "THEN the Ticket is returned successfully from Database.")
	public void validate_testGetTicketById() {
		Ticket ticket = new Ticket();
		ticket.setPassengerName("manish");
		ticket.setSourceStation("Kolkata");
		ticket.setDestStation("Delhi");
		ticket.setBookingDate(new Date());
		ticket.setEmail("manish.s2017@gmail.com");
		// Save ticket in DB
		Ticket ticketSavedInDb = entityManager.persist(ticket);

		// Get Ticket from DB
		Ticket ticketFromInDb = ticketBookingDao.findOne(ticketSavedInDb.getTicketId());
		assertThat(ticketSavedInDb).isEqualTo(ticketFromInDb);
	}

	@Test
	@Stories("Store WS-602")
	@Description("GIVEN Ticket Booking service is up and running, " + "WHEN user provide required details, "
			+ "THEN the list of all the booked ticket returned.")
	public void validate_testGetAllBookedTickets() {
		Ticket ticket1 = new Ticket();
		ticket1.setPassengerName("manish Bingel");
		ticket1.setSourceStation("Kolkata");
		ticket1.setDestStation("Delhi");
		ticket1.setBookingDate(new Date());
		ticket1.setEmail("manish.s2017@gmail.com");

		Ticket ticket2 = new Ticket();
		ticket2.setPassengerName("Sean Murphy");
		ticket2.setSourceStation("Kolkata");
		ticket2.setDestStation("Mumbai");
		ticket2.setBookingDate(new Date());
		ticket2.setEmail("sean.s2017@gmail.com");

		// Save both tickets in DB
		entityManager.persist(ticket1);
		entityManager.persist(ticket2);

		Iterable<Ticket> allTicketsFromDb = ticketBookingDao.findAll();
		List<Ticket> ticketList = new ArrayList<>();

		for (Ticket ticket : allTicketsFromDb) {
			ticketList.add(ticket);
		}
		assertThat(ticketList.size()).isEqualTo(2);
	}

	@Test
	@Stories("Store WS-603")
	@Description("GIVEN Ticket Booking service is up and running, " + "WHEN user provide required details and emailId, "
			+ "THEN Booked ticket for that emailId is returned successfully.")
	public void validate_testFindByEmail() {
		Ticket ticket = new Ticket();
		ticket.setPassengerName("manish Bingel");
		ticket.setSourceStation("Kolkata");
		ticket.setDestStation("Delhi");
		ticket.setBookingDate(new Date());
		ticket.setEmail("manish.s2017@gmail.com");

		// Ticket in DB
		entityManager.persist(ticket);

		// Get ticket info from DB for specified email
		Ticket getFromDb = ticketBookingDao.findByEmail("manish.s2017@gmail.com");
		assertThat(getFromDb.getPassengerName()).isEqualTo("manish Bingel");
	}

	@Test
	@Stories("Store WS-604")
	@Description("GIVEN Ticket Booking service is up and running, "
			+ "WHEN user wanted to delete the booked ticket from DB, "
			+ "THEN Booked ticket was removed successfully from DB.")
	public void validate_testDeleteTicketById() {
		Ticket ticket1 = new Ticket();
		ticket1.setPassengerName("manish Bingel");
		ticket1.setSourceStation("Kolkata");
		ticket1.setDestStation("Delhi");
		ticket1.setBookingDate(new Date());
		ticket1.setEmail("manish.s2017@gmail.com");

		Ticket ticket2 = new Ticket();
		ticket2.setPassengerName("Sean Murphy");
		ticket2.setSourceStation("Kolkata");
		ticket2.setDestStation("Mumbai");
		ticket2.setBookingDate(new Date());
		ticket2.setEmail("sean.s2017@gmail.com");
		// Save both tickets in DB
		Ticket persist = entityManager.persist(ticket1);
		entityManager.persist(ticket2);

		// delete one ticket DB
		entityManager.remove(persist);

		Iterable<Ticket> allTicketsFromDb = ticketBookingDao.findAll();
		List<Ticket> ticketList = new ArrayList<>();

		for (Ticket ticket : allTicketsFromDb) {
			ticketList.add(ticket);
		}
		assertThat(ticketList.size()).isEqualTo(1);
	}

	@Test
	@Stories("Store WS-605")
	@Description("GIVEN Ticket Booking service is up and running, "
			+ "WHEN user wanted to update the details in booked ticket, "
			+ "THEN user is successfully updated the emailId for booked ticket.")
	public void validate_testUpdateTicket() {
		Ticket ticket = new Ticket();
		ticket.setPassengerName("manish");
		ticket.setSourceStation("Kolkata");
		ticket.setDestStation("Delhi");
		ticket.setBookingDate(new Date());
		ticket.setEmail("manish.s2006@gmail.com");

		// save Ticket info in DB
		entityManager.persist(ticket);

		Ticket getFromDb = ticketBookingDao.findByEmail("manish.s2006@gmail.com");
		// update Email Address
		getFromDb.setEmail("manish_update.s2000@gmail.com");
		entityManager.persist(getFromDb);

		assertThat(getFromDb.getEmail()).isEqualTo("manish_update.s2000@gmail.com");
	}

	private Ticket getTicket() {
		Ticket ticket = new Ticket();
		ticket.setPassengerName("LM_Manish");
		ticket.setSourceStation("Delhi");
		ticket.setDestStation("Mumbai");
		ticket.setBookingDate(new Date());
		ticket.setEmail("manish.s2006@gmail.com");
		return ticket;
	}
}
