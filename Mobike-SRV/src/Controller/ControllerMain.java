package Controller;

/** 
 * Main Controller interface
 * @author Paolo, Bruno, Marco, Andrea
 * @version 2.0
 * @see ControllerMainImpl Controller.ControllerMainImpl: implemented class
 * 
 */
public interface ControllerMain{
	
	/**
	 * @param json
	 * @return String
	 */
	public String createRoute(String json);
	
	/**
	 * @param id
	 * @return String
	 */
	public String getRoute(Long id);
	
	/**
	 * @return String
	 */
	public String retrieveAllRoutes();

	/**
	 * @param id
	 * @return String
	 */
	public String getRoutegpx(Long id);
	
	
}

