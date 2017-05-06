package pl.cinema.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.cinema.model.Film;
import pl.cinema.model.Projection;
import pl.cinema.repositories.ProjectionRepository;

@Service
public class ProjectionService implements CinemaService<Projection>{
	@Autowired 
	private ProjectionRepository projectionRepository;

	public List<Projection> getAll() {
		List<Projection> projections = projectionRepository.findAll();
		return projections;
	}
	
	public List<Projection> getAllProjectionsByFilmId(Long filmId) {
		List<Projection> projections = projectionRepository.findByFilmId(filmId);
		return projections;
	}
	
	public void add(Projection projection) {
		projectionRepository.save(projection);
	}
	
	public void clear() {
		projectionRepository.deleteAll();
	}

	@Override
	public String getModelName() {
		return "projection";
	}

	@Override
	public String getModelCollectionName() {
		return "projections";
	}
	
	
}
