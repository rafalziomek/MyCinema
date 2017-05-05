package pl.cinema.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import pl.cinema.services.ProjectionService;

@Component
public class FilmDeleteValidator implements Validator{
	@Autowired
	private ProjectionService projectionService;
	@Override
	public boolean supports(Class<?> clazz) {
		return Film.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Film film = (Film) target;
		List<Projection> filmProjections = projectionService.getAllProjectionsByFilmId(film.getId());
		if(filmProjections.size() > 0) {
			errors.reject("cannot delete film", "This film is on projection, you can't delete it");
		}
	}

}
