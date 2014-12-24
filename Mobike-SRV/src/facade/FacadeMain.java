package facade;

import java.time.Duration;
import java.util.List;

import model.Path;
import persistence.exception.PersistenceException;

public interface FacadeMain{

	
	public void addPath(String name, String description, String url,int length, Duration duration, long userId);
	
	public Path searchPath(Long id);
	
	public List<Path> retriveAllPaths();
	
}

