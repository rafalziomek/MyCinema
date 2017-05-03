package validator;

import java.time.LocalDateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DatesValidator implements ConstraintValidator<BetweenDates, LocalDateTime> {

	@Override
	public void initialize(BetweenDates constraintAnnotation) {
		
		
	}

	@Override
	public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		return false;
	}

}
