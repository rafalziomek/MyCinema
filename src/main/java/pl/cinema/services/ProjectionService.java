package pl.cinema.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.cinema.model.Film;
import pl.cinema.model.Projection;
import pl.cinema.repositories.ProjectionRepository;

@Service
public class ProjectionService {
	@Autowired 
	private ProjectionRepository projectionRepository;
	
	public List<Projection> getAllProjections(Long filmId) {
		List<Projection> projections = projectionRepository.findAll();
		return projections;
	}
	
	public List<Projection> getAllProjectionsByFilmId(Long filmId) {
		List<Projection> projections = projectionRepository.findByFilmId(filmId);
		return projections;
	}
	
	public void saveProjection(Projection projection) {
		projectionRepository.save(projection);
	}

	
	
}
