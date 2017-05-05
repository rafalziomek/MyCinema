package pl.cinema.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Future;


@Entity
@Table(name="projection")
public class Projection {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "film_id", 
		foreignKey = @ForeignKey(name = "FILM_ID_FK"), nullable = false)
	private Film film;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "reservation_id", 
		foreignKey = @ForeignKey(name = "RESERVATION_ID_FK"))
	private Reservation reservation;
	
	public Projection() {
	}
	
	
	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	
	
	public Projection(Film film) {
		this.film = film;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Projection other = (Projection) obj;
		if (id != other.id)
			return false;
		return true;
	}

	

	
	
	
 	
	
}
