package pl.cinema.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.Future;

@Entity
public class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Future
	private LocalDateTime startDate;
	
	@Transient
	private LocalDateTime endDate;
	
	public Reservation() {
		
	}
	
	public Reservation(Hall hall, LocalDateTime startDate, int duration) {
		this.startDate = startDate;
		endDate = startDate.plusMinutes(duration).plusMinutes(15);
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	
	
	
}
