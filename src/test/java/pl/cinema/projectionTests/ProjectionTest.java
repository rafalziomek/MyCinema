package pl.cinema.projectionTests;

import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import pl.cinema.ControllerTest;
import pl.cinema.initializers.FilmInitializer;
import pl.cinema.initializers.ProjectionInitializer;
import pl.cinema.model.Film;
import pl.cinema.model.Hall;
import pl.cinema.model.Projection;
import pl.cinema.model.Reservation;
import pl.cinema.model.validators.ReservationValidator;
import pl.cinema.services.FilmService;
import pl.cinema.services.HallService;
import pl.cinema.services.ProjectionService;
import pl.cinema.services.ReservationService;

public class ProjectionTest extends ControllerTest {
	
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
	
	@Autowired
	private FilmService filmService;
	
	@Autowired 
	private HallService hallService;
	
	@Autowired
	private ProjectionService projectionService;
	
	@Autowired
	private DateTimeFormatter formatter;
	
	@Before
	public void repositoryInitialize() {
		films = filmInitializer.initialize();
		projections = projectionInitializer.initialize();
	}
	
	@Test 
	public void testAddProjectionUnauthorized() throws Exception {
		String title = "titleAddProjectionUnauthorized";
		long filmId = filmService.saveExampleFilmAndGetId(title);
		Hall hall = new Hall();
		hallService.addHall(hall);
		List<Hall> halls = hallService.getAll();
		long hallId = halls.get(halls.size() - 1).getId();
		mockMvc
			.perform(post("/projections/add")
			.with(csrf())
			.param("film", Long.toString(filmId))
			.param("hall", Long.toString(hallId))
			.param("startDate", LocalDateTime.now().toString()))
			.andExpect(unauthenticated());
		filmService.deleteFilmById(filmId);
	}
	
	@Test 
	@WithMockUser
	public void testAddProjection() throws Exception {
		String title = "titleAddProjectionMockUser";
		
		long filmId = filmService.saveExampleFilmAndGetId(title);
		long hallId = hallService.addExampleHallAndGetId();
		
		String date = LocalDateTime.now().format(formatter);
		LocalDateTime time = LocalDateTime.parse(date, formatter);
		
		mockMvc
			.perform(post("/projections/add")
			.with(csrf())
			.param("film", Long.toString(filmId))
			.param("reservation.hall", Long.toString(hallId))
			.param("reservation.startDate", date))
			.andDo(print())
			.andExpect(status().is3xxRedirection());
		
		Projection projection = projectionService.getAllProjectionsByFilmId(filmId).get(0);
		
		assertEquals(projection.getFilm(), filmService.getFilmById(filmId));
		assertEquals(projection.getReservation().getHall(), hallService.getHallById(hallId));
		assertEquals(projection.getReservation().getStartDate(), time);
		
		projectionService.delete(projection);
		filmService.deleteFilmById(filmId);
	}
	
	
}
