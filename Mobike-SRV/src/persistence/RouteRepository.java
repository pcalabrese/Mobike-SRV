package persistence;

import java.util.List;

import model.Route;
import persistence.exception.PersistenceException;

public interface RouteRepository {

	
	public Route routeFromId(long id) throws PersistenceException;
	
	public List<Route> getAllRoutes() throws PersistenceException;
	
	public Route routeFromName(String name) throws PersistenceException;

	public void addRoute(Route p) throws PersistenceException;
	
	public void removeRoute(Route p) throws PersistenceException;
	
	public void removeRouteFromId(long id) throws PersistenceException;
}
