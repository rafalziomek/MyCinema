package pl.cinema.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.cinema.model.User;
import pl.cinema.model.UserRole;
import pl.cinema.repositories.UserRoleRepository;
@Service
public class RolesService {
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	private final static String ADMIN = "ROLE_ADMIN";
	private final static String MANAGER = "ROLE_MANAGER";
	private final static String USER = "ROLE_USER";
	
	public void setAdminRole(User user) {
		setRole(user, ADMIN);
	}
	
	public void setUserRole(User user) {
		setRole(user, USER);
	}
	
	public void setManagerRole(User user) {
		setRole(user, MANAGER);
	}
	
	
	private void setRole(User user, String role) {
		UserRole userRole = new UserRole(user, role); 
		userRoleRepository.save(userRole);
	}
	
}
