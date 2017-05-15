package pl.cinema.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="film")
public class Film {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Size(min = 1, max = 100, message = "Title should contains 1 to 100 characters")
	@NotNull
	private String title;
	
	@NotNull
	@Min(value = 1, message = "Duration of film should be greater then 1")
	private int duration;
	
	@Size(min = 5, max = 250, message = "Description should contains 5 to 250 characters")
	private String description;
	
	
	public Film() {
		
	}
	
	public Film(String title) {
		this.title = title;
	}

	public Film(String title, int duration, String description) {
		super();
		this.title = title;
		this.duration = duration;
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Film other = (Film) obj;
		if (id != other.id)
			return false;
		return true;
	}

	

	
	

	

	
	
	
}
