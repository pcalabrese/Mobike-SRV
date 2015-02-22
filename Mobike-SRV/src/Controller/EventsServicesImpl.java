/**
 * 
 */
package Controller;

import java.util.List;

import persistence.EventRepository;
import persistence.exception.*;
import persistence.mysql.EventMySQL;

import model.Event;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.*;

/**
 * @author Paolo
 *
 */
@Path("/events")
public class EventsServicesImpl implements EventsServices {
	protected EventRepository eventRep;
	/**
	 * 
	 */
	public EventsServicesImpl() {
		eventRep = new EventMySQL();
	}

	
	@Override
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String createEvent(String json) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		Event e = gson.fromJson(json, Event.class);
		long insertedId = -1;
		
		try{
			insertedId = eventRep.addEvent(e);
		}
		catch (Exception e1){
			throw new UncheckedPersistenceException("Error adding event to the DataBase" + e1.getMessage());
		}
		
		return ""+insertedId+"";
	}

	
	

	
	@Override
	@GET
	@Path("/retrieveall")
	@Produces(MediaType.APPLICATION_JSON)
	public String retrieveAllEvents() {
		List<Event> allEvents = null;
		EventRepository eventRep = new EventMySQL();
		try {
			allEvents = eventRep.getAllEvents();
		} 
		catch (Exception e1){
			throw new UncheckedPersistenceException("Error retrieving allEvents from the DataBase" + e1.getMessage());
		}
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		
		
		return gson.toJson(allEvents,List.class);
	}
	
	@Override
	@GET
	@Path("/retrieve/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getEvent(@PathParam("id") Long id){
		Event event = null;
		System.out.println(id);
		try {
			event = eventRep.eventFromId(id);
		}
		catch (Exception e1){
			System.out.println("errore");
			throw new UncheckedPersistenceException("Error retrieving Event from the DataBase: "+ e1.getMessage());
		}
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		
		return gson.toJson(event, Event.class);
		
	}

	/* (non-Javadoc)
	 * @see Controller.EventsServices#associateRoute(java.lang.Long)
	 */
	@Override
	public String associateRoute(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
