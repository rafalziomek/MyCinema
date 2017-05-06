package pl.cinema.model.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import pl.cinema.model.Film;

public class FilmUniqueValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Film.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
	}

}
