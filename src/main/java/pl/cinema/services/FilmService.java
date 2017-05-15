package pl.cinema.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.cinema.model.Film;
import pl.cinema.repositories.FilmRepository;

@Service
public class FilmService implements CinemaService<Film>{
	@Autowired
	private FilmRepository filmRepository;
	
	public List<Film> getAll() {
		return filmRepository.findAll();
	}
	public Film getFilmById(long id) {
		return filmRepository.findById(id);
	}
	public Film getFilmByTitle(String title) {
		return filmRepository.findByTitle(title);
	}
	public void add(Film film) {
		filmRepository.save(film);
	}
	
	public void deleteFilmById(long id) {
		filmRepository.delete(id);
	}
	
	public long getLastRecordId() {
		List<Film> films = this.getAll();
		int size = films.size();
		return films.get(size - 1).getId();
	}
	
	public long getFirstRecordId() {
		List<Film> films = this.getAll();
		return films.get(0).getId();
	}
	
	public long saveExampleFilmAndGetId(String title) {
		Film film = new Film(title, 20, "someValidAndGoodDescription");
		add(film);
		return getFilmByTitle(title).getId();
	}
	
	public void clear() {
		filmRepository.deleteAll();
	}
	@Override
	public String getModelName() {
		return "film";
	}
	@Override
	public String getModelCollectionName() {
		return "films";
	}
}
