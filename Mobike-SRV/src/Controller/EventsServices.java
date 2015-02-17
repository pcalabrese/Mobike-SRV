package Controller;

// test comments for controllermain

/** 
 * Main Controller interface
 * @author Paolo, Bruno, Marco, Andrea
 * @see RouteServicesImpl Controller.RouteServicesImpl: implemented class
 * @version 2.0
 */
public interface EventsServices{
	
	/**
	 * @param json
	 * @return String
	 */
	public String createEvent(String json);
	
	/**
	 * @param id
	 * @return String
	 */
	public String getEvent(Long id);
	
	/**
	 * @return String
	 */
	public String retrieveAllEvents();

	/**
	 * @param id
	 * @return String
	 */
	public String associateRoute(Long id);
	
	
	
}

