package pl.cinema.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.cinema.model.UserRole;
import pl.cinema.repositories.UserRepository;
@Service
public class AppUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	
	@Transactional(readOnly=true)
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		pl.cinema.model.User user = userRepository.findByEmail(username);
		List<GrantedAuthority> authorities =
                buildUserAuthority(user.getUserRole());
		
		return buildUserForAuthentication(user, authorities);
	}
	
	private User buildUserForAuthentication(pl.cinema.model.User user,
			List<GrantedAuthority> authorities) {
			return new User(user.getEmail(), user.getPassword(),
				user.isEnabled(), true, true, true, authorities);
		}
	
	private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		// Build user's authorities
		for (UserRole userRole : userRoles) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
		}

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

		return Result;
	}
}
