package persistence;

import java.util.List;
import model.Event;
import persistence.exception.PersistenceException;

/** 
 * RouteRepository interface
 * @author Paolo, Bruno, Marco, Andrea
 * @see RouteMySql persistence.mysql.RouteMySql: implemented class
 * @version 2.0
 */
public interface EventRepository {

	
	/**
	 * @param id
	 * @return Event
	 * @throws PersistenceException
	 */
	public Event eventFromId(long id) throws PersistenceException;
	
	/**
	 * @return List<Event>
	 * @throws PersistenceException
	 */
	public List<Event> getAllEvents() throws PersistenceException;
	

	/**
	 * @param e Event
	 * @return id EventID
	 * @throws PersistenceException
	 */
	public long addEvent(Event e) throws PersistenceException;
	
	/**
	 * @param e Event
	 * @throws PersistenceException
	 */
	public void removeEvent(Event e) throws PersistenceException;
	
	/**
	 * @param id
	 * @throws PersistenceException
	 */
	public void removeEventFromId(long id) throws PersistenceException;
}
