package Controller;

import javax.ws.rs.core.Response;

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
	public Response createEvent(String wrappingJson);
	
	/**
	 * @param id
	 * @return String
	 */
	public Response getEvent(Long id);
	
	/**
	 * @return String
	 */
	public Response retrieveAllEvents();

	/**
	 * @param id
	 * @return String
	 */
	public String associateRoute(Long id);

	public Response removeEvent(String cryptedJson);
	
	
	
}

