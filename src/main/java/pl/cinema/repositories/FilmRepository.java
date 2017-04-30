package pl.cinema.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import pl.cinema.model.Film;

public interface FilmRepository extends CrudRepository<Film, Long>{
	
	public Film findById(long id);
	public List<Film> findAll();
}
