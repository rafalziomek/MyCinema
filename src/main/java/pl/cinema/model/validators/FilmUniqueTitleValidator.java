package pl.cinema.model.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import pl.cinema.model.Film;
import pl.cinema.services.FilmService;

public class FilmUniqueTitleValidator implements Validator {
	@Autowired
	private FilmService filmService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Film.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Film film = (Film) target;
		filmService.getFilmByTitle(film.getTitle());
		
	}

}
