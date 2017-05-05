package pl.cinema.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import pl.cinema.model.Hall;

public interface HallRepository extends CrudRepository<Hall, Long>{
	public List<Hall> findAll();
}
