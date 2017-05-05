package pl.cinema.services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.Temporal;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.cinema.model.Reservation;
import pl.cinema.repositories.ReservationRepository;

@Service
public class ReservationService {
	@Autowired 
	private ReservationRepository reservationRepository;
	
	@Autowired
	private Validator validator;
	
	public List<Reservation> getAll() {
		return reservationRepository.findAll();
	}
	public void addReservation(Reservation reservation) {
		reservationRepository.save(reservation);
	}
	
	public List<Reservation> getDate(Reservation reservation) {
		return reservationRepository.findDateOccurences(reservation);
	}
}
