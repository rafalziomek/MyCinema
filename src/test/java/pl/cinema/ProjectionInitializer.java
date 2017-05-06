package pl.cinema;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.cinema.model.Film;
import pl.cinema.model.Hall;
import pl.cinema.model.Projection;
import pl.cinema.model.Reservation;
import pl.cinema.services.FilmService;
import pl.cinema.services.HallService;
import pl.cinema.services.ProjectionService;
import pl.cinema.services.ReservationService;
@Service
public class ProjectionInitializer extends Initializer<Projection> {
	
	@Autowired
	private FilmService filmService;
	
	@Autowired 
	private ProjectionService projectionService;
	
	@Autowired 
	private ReservationService reservationService;
	
	@Autowired 
	private HallService hallService;
	
	@Override
	protected boolean conditionInitialize() {
		return projectionService.getAllProjections().size() == 0;
	}

	@Override
	protected List<Projection> initializer() {
		List<Film> films = filmService.getAll();
		List<Projection> projections = new ArrayList<Projection>();
		Hall hall = new Hall();
		hallService.addHall(hall);
		hall = hallService.getAll().get(0);
		long idOfFilm = 0;
		for(int j = 0; j < films.size() / 2; j ++) {
			idOfFilm = films.get(j).getId();
			for(int i = 1; i < 10; i++) {
				Projection projection = new Projection(filmService.getFilmById(idOfFilm));
				Reservation res = 
						new Reservation(hall, 
								LocalDateTime.now().plusDays(j).plusHours(i), 
								i*3);
				projection.setReservation(res);
				if(initializeDatabase) {
					projectionService.saveProjection(projection);
				}
				projections.add(projection);
			}
		}
		return projections;
	}
}
