package Controller;

import javax.ws.rs.core.Response;

// test comments for controllermain

/** 
 * Main Controller interface
 * @author Paolo, Bruno, Marco, Andrea
 * @see RouteServicesImpl Controller.RouteServicesImpl: implemented class
 * @version 2.0
 */
public interface UserServices{
	
	/**
	 * @param long id
	 * @return String (JSON of USER)
	 */
	public Response getUser(long id, String token);
	
	/** 
	 * @return String
	 */
	public Response getAllUsers(String cryptedJson);
	
	/**
	 * @return String
	 */
	public Response createUser(String cryptedJson);

	
	
	public Response authenticateUser(String Cryptedjson);

	/**
	 * @param id
	 * @return String
	 */
	public String getUserRoutes(long id);
	
	/**
	 * @param id
	 * @return String
	 */
	public String getUserEvents(long id);
	
	
	
	
}

