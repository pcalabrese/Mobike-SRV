/**
 * 
 */
package Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import persistence.EventRepository;
import persistence.exception.*;
import persistence.mysql.EventMySQL;
import utils.Authenticator;
import utils.Crypter;
import utils.Wrapper;
import utils.exception.UncheckedAuthenticationException;
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

	@Override
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response createEvent(String wrappingJson) {

		if (wrappingJson != null) {
			Wrapper wrapper = new Wrapper();
			Map<String, String> map = null;

			try {
				map = wrapper.unwrap(wrappingJson);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (map.containsKey("event") & map.containsKey("user")) {
				Crypter crypter = new Crypter();
				Authenticator auth = new Authenticator();
				ObjectMapper mapper = new ObjectMapper();
				Event event = null;

				try {

					event = mapper.readValue(crypter.decrypt(map.get("event")),
							Event.class);
				} catch (Exception e) {
					e.printStackTrace();
				}

				boolean authorized = false;

				try {
					authorized = auth.isAuthorized(event.getOwner().getId(),
							map.get("user"));

				} catch (Exception e1) {
					e1.printStackTrace();
				}

				if (authorized) {
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
			e1.printStackTrace();
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
				e.printStackTrace();
			}

			Crypter crypter = new Crypter();
			String cryptedJson = null;
			try {
				cryptedJson = crypter.encrypt(json);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			Map<String, String> map = new HashMap<String, String>();
			map.put("event", cryptedJson);
			Wrapper wrapper = new Wrapper();
			String jsonOutput = null;

			try {
				jsonOutput = wrapper.wrap(map);
			} catch (Exception e) {
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

			
			Authenticator auth = new Authenticator();
			boolean exists = false;
			try {
				exists = auth.validateCryptedUser(cryptedJson);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
			if (exists) {
					Crypter crypter = new Crypter();
					ObjectMapper mapper = new ObjectMapper();
					User user = null;
					
					try {
						user = mapper.readValue(crypter.decrypt(cryptedJson), User.class);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					
					List<Event> allEvents = null;
					EventRepository eventRep = new EventMySQL();
					try {
						allEvents = eventRep.getAllEvents();
					} catch (Exception e1) {
						e1.printStackTrace();
					}

					if (!(allEvents.isEmpty())) {
						for (Event e : allEvents) {
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
							e.printStackTrace();
						}

						String cryptedOutputJson = null;
						try {
							cryptedOutputJson = crypter.encrypt(json);
						} catch (Exception e1) {
							e1.printStackTrace();
						}

						Map<String, String> map = new HashMap<String, String>();
						map.put("event", cryptedOutputJson);
						Wrapper wrapper = new Wrapper();
						String jsonOutput = null;

						try {
							jsonOutput = wrapper.wrap(map);
						} catch (Exception e) {
							e.printStackTrace();
						}

						return Response.ok(jsonOutput,MediaType.APPLICATION_JSON).build();

					} else {
						return Response.status(404).build();
					}

				} else {
					return Response.status(401).build();
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
				e.printStackTrace();
			}

			// inizialize Crypter and JSON encrypted string
			Crypter crypter = new Crypter();
			String cryptedJson = null;

			// start encrypting JSON
			try {
				cryptedJson = crypter.encrypt(json);
			} catch (Exception e) {
				e.printStackTrace();
			}

			Map<String, String> map = new HashMap<String, String>();
			map.put("event", cryptedJson);

			Wrapper wrapper = new Wrapper();
			String outputJson = null;

			try {
				outputJson = wrapper.wrap(map);
			} catch (Exception e) {
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

	@Override
	@POST
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response removeEvent(String wrappingJson) {

		if (wrappingJson != null) {

			Wrapper wrapper = new Wrapper();
			Map<String, String> map = null;
			try {
				map = wrapper.unwrap(wrappingJson);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			if (map.containsKey("event") & map.containsKey("user")) {

				Authenticator auth = new Authenticator();
				ObjectMapper mapper = new ObjectMapper();
				Crypter crypter = new Crypter();

				Event event = null;

				try {
					event = mapper.readValue(
							crypter.decrypt(map.get("event")), Event.class);
				} catch (Exception e2) {
					e2.printStackTrace();
				}

				boolean authorized = false;
				try {
					authorized = auth.isAuthorized(event.getOwner().getId(), map.get("user"));
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				if (authorized) {

					try {
						eventRep.removeEventFromId(event.getId());

					} catch (PersistenceException e) {
						e.printStackTrace();
						throw new UncheckedPersistenceException(
								"Error Removing Event from DB");
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

	@Override
	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateEvent(String wrappingJson) {

		Wrapper wrapper = new Wrapper();

		Map<String, String> map = null;
		try {
			map = wrapper.unwrap(wrappingJson);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		if (map != null) {

			if (map.get("event") != null & map.get("user") != null) {

				ObjectMapper mapper = new ObjectMapper();
				Event event = null;
				Crypter crypter = new Crypter();
				try {
					event = mapper.readValue(crypter.decrypt(map.get("event")),
							Event.class);
				} catch (Exception e2) {
					e2.printStackTrace();
				}

				Authenticator auth = new Authenticator();
				boolean authorized = false;
				try {
					authorized = auth.isAuthorized(event.getOwner().getId(),
							map.get("user"));
				} catch (Exception e1) {
					e1.printStackTrace();
					throw new UncheckedAuthenticationException();
				}

				if (authorized) {

					try {
						eventRep.updateEvent(event);

					} catch (PersistenceException e) {
						e.printStackTrace();
						throw new UncheckedPersistenceException(
								"Error updating event");
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
