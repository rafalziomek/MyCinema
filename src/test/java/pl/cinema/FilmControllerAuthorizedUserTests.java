package pl.cinema;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Test;
import org.springframework.security.test.context.support.WithMockUser;

import pl.cinema.model.Film;

public class FilmControllerAuthorizedUserTests extends FilmControllerTest {
	
	@Test 
	@WithMockUser
	public void testAddFilm() throws Exception {
		Film film = new Film("title1");
		film.setDescription("description1");
		film.setDuration(20);
		long id = filmService.getLastRecordId();
		film.setId(id++);
		mockMvc
			.perform(post("/films/add")
			.with(csrf())
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
	@WithMockUser
	public void testDeleteFilm() throws Exception {
		List<Film> films = filmService.getAll();
		Film filmToDelete = films.get(0);
		long id = filmToDelete.getId();
		mockMvc
			.perform(post("/films/delete/" + id).with(csrf()))
			.andExpect(status().isOk());
		films = filmService.getAll();
		boolean filmsContainsFilmToDelete = films.contains(filmToDelete);
		assertFalse(filmsContainsFilmToDelete);
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
