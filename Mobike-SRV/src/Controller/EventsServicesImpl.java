/**
 * 
 */
package Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import persistence.EventRepository;
import persistence.UserRepository;
import persistence.exception.*;
import persistence.mysql.EventMySQL;
import persistence.mysql.UserMySQL;
import utils.Crypter;
import model.Event;
import model.User;
import model.Views;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

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

	@SuppressWarnings("unchecked")
	@Override
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response createEvent(String wrappingJson) {

		if (wrappingJson != null) {
			ObjectMapper mapper = new ObjectMapper();
			Map<String, String> map = null;

			try {
				map = mapper.readValue(wrappingJson, Map.class);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (map.containsKey("event")) {
				Crypter crypter = new Crypter();
				String json = null;

				try {
					json = crypter.decrypt(map.get("event"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Event event = null;
				try {
					event = mapper.readValue(json, Event.class);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				boolean exists = false;
				UserRepository userRep = new UserMySQL();

				try {
					exists = userRep.userExists(event.getOwner().getId(), event
							.getOwner().getNickname());
				} catch (PersistenceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (exists) {
					long insertedId = -1;

					try {
						insertedId = eventRep.addEvent(event);
					} catch (Exception e) {
						throw new UncheckedPersistenceException(
								"Error adding event to the DataBase"
										+ e.getMessage());
					}
					String outputId = "" + insertedId + "";
					return Response.ok(outputId, MediaType.TEXT_PLAIN).build();
				} else {
					return Response.status(401).build();
				}

			} else {
				return Response.status(400).build();
			}
		} else {
			return Response.status(400).build();
		}
	}

	@Override
	@GET
	@Path("/retrieveall")
	@Produces(MediaType.APPLICATION_JSON)
	public Response retrieveAllEvents() {
		List<Event> allEvents = null;
		EventRepository eventRep = new EventMySQL();
		try {
			allEvents = eventRep.getAllEvents();
		} catch (Exception e1) {
			throw new UncheckedPersistenceException(
					"Error retrieving allEvents from the DataBase"
							+ e1.getMessage());
		}

		if (!(allEvents.isEmpty())) {

			ObjectMapper mapper = new ObjectMapper();
			mapper.setConfig(mapper.getSerializationConfig().withView(
					Views.EventGeneralView.class));
			mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);

			String json = null;
			try {
				json = mapper.writeValueAsString(allEvents);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Crypter crypter = new Crypter();
			String cryptedJson = null;
			try {
				cryptedJson = crypter.encrypt(json);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			Map<String, String> map = new HashMap<String, String>();
			map.put("event", cryptedJson);
			mapper = new ObjectMapper();
			String jsonOutput = null;

			try {
				jsonOutput = mapper.writeValueAsString(map);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return Response.ok(jsonOutput, MediaType.APPLICATION_JSON).build();

		} else {
			return Response.status(404).build();
		}

	}

	@Override
	@GET
	@Path("/retrieveallws")
	@Produces(MediaType.APPLICATION_JSON)
	public Response retrieveAllEventsWithState(
			@QueryParam("user") String cryptedJson) {

		if (cryptedJson != null) {

			Crypter crypter = new Crypter();

			String plainUserJson = null;

			try {
				plainUserJson = crypter.decrypt(cryptedJson);
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			ObjectMapper mapper = new ObjectMapper();
			User user = null;
			try {
				user = mapper.readValue(plainUserJson, User.class);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			if (user.getId() != null & user.getNickname() != null) {
				UserRepository userRep = new UserMySQL();
				boolean exists = false;
				try {
					exists = userRep.userExists(user.getId(),
							user.getNickname());
				} catch (PersistenceException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				if (exists) {

					List<Event> allEvents = null;
					EventRepository eventRep = new EventMySQL();
					try {
						allEvents = eventRep.getAllEvents();
					} catch (Exception e1) {
						throw new UncheckedPersistenceException(
								"Error retrieving allEvents from the DataBase"
										+ e1.getMessage());
					}

					if (!(allEvents.isEmpty())) {
						for(Event e: allEvents){
							e.setUserStateByUserId(user.getId());
						}
						
						mapper.setConfig(mapper.getSerializationConfig()
								.withView(Views.EventGeneralView.class));
						mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION,
								false);

						String json = null;
						try {
							json = mapper.writeValueAsString(allEvents);
						} catch (JsonProcessingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						
						String cryptedOutputJson = null;
						try {
							cryptedOutputJson = crypter.encrypt(json);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						Map<String, String> map = new HashMap<String, String>();
						map.put("event", cryptedOutputJson);
						mapper = new ObjectMapper();
						String jsonOutput = null;

						try {
							jsonOutput = mapper.writeValueAsString(map);
						} catch (JsonProcessingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						return Response.ok(jsonOutput,
								MediaType.APPLICATION_JSON).build();

					} else {
						return Response.status(404).build();
					}

				} else {
					return Response.status(401).build();
				}

			}
			else {
				return Response.status(400).build();
			}
		} else {
			return Response.status(400).build();
		}

	}

	@Override
	@GET
	@Path("/retrieve/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEvent(@PathParam("id") Long id) {
		Event event = null;
		String json = null;
		EventRepository eventRep = new EventMySQL();
		ObjectMapper objectMapper = new ObjectMapper();

		// take Event from db
		try {
			event = eventRep.eventFromId(id);
		} catch (Exception e1) {
			throw new UncheckedPersistenceException(
					"Error retrieving Event from the DataBase: "
							+ e1.getMessage());
		}

		if (event != null) {

			// set the view
			objectMapper.setConfig(objectMapper.getSerializationConfig()
					.withView(Views.EventDetailView.class));
			objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);

			// starting JSON serialization
			try {
				json = objectMapper.writerWithView(Views.EventDetailView.class)
						.writeValueAsString(event);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// inizialize Crypter and JSON encrypted string
			Crypter crypter = new Crypter();
			String cryptedJson = null;

			// start encrypting JSON
			try {
				cryptedJson = crypter.encrypt(json);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Map<String, String> map = new HashMap<String, String>();
			map.put("event", cryptedJson);

			objectMapper = new ObjectMapper();
			String outputJson = null;

			try {
				outputJson = objectMapper.writeValueAsString(map);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return Response.ok(outputJson, MediaType.APPLICATION_JSON).build();

		}

		// event=null -> id doesnt exist in the db
		else {
			return Response.status(404).build();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Controller.EventsServices#associateRoute(java.lang.Long)
	 */
	@Override
	public String associateRoute(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	@POST
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response removeEvent(String cryptedJson) {
		EventRepository eventRep = new EventMySQL();

		if (cryptedJson != null) {
			ObjectMapper mapper = new ObjectMapper();
			Map<String, String> map = null;

			try {
				map = (Map<String, String>) mapper.readValue(cryptedJson,
						Map.class);
			} catch (IOException ioe) {
				// TODO Auto-generated catch block
				ioe.printStackTrace();
			}

			String userCryptedJson = map.get("user");
			String eventCryptedJson = map.get("event");

			if (userCryptedJson != null && eventCryptedJson != null) {
				Crypter crypter = new Crypter();
				String userJson = null;
				String eventJson = null;

				try {
					userJson = crypter.decrypt(userCryptedJson);
				} catch (Exception e4) {
					// TODO Auto-generated catch block
					e4.printStackTrace();
				}
				try {
					eventJson = crypter.decrypt(eventCryptedJson);
				} catch (Exception e4) {
					// TODO Auto-generated catch block
					e4.printStackTrace();
				}

				User user = null;
				try {
					user = mapper.readValue(userJson, User.class);
				} catch (IOException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}

				Event event = null;
				try {
					event = mapper.readValue(eventJson, Event.class);
				} catch (IOException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}

				if (event.getOwner().getId() == user.getId()) {
					try {
						eventRep.removeEvent(event);
					} catch (PersistenceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					return Response.ok().build();

				} else {
					return Response.status(401).build();
				}

			} else {
				return Response.status(400).build();

			}

		} else {
			return Response.status(400).build();
		}

	}

}
