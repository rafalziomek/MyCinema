package pl.cinema.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.junit.validator.ValidateWith;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

@Entity
@Validated
public class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotNull
	@DateTimeFormat(pattern = "MM/dd/yyyy HH:mm")
	private LocalDateTime startDate;
	
	@DateTimeFormat(pattern = "MM/dd/yyyy HH:mm")
	private LocalDateTime endDate;
	
	@ManyToOne
	@JoinColumn(name = "hall_id", 
		foreignKey = @ForeignKey(name = "HALL_ID_FK"), nullable = false)
	private Hall hall;
	
	public Reservation() {
		
	}
	
	public Reservation(Hall hall, LocalDateTime startDate, int duration) {
		this.startDate = startDate;
		endDate = startDate.plusMinutes(duration).plusMinutes(15);
		this.hall = hall;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public Hall getHall() {
		return hall;
	}

	public void setHall(Hall hall) {
		this.hall = hall;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reservation other = (Reservation) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
}
