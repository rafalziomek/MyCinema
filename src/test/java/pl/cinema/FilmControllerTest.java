package pl.cinema;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;


import org.junit.After;
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

import pl.cinema.services.FilmService;
import pl.cinema.services.ProjectionService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
@WebAppConfiguration
public abstract class FilmControllerTest {
	
	@Autowired
	private WebApplicationContext context;

	protected MockMvc mockMvc;
	
	@Autowired
	protected FilmService filmService;
	
	@Autowired
	protected ProjectionService projectionService;
	
	@Before
	public void initialize() {
		FilmInitializer.initializeFilms(filmService);
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity()) 
				.build();
	}
	
	@After
	public void clear() {
		filmService.clear();
		projectionService.clear();
	}
	
	
	
	

	
	
	
	
	
	
}
