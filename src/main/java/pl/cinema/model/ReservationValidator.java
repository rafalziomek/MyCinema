package pl.cinema.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import pl.cinema.services.ReservationService;

@Component
public class ReservationValidator implements Validator {

	@Autowired
	private ReservationService reservationService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Reservation.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Reservation reservation = (Reservation) target;
		List<Reservation> wrongReservations = reservationService.getDate(reservation);
		if(wrongReservations.size() == 0) {
			errors.reject("hall is booked", "On this date hall is booked");
		}
	}

}
