package pl.cinema.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import pl.cinema.model.Projection;

public interface ProjectionRepository extends CrudRepository<Projection, Long> {
	
	public List<Projection> findAll();
	public List<Projection> findByFilmId(Long filmId);
}
