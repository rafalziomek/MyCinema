package pl.cinema.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.cinema.model.Film;
import pl.cinema.model.Projection;
import pl.cinema.services.CinemaService;
import pl.cinema.services.ProjectionService;

@Controller
@RequestMapping("/projections")
public class ProjectionController extends AbstractCinemaController<Projection> {
	
	private ProjectionService projectionService;
	
	@Autowired
	public ProjectionController(ProjectionService projectionService, Validator reservationValidator) {
		super(projectionService, reservationValidator);
		this.projectionService = projectionService;
	}
	

	

}
