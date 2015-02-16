package Controller;

// test comments for controllermain

/** 
 * Main Controller interface
 * @author Paolo, Bruno, Marco, Andrea
 * @see RouteServicesImpl Controller.RouteServicesImpl: implemented class
 * @version 2.0
 */
public interface RouteServices{
	
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

