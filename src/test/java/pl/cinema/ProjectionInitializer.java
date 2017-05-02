package pl.cinema;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import pl.cinema.model.Film;
import pl.cinema.model.Projection;
import pl.cinema.services.FilmService;
import pl.cinema.services.ProjectionService;

public class ProjectionInitializer {
	public static List<Projection> initializeProjections(List<Film> films, FilmService filmService, ProjectionService projectionService) {
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
}
