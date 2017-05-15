package pl.cinema.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.cinema.model.Hall;
import pl.cinema.repositories.HallRepository;
@Service
public class HallService {
	
	@Autowired 
	private HallRepository hallRepository;
	
	public List<Hall> getAll() {
		return hallRepository.findAll();
	}
	
	public void addHall(Hall hall) {
		hallRepository.save(hall);
	}
	
	public Hall getHallById(long id) {
		return hallRepository.findById(id);
	}
	public long addExampleHallAndGetId() {
		Hall hall = new Hall();
		addHall(hall);
		List<Hall> halls = getAll();
		return halls.get(halls.size() - 1).getId();
	}
}

