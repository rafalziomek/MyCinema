package pl.cinema;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultMatcher;

import pl.cinema.model.Film;
import pl.cinema.model.Projection;

public class FilmControllerAuthorizedUserTests extends FilmControllerTest {
	
	@Test 
	@WithMockUser
	public void testAddFilm() throws Exception {
		mockMvc
			.perform(post("/films/add")
			.with(csrf())
			.param("title", "title1")
			.param("duration", "20")
			.param("description", "description1"))
			.andExpect(status().is3xxRedirection());
		
		Film film = filmService.getFilmByTitle("title1");
 
		String expectedTitle = "title1";
		String expectedDescription = "description1";
		int expectedDuration = 20;
		assertNotNull(film);
		assertEquals(film.getDescription(), expectedDescription);
		assertEquals(film.getDuration(), expectedDuration);
		assertEquals(film.getTitle(), expectedTitle);
	}
	
	@Test
	@WithMockUser
	public void testValidationAddFilm() throws Exception {
		mockMvc
		.perform(post("/films/add")
		.with(csrf())
		.param("title", "")
		.param("duration", "0")
		.param("description", ""))
		.andExpect(status().isOk())
		.andExpect(model().attributeErrorCount("film", 3))
		.andExpect(model().attributeHasFieldErrors("film", "title","description","duration"))
		.andExpect(view().name("addFilm"))
		.andDo(print())
		.andExpect(content().string(containsString("Title should contains 1 to 100 characters")))
		.andExpect(content().string(containsString("Duration of film should be greater then 1")))
		.andExpect(content().string(containsString("Description should contains 5 to 250 characters")));
	}
	
	
	@Test
	@WithMockUser
	public void testDeleteFilm() throws Exception {
		List<Film> films = filmService.getAll();
		Film filmToDelete = filmService.getFilmById(filmService.getFirstRecordId());
		long id = filmToDelete.getId();
		mockMvc
			.perform(post("/films/delete/" + id).with(csrf()))
			.andExpect(status().is3xxRedirection());
		films = filmService.getAll();
		boolean filmsContainsFilmToDelete = films.contains(filmToDelete);
		assertFalse(filmsContainsFilmToDelete);
	
	}
	
	@Test
	@WithMockUser
	public void testDeleteFilmWhichIsOnProjection() throws Exception {
		List<Projection> projections = projectionService.getAllProjections();
		Film film = projections.get(0).getFilm();
		long id = film.getId();
		mockMvc
			.perform(post("/films/delete/" + id).with(csrf()))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("This film is on projection, you cant delete it")));
		testFilmsContains(film);
	}
	
	private void testFilmsContains(Film film) {
		List<Film> films = filmService.getAll();
		boolean filmsContainsFilm = films.contains(films);
		assertTrue(filmsContainsFilm);
	}
	@Test
	@WithMockUser
	public void testEditFilm() throws Exception {
		long id = filmService.getFirstRecordId();
		mockMvc
			.perform(post("/films/edit/" + id).with(csrf())
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
