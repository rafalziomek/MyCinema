package pl.cinema.filmTests;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.springframework.security.test.context.support.WithMockUser;

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
		long id = film.getId();
		filmService.deleteFilmById(id);
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
		.andExpect(view().name("film/addFilm"))
		.andDo(print())
		.andExpect(content().string(containsString("Title should contains 1 to 100 characters")))
		.andExpect(content().string(containsString("Duration of film should be greater then 1")))
		.andExpect(content().string(containsString("Description should contains 5 to 250 characters")));
	}
	
	@Test
	@WithMockUser
	public void testValidationEditFilm() throws Exception {
		Film film = new Film("filmEditValidation");
		film.setDuration(20);
		film.setDescription("description");
		filmService.addFilm(film);
		
		long id = filmService.getFilmByTitle("filmEditValidation").getId();
		mockMvc
		.perform(post("/films/edit/" + id)
		.with(csrf())
		.param("title", "")
		.param("duration", "0")
		.param("description", ""))
		.andExpect(status().isOk())
		.andExpect(model().attributeErrorCount("film", 3))
		.andExpect(model().attributeHasFieldErrors("film", "title","description","duration"))
		.andExpect(view().name("film/editFilm"))
		.andDo(print())
		.andExpect(content().string(containsString("Title should contains 1 to 100 characters")))
		.andExpect(content().string(containsString("Duration of film should be greater then 1")))
		.andExpect(content().string(containsString("Description should contains 5 to 250 characters")));
		
		filmService.deleteFilmById(id);
	}
	
	@Test
	@WithMockUser
	public void testUniqueAddFilm() throws Exception {
		Film film = new Film("notUniqueTitle");
		film.setDuration(20);
		film.setDescription("someValidAndGoodDescription");
		
		filmService.addFilm(film);
		
		mockMvc.perform(post("/films/add")
				.with(csrf())
				.param("title", "notUniqueTitle")
				.param("duration", "23")
				.param("description", "description"))
				.andExpect(status().isOk())
				.andExpect(model().attributeErrorCount("film", 1))
				.andExpect(model().attributeHasFieldErrors("film", "title"))
				.andDo(print())
				.andExpect(content().string(containsString("Title has to be unique")));
		filmService.deleteFilmById(film.getId());
	}
	
	@Test
	@WithMockUser
	public void testUniqueEditFilm() throws Exception {
		Film film = new Film("notUniqueTitle");
		film.setDuration(20);
		film.setDescription("someValidAndGoodDescription");
		
		filmService.addFilm(film);
		long id = film.getId();
		mockMvc.perform(post("/films/edit/" + id)
				.with(csrf())
				.param("title", "notUniqueTitle")
				.param("duration", "23")
				.param("description", "description"))
				.andExpect(status().isOk())
				.andExpect(model().attributeErrorCount("film", 1))
				.andExpect(model().attributeHasFieldErrors("film", "title"))
				.andDo(print())
				.andExpect(content().string(containsString("Title has to be unique")));
		filmService.deleteFilmById(id);
	}
	
	@Test
	@WithMockUser
	public void testDeleteFilm() throws Exception {
		Film film = new Film("filmDelete");
		film.setDuration(20);
		film.setDescription("description");
		filmService.addFilm(film);
		
		film = filmService.getFilmByTitle("filmDelete");
		long id = film.getId();
		
		mockMvc
			.perform(post("/films/delete/" + id).with(csrf()))
			.andExpect(status().is3xxRedirection());
		
		film = filmService.getFilmByTitle("filmDelete");
		assertNull(film);
	}
	
	@Test
	@WithMockUser
	public void testDeleteFilmWhichIsOnProjection() {
		List<Projection> projections = projectionService.getAllProjections();
		List<Projection> projectionsWithFilm = projections.stream().filter(p -> (p.getFilm() != null)).collect(Collectors.toList());
		projectionsWithFilm.forEach(p -> {
			Film film = p.getFilm();
			long id = film.getId();
			try {
				mockMvc
				.perform(post("/films/delete/" + id).with(csrf()))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("This film is on projection, you cant delete it")));
			} catch (Exception e) {
				e.printStackTrace();
			}
			testFilmsContains(film);
		});
	}

	
	private void testFilmsContains(Film film) {
		List<Film> films = filmService.getAll();
		boolean filmsContainsFilm = films.contains(film);
		assertTrue(filmsContainsFilm);
	}
	
	@Test
	@WithMockUser
	public void testEditFilm() throws Exception {
		Film film = new Film("filmEdit");
		film.setDuration(20);
		film.setDescription("description");
		filmService.addFilm(film);
		
		long id = filmService.getFilmByTitle("filmEdit").getId();
		mockMvc
			.perform(post("/films/edit/" + id).with(csrf())
			.param("title", "editTitle")
			.param("duration", "22")
			.param("description", "editDescription"))
			.andExpect(status().is3xxRedirection());
		
		film = filmService.getFilmById(id);
		String expectedTitle = "editTitle";
		String expectedDescription = "editDescription";
		int expectedDuration = 22;
		
		assertEquals(film.getDescription(), expectedDescription);
		assertEquals(film.getDuration(), expectedDuration);
		assertEquals(film.getTitle(), expectedTitle);
		
		filmService.deleteFilmById(id);
	} 
	
	
}
