package pl.cinema.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.cinema.model.Film;
import pl.cinema.model.validators.*;
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
	
	@Autowired
	private FilmDeleteValidator filmDeleteValidator;
	
	@GetMapping()
	public String getAll(Model model) {
		List<Film> films = filmService.getAll();
		model.addAttribute("films", films);
		
		return "film/films";
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
		return "film/addFilm";
	}
	
	@PostMapping("/add")
	public String addFilm(@Valid @ModelAttribute("film") Film film, BindingResult result) {
		if(result.hasErrors()) {
			return "film/addFilm";
		}
		filmService.addFilm(film);
		return "redirect:/films";
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
	@ResponseBody
	public String editFilm(@PathVariable long id, @ModelAttribute(name="film") Film film) {
		film.setId(id);
		filmService.addFilm(film);
		return "Succesfully updated";
	}
}
