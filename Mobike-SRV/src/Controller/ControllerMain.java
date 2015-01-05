package Controller;

import java.util.List;
import model.Route;

public interface ControllerMain{
	
	public void createRoute(String json);
	
	public String getRoute(Long id);
	
	public String retrieveAllRoutes();
	
	
}

