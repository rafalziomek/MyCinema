package pl.cinema;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import pl.cinema.model.Film;
import pl.cinema.model.Projection;
import pl.cinema.services.FilmService;
import pl.cinema.services.ProjectionService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectionTest {
	@Autowired
	private ProjectionService projectionService;
	
	@Autowired
	private FilmService filmService;
	
	private List<Film> films;
	private List<Projection> projections;
	private long idOfFirstFilm;
	@Before
	public void initialize() {
		films = initializeFilms();
		projections = initializeProjections(films);
	}
	@Test
	public void testProjection() {
		List<Projection> projectionsOfFirstFilm = 
				projectionService.getAllProjectionsByFilmId(films.get(0).getId());
		List<Projection> projectionsToCheck = projections.subList(0, 9);
		assertEquals(projectionsOfFirstFilm, projectionsToCheck);
	}
	
	private List<Projection> initializeProjections(List<Film> films) {
		List<Projection> projections = new ArrayList<Projection>();
		long idOfFilm = 0;
		for(int j = 0; j < films.size() / 2; j ++) {
			idOfFilm = films.get(j).getId();
			for(int i = 1; i < 10; i++) {
				Projection projection = new Projection();
				projection.setFilm(filmService.getFilmById(idOfFilm));
				projection.setStartDate(LocalDateTime.now().plusDays(j).plusHours(i));
				projectionService.saveProjection(projection);
				projections.add(projection);
			}
		}
			
		return projections;
	}
	private List<Film> initializeFilms() {
		List<Film> films = new ArrayList<Film>();
		for(int i = 1; i < 30; i++) {
			Film film = new Film("Title " + i);
			film.setDescription("Description " + i);
			film.setDuration(i*2);
			filmService.addFilm(film);
			films.add(film);
		}
		return films;
	}
}
