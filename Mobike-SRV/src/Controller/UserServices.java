package Controller;

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
	public String getUser(long id, String token);
	
	/** 
	 * @return String
	 */
	public String getAllUsers();
	
	/**
	 * @return String
	 */
	public String createUser(String json);

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

