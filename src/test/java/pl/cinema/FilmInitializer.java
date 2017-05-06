package pl.cinema;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.cinema.model.Film;
import pl.cinema.services.FilmService;
@Service
public class FilmInitializer extends Initializer<Film> {
	@Autowired 
	private FilmService filmService;
	
	@Override
	protected boolean conditionInitialize() {
		return filmService.getAll().size() == 0;
	}
	
	@Override
	protected List<Film> initializer() {
		List<Film> films = new ArrayList<Film>();
		for(int i = 1; i < 30; i++) {
			Film film = new Film("Title " + i);
			film.setDescription("Description " + i);
			film.setDuration(i*2);
			films.add(film);
			if(initializeDatabase) {
				filmService.addFilm(film);
			}
		}
		return films;
	}

	
}
