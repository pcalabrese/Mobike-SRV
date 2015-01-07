package Controller;

public interface ControllerMain{
	
	public String createRoute(String json);
	
	public String getRoute(Long id);
	
	public String retrieveAllRoutes();

	public String getRoutegpx(Long id);
	
	
}

