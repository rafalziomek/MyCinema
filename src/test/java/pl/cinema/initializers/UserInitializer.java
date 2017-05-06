package pl.cinema.initializers;


import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import pl.cinema.model.User;
import pl.cinema.repositories.UserRepository;
import pl.cinema.services.PasswordEncodeService;
import pl.cinema.services.RolesService;

@Service
public class UserInitializer extends Initializer<User>{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RolesService rolesService;
	
	@Autowired 
	private PasswordEncodeService encodeService;
	
	private User user;
	
	

	@Override
	protected boolean conditionInitialize() {
		return !userRepository.exists((long) 1);
	}

	@Override
	protected List<User> initializer() {
		user = new User();
		user.setEmail("myuser@example.pl");
		user.setEnabled(true);
		String hashedPass = encodeService.encode("password");
		user.setPassword(hashedPass);
		
		if(initializeDatabase) {
			userRepository.save(user);
			rolesService.setUserRole(user);
		}
		
		List<User> users = new ArrayList<User>();
		users.add(user);
		return users;
	}
	
}
