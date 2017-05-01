package pl.cinema;

import java.util.ArrayList;
import java.util.List;

import pl.cinema.model.Film;
import pl.cinema.services.FilmService;

public class FilmInitializer {
	public static List<Film> initializeFilms(FilmService filmService) {
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
