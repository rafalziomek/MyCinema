package pl.cinema.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "email", unique = true, nullable = false)
	@Email
	private String email;
	
	@Column(name = "password", nullable = false, length = 60)
	private String password;
	
	@Column(name = "enabled", nullable = false)
	private boolean enabled;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<UserRole> userRole;
	
	public User() {
		userRole = new HashSet<UserRole>();
	}

	public User(String email, String password, boolean enabled) {
		this.email = email;
		this.password = password;
		this.enabled = enabled;
		userRole = new HashSet<UserRole>();
	}

	public User(String email, String password,
		boolean enabled, Set<UserRole> userRole) {
		this.email = email;
		this.password = password;
		this.enabled = enabled;
		this.userRole = userRole;
	}

	
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public void addRole(UserRole userRole) {
		this.userRole.add(userRole);
	}
	
	public Set<UserRole> getUserRole() {
		return this.userRole;
	}

	public void setUserRole(Set<UserRole> userRole) {
		this.userRole = userRole;
	}

}