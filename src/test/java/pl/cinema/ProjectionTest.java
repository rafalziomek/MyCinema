package pl.cinema;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
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
	
	@Before
	public void initialize() {
		films = FilmInitializer.initializeFilms(filmService);
		projections = ProjectionInitializer.initializeProjections(films, filmService, projectionService);
	}
	
	@After
	public void delete() {
		projectionService.clear();
		filmService.clear();
	}
	
	@Test
	public void testProjection() {
		List<Projection> projectionsOfFirstFilm = 
				projectionService.getAllProjectionsByFilmId(films.get(0).getId());
		List<Projection> projectionsToCheck = projections.subList(0, 9);
		assertEquals(projectionsOfFirstFilm, projectionsToCheck);
	}
	
	
	
}
