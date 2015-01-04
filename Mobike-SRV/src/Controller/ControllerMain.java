package Controller;

import java.util.List;
import model.Route;

public interface ControllerMain{
	
	public void createRoute(String json);
	
	public Route searchRoute(Long id);
	
	public List<Route> retriveAllRoutes();
	
	
}

