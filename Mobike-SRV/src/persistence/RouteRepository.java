package persistence;

import java.util.List;
import model.Route;
import persistence.exception.PersistenceException;

/** 
 * RouteRepository interface
 * @author Paolo, Bruno, Marco, Andrea
 * @see RouteMySql persistence.mysql.RouteMySql: implemented class
 * @version 2.0
 */
public interface RouteRepository {

	
	/**
	 * @param id
	 * @return Route
	 * @throws PersistenceException
	 */
	public Route routeFromId(long id) throws PersistenceException;
	
	/**
	 * @return List<Route>
	 * @throws PersistenceException
	 */
	public List<Route> getAllRoutes() throws PersistenceException;
	
	/**
	 * @param name
	 * @return Route
	 * @throws PersistenceException
	 */
	public List<Route> routeFromName(String name) throws PersistenceException;

	/**
	 * @param p Route
	 * @return id RouteID
	 * @throws PersistenceException
	 */
	public long addRoute(Route p) throws PersistenceException;
	
	/**
	 * @param p Route
	 * @throws PersistenceException
	 */
	public void removeRoute(Route p) throws PersistenceException;
	
	/**
	 * @param id
	 * @throws PersistenceException
	 */
	public void removeRouteFromId(long id) throws PersistenceException;
}
