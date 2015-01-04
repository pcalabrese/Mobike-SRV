package Controller;

import java.util.List;
import java.util.Date;
import model.Route;


public interface ControllerMain{

	
	//public void createRoute(String name, String description,double length, long duration, Date uploadDate, String creatorEmail, String gpxString);
	
	public String createRoute(Route route);
	
	public Route searchRoute(Long id);
	
	public List<Route> retriveAllRoutes();
	
}

