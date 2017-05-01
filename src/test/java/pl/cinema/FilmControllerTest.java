package pl.cinema;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import pl.cinema.model.Film;
import pl.cinema.model.Projection;
import pl.cinema.services.FilmService;
import pl.cinema.services.ProjectionService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FilmControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private FilmService filmService;
	
	@Autowired
	private ProjectionService projectionService;
	
	@Before
	public void initialize() {
		FilmInitializer.initializeFilms(filmService);
	}
	
	@After
	public void clear() {
		filmService.clear();
		projectionService.clear();
	}
	
	@Test
	public void testGetAll() throws Exception {
		List<Film> films = filmService.getAll();
		mockMvc.perform(get("/films"))
			.andExpect(status().isOk())
			.andExpect(model().attribute("films", films));
	}
	
	@Test
	public void testGetFilmDetails() {
		List<Film> films = filmService.getAll();
		films.forEach(film -> {
			try {
				mockGetAllFilmsDetails(film);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
	private void mockGetAllFilmsDetails(Film film) throws Exception {
		long id = film.getId();
		List<Projection> projections = projectionService.getAllProjectionsByFilmId(id);
			mockMvc
			.perform(get("/films/" + id))
			.andExpect(status().isOk())
			.andExpect(model().attribute("film", film))
			.andExpect
				(model().attribute("projections", projections));
	}

	
	@Test 
	public void testAddFilm() throws Exception {
		Film film = new Film("title1");
		film.setDescription("description1");
		film.setDuration(20);
		
		long id = filmService.getLastRecordId();
		film.setId(id++);
		mockMvc
			.perform(post("/films/add")
			.param("title", "title1")
			.param("duration", "20")
			.param("description", "description1"))
			.andExpect(status().isOk());
		List<Film> films = filmService.getAll();
 		boolean filmIsInDatabase = filmService.getAll().contains(film);
		assertTrue(filmIsInDatabase);
		
		film = filmService.getFilmById(id);
		String expectedTitle = "title1";
		String expectedDescription = "description1";
		int expectedDuration = 20;
		assertEquals(film.getDescription(), expectedDescription);
		assertEquals(film.getDuration(), expectedDuration);
		assertEquals(film.getTitle(), expectedTitle);
	}
	
	@Test
	public void testDeleteFilm() throws Exception {
		List<Film> films = filmService.getAll();
		Film filmToDelete = films.get(0);
		long id = filmToDelete.getId();
		mockMvc
			.perform(post("/films/delete/" + id))
			.andExpect(status().isOk());
		films = filmService.getAll();
		boolean filmsContainsFilmToDelete = films.contains(filmToDelete);
		assertFalse(filmsContainsFilmToDelete);
	}
	
	@Test
	public void testEditFilm() throws Exception {
		List<Film> films = filmService.getAll();
		Film filmToEdit = films.get(0);
		long id = filmToEdit.getId();
		mockMvc
			.perform(post("/films/edit/" + id)
			.param("title", "editTitle")
			.param("duration", "22")
			.param("description", "editDescription"))
			.andExpect(status().isOk());
		Film film = filmService.getFilmById(id);
		String expectedTitle = "editTitle";
		String expectedDescription = "editDescription";
		int expectedDuration = 22;
		assertEquals(film.getDescription(), expectedDescription);
		assertEquals(film.getDuration(), expectedDuration);
		assertEquals(film.getTitle(), expectedTitle);
	}
	
}
