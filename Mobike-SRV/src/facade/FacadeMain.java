package facade;

import java.util.List;
import java.util.Date;
import model.Path;


public interface FacadeMain{

	
	public void createPath(String name, String description,double length, long duration, Date uploadDate, String creatorEmail, String gpxString);
	
	public Path searchPath(Long id);
	
	public List<Path> retriveAllPaths();
	
}

