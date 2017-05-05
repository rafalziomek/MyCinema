package pl.cinema.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import pl.cinema.model.Reservation;

public interface ReservationRepository extends CrudRepository<Reservation, Long>{
	public List<Reservation> findAll();
	@Query("select u from Reservation u where u.endDate > :#{#reservation.startDate} "
			+ "AND u.startDate < :#{#reservation.startDate} "
			+ "AND u.hall = :#{#reservation.hall}" )
	public List<Reservation> findDateOccurences(@Param("reservation") Reservation reservation);
}
