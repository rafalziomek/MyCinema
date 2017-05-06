package pl.cinema.projectionTests;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import pl.cinema.initializers.FilmInitializer;
import pl.cinema.initializers.ProjectionInitializer;
import pl.cinema.model.Film;
import pl.cinema.model.Hall;
import pl.cinema.model.Projection;
import pl.cinema.model.Reservation;
import pl.cinema.model.validators.ReservationValidator;
import pl.cinema.services.FilmService;
import pl.cinema.services.ProjectionService;
import pl.cinema.services.ReservationService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectionTest {
	
	@Autowired
	private ProjectionInitializer projectionInitializer;
	
	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	private FilmInitializer filmInitializer;
	
	private List<Film> films;
	private List<Projection> projections;
	
	@Autowired
	private ReservationValidator reservationValidator;
	@Before
	public void initialize() {
		films = filmInitializer.initialize();
		projections = projectionInitializer.initialize();
		
	}

	
	@Test
	public void testRepository() {
		Reservation bookedReservation = reservationService.getAll().get(0);
		Hall bookedHall = bookedReservation.getHall();
		// Projection initializer initiate reservations when above time reservation will be booked
		LocalDateTime timeToCheck = bookedReservation.getStartDate().plusSeconds(50);
		Reservation reservationToCheck = new Reservation(bookedHall, timeToCheck, 20);
		
		List<Reservation> bookedReservationsOnTimeToCheck 
			= reservationService.getBookedReservations(reservationToCheck);
		
		assertTrue(bookedReservationsOnTimeToCheck.contains(bookedReservation));
	}
	
	
	@Test
	public void testDateValidationReservation() {
		Reservation firstReservation = reservationService.getAll().get(0);
		Hall bookedHall = firstReservation.getHall();
		LocalDateTime wrongTime = firstReservation.getStartDate().plusSeconds(50);
		Reservation reservation = new Reservation(bookedHall, wrongTime, 5);
		
		Errors result = new BindException(reservation, "reservation");
		
		reservationValidator.validate(reservation, result);
		
		assertTrue(result.getErrorCount() == 1);
		
		ObjectError objErr = result.getAllErrors().get(0);
		assertEquals(objErr.getDefaultMessage(), "On this date hall is booked");
	}
	
}
