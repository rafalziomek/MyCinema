package pl.cinema.repositories;

import org.springframework.data.repository.CrudRepository;

import pl.cinema.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
	public User findByEmail(String email);
}
