package pl.cinema.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import pl.cinema.model.Hall;
import pl.cinema.model.Projection;

public interface HallRepository extends CrudRepository<Hall, Long>{
	public List<Hall> findAll();
	public Hall findById(long hallId);
}
