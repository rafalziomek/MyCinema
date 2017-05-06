package pl.cinema.services;

import java.util.List;

public interface CinemaService<T> {
	public List<T> getAll();
	public String getModelName();
	public String getModelCollectionName();
	public void add(T t);
}
