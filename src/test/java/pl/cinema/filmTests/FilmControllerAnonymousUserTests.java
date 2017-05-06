package pl.cinema.filmTests;

import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Test;

import pl.cinema.model.Film;
import pl.cinema.model.Projection;

public class FilmControllerAnonymousUserTests extends FilmControllerTest {
	
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
	public void testAddFilmUnauthorized() throws Exception {
		mockMvc
			.perform(post("/films/add")
			.with(csrf())
			.param("title", "title1")
			.param("duration", "20")
			.param("description", "description1"))
			.andExpect(unauthenticated());
	}
	
	@Test
	public void testDeleteFilmUnauthorized() throws Exception {
		List<Film> films = filmService.getAll();
		Film filmToDelete = films.get(0);
		long id = filmToDelete.getId();
		mockMvc
			.perform(post("/films/delete/" + id))
			.andExpect(status().is4xxClientError());
		films = filmService.getAll();
		boolean filmsContainsFilmToDelete = films.contains(filmToDelete);
		assertTrue(filmsContainsFilmToDelete);
	}
	
	@Test
	public void testEditFilmUnauthorized() throws Exception {
		long id = filmService.getFirstRecordId();
		mockMvc
			.perform(post("/films/edit/" + id).with(csrf())
			.param("title", "editTitle")
			.param("duration", "22")
			.param("description", "editDescription"))
			.andExpect(unauthenticated());
	}
	
}
