package pl.cinema.filmTests;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;


import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import pl.cinema.ControllerTest;
import pl.cinema.initializers.FilmInitializer;
import pl.cinema.services.FilmService;
import pl.cinema.services.ProjectionService;


public abstract class FilmControllerTest extends ControllerTest  {
	

	@Autowired
	protected FilmService filmService;
	
	@Autowired
	protected ProjectionService projectionService;
	
	@Autowired
	private FilmInitializer filmInitializer;
	
	@Before
	public void filminitializer() {
		filmInitializer.initialize();
	}
	
}
