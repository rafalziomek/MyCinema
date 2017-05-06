package pl.cinema.initializers;

import java.util.List;

import org.springframework.stereotype.Service;
@Service
public abstract class Initializer<T> {
	protected boolean initializeDatabase;
	
	protected abstract boolean conditionInitialize();
	
	protected abstract List<T> initializer();
	
	public List<T> initialize() {
		if(conditionInitialize()) {
			initializeDatabase = true;
		}
		else {
			initializeDatabase = false;
		}
		return initializer();
	}
}
