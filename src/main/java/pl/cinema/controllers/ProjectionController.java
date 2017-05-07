package pl.cinema.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.cinema.model.Film;
import pl.cinema.model.Hall;
import pl.cinema.model.Projection;
import pl.cinema.model.Reservation;
import pl.cinema.services.FilmService;
import pl.cinema.services.HallService;
import pl.cinema.services.ProjectionService;

@Controller
@RequestMapping("/projections")
public class ProjectionController extends AbstractCinemaController<Projection> {
	
	private ProjectionService projectionService;
	
	@Autowired
	private HallService hallService;
	
	@Autowired
	private FilmService filmService;
	
	private Validator reservationValidator; 
	
	
	@Autowired
	public ProjectionController(ProjectionService projectionService, Validator reservationValidator) {
		super(projectionService, reservationValidator);
		this.projectionService = projectionService;
		this.reservationValidator = reservationValidator;
	}

	@GetMapping("/add")
	public String getAddProjection(Model model) {
		List<Hall> halls = hallService.getAll();
		List<Film> films = filmService.getAll();
		Projection projection = new Projection();
		Reservation res = new Reservation();
		res.setStartDate(LocalDateTime.now());
		projection.setReservation(new Reservation());
		model.addAttribute("projection", new Projection());
		model.addAttribute("halls", halls);
		model.addAttribute("films", films);
		return "projection/add";
	}
	
	@PostMapping("/add")
	public String addProjection(@Valid @ModelAttribute("projection") Projection projection, BindingResult result, Model model) {
		reservationValidator.validate(projection.getReservation(), result);
		if(result.hasErrors()) {
			List<Hall> halls = hallService.getAll();
			List<Film> films = filmService.getAll();
			model.addAttribute("films", films);
			model.addAttribute("halls", halls);
			model.addAttribute("projection", projection);
			return "projection/add";
		}
		LocalDateTime startDate = projection.getReservation().getStartDate();
		int duration = projection.getFilm().getDuration();
		projection.getReservation().setEndDate(startDate.plusMinutes(duration).plusMinutes(15));;
		projectionService.add(projection);
		return "redirect:/projections";
	}
}
