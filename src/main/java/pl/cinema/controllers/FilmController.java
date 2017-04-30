package pl.cinema.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.cinema.model.Film;
import pl.cinema.model.Projection;
import pl.cinema.services.FilmService;
import pl.cinema.services.ProjectionService;

@Controller
@RequestMapping("/films")
public class FilmController {
	
	@Autowired
	private FilmService filmService;
	
	@Autowired
	private ProjectionService projectionService;
	
	@GetMapping()
	public String getAll(Model model) {
		List<Film> films = filmService.getAll();
		model.addAttribute("films", films);
		return "films";
	}
	
	@GetMapping("/{id}")
	public String getFilmDetails(@PathVariable long id, Model model) {
		Film film = filmService.getFilmById(id);
		List<Projection> projections = projectionService.getAllProjectionsByFilmId(id);
		model.addAttribute("film", film);
		model.addAttribute("projections", projections);
		return "filmDetails";
	}
	
	@GetMapping("/add")
	public String getAddFilm(Model model) {
		model.addAttribute("film", new Film());
		return "addFilm";
	}
	
	@PostMapping("/add")
	@ResponseBody
	public String addFilm(@ModelAttribute(name="film") Film film) {
		filmService.addFilm(film);
		return "Succesfully added";
	}
	
	@DeleteMapping("/delete/{id}")
	@ResponseBody
	public String deleteFilm(@PathVariable long id) {
		filmService.deleteFilmById(id);
		return "Succesfully deleted";
	}
	
	
}
