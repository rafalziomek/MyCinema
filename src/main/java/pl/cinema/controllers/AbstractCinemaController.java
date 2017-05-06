package pl.cinema.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;

import pl.cinema.services.CinemaService;

@Controller
public abstract class AbstractCinemaController<T> {
	
	private CinemaService<T> service;
	private Validator addOrUpdateValidator;
	
	public AbstractCinemaController(CinemaService<T> service, Validator addOrUpdateValidator) {
		this.service = service;
		this.addOrUpdateValidator = addOrUpdateValidator;
	}
	
	@GetMapping()
	protected String getAll(Model model) {
		List<T> records = service.getAll();
		model.addAttribute(service.getModelCollectionName(), records);
		return service.getModelName() + "/" + service.getModelCollectionName();
	}
	
	public String add(T record,  BindingResult result) {
		return addOrUpdate(record,result, "add");
	}
	
	public String edit(T record, BindingResult result) {
		return addOrUpdate(record, result, "edit");
	}
	
	private String addOrUpdate(T record, BindingResult result, String actionString) {
		addOrUpdateValidator.validate(record, result);
		if(result.hasErrors()) {
			return service.getModelName() + "/" + actionString;
		}
		service.add(record);
		return "redirect:/" + service.getModelCollectionName();
	}
	
}
