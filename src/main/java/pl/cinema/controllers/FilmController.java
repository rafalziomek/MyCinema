package pl.cinema.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.cinema.model.Film;
import pl.cinema.model.validators.*;
import pl.cinema.model.Projection;
import pl.cinema.services.CinemaService;
import pl.cinema.services.FilmService;
import pl.cinema.services.ProjectionService;

@Controller
@RequestMapping("/films")
public class FilmController extends AbstractCinemaController<Film>{
	
	
	private FilmService filmService;
	
	@Autowired
	private ProjectionService projectionService;
	
	@Autowired
	private Validator filmDeleteValidator;
	
	@Autowired
	public FilmController(FilmService filmService, Validator filmUniqueTitleValidator) {
		super(filmService, filmUniqueTitleValidator);
		this.filmService = filmService;
	}
	
	@GetMapping("/{id}")
	public String getFilmDetails(@PathVariable long id, Model model) {
		Film film = filmService.getFilmById(id);
		List<Projection> projections = projectionService.getAllProjectionsByFilmId(id);
		model.addAttribute("film", film);
		model.addAttribute("projections", projections);
		return "film/filmDetails";
	}
	
	@GetMapping("/add")
	public String getAddFilm(Model model) {
		model.addAttribute("film", new Film());
		return "film/add";
	}
	
	@PostMapping("/add")
	public String addFilm(@Valid @ModelAttribute("film") Film film, BindingResult result) {
		return add(film, result);
	}
	
	@GetMapping("/delete/{id}")
	public String getDeleteFilm(@PathVariable long id, Model model) {
		Film film = filmService.getFilmById(id);
		model.addAttribute("film", film);
		return "film/deleteFilm";
	}
	
	@PostMapping("/delete/{id}")
	public String deleteFilm(@PathVariable long id, @ModelAttribute(name="film") Film film,  BindingResult result) {
		filmDeleteValidator.validate(film, result);
		if(result.hasErrors()) {
			return "film/deleteFilm";
		} 
		filmService.deleteFilmById(id);
		return "redirect:/films";
	}
	
	@GetMapping("/edit/{id}")
	public String getEditFilm(@PathVariable long id, Model model) {
		Film filmToEdit = filmService.getFilmById(id);
		model.addAttribute("film", filmToEdit);
		return "film/editFilm";
	}
	
	@PostMapping("/edit/{id}")
	public String editFilm(@PathVariable long id, @Valid @ModelAttribute(name="film") Film film, BindingResult result) {
		film.setId(id);
		return edit(film, result);
	}
}
