package facade;

import java.util.List;

import model.Path;


public interface FacadeMain{

	
	public void addPath(String name, String description, String url,int length, long duration, long userId);
	
	public Path searchPath(Long id);
	
	public List<Path> retriveAllPaths();
	
}

