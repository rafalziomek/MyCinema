package pl.cinema;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import pl.cinema.model.User;
@RunWith(SpringRunner.class)
@SpringBootTest

public class UserTests {
	
	@Autowired
	private UserInitializer userInitializer;
	@Test
	public void initializeUser() {
		List<User> user = userInitializer.initialize();
	}
}
