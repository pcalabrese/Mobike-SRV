package Controller;

import javax.ws.rs.core.Response;

// test comments for controllermain

/** 
 * Main Controller interface
 * @author Paolo, Bruno, Marco, Andrea
 * @see RouteServicesImpl Controller.RouteServicesImpl: implemented class
 * @version 3.0
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
	public Response getRoute(Long id);
	
	/**
	 * @return String
	 */
	public Response retrieveAllRoutes();

	/**
	 * @param id
	 * @return String
	 */
	public String getRoutegpx(Long id);
	
	
}

