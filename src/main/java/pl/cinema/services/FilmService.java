package pl.cinema.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.cinema.model.Film;
import pl.cinema.repositories.FilmRepository;

@Service
public class FilmService {
	@Autowired
	private FilmRepository filmRepository;
	
	public List<Film> getAll() {
		return filmRepository.findAll();
	}
	public Film getFilmById(long id) {
		return filmRepository.findById(id);
	}

	public void addFilm(Film film) {
		filmRepository.save(film);
	}
	
	public void deleteFilmById(long id) {
		filmRepository.delete(id);
	}
}
