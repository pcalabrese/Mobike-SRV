package Controller;

import persistence.UserRepository;
import persistence.exception.*;
import persistence.mysql.UserMySQL;
import model.Event;
import model.Route;
import model.User;
import model.Views;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import utils.Authenticator;
import utils.Crypter;
import utils.Wrapper;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RESTful User Endpoint
 * 
 * @author Paolo, Bruno, Marco, Andrea
 * @version 3.0
 * @see UserServices UserServices: Interface implemented by this class
 * @see UserMySQL UserMySQL: SQL DAO Class
 *
 */
@Path("/users")
public class UserServicesImpl implements UserServices {

	protected UserRepository userRep;

	public UserServicesImpl() {
		userRep = new UserMySQL();
	}

	@Override
	@GET
	@Path("/getDetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@NotNull @QueryParam("id") long id,
			@NotNull @QueryParam("token") String cryptedJson) {

		if (cryptedJson != null) {

			Authenticator auth = new Authenticator();
			boolean authorized = false;

			try {
				authorized = auth.isAuthorized(id, cryptedJson);
			} catch (Exception e1) {

				e1.printStackTrace();
			}

			if (authorized) {
				User user = null;
				try {

					user = userRep.userFromId(id);
				} catch (Exception e) {
					throw new UncheckedPersistenceException(
							"Error accessing user database" + e.getMessage());
				}

				if (user != null) {

					ObjectMapper mapper = new ObjectMapper();
					DateFormat dateFormat = new SimpleDateFormat(
							"yyyy/MM/dd HH:mm:ss");
					mapper.setDateFormat(dateFormat);
					mapper.setConfig(mapper.getSerializationConfig().withView(
							Views.UserDetailView.class));
					mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION,
							false);
					Map<String, String> map = null;
					String wrappingJson = null;
					try {
						Crypter crypter = new Crypter();
						String cryptedUserJson = crypter.encrypt(mapper
								.writerWithView(Views.UserDetailView.class)
								.writeValueAsString(user));

						map = new HashMap<String, String>();
						map.put("user", cryptedUserJson);

						Wrapper wrapper = new Wrapper();
						wrappingJson = wrapper.wrap(map);

					} catch (Exception e) {
						e.printStackTrace();
					}

					return Response
							.ok(wrappingJson, MediaType.APPLICATION_JSON)
							.build();

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
	@Path("/retrieveall")
	public Response getAllUsers(@QueryParam("token") String cryptedJson) {
		// if param is != null start the decryption
		if (cryptedJson != null) {

			Authenticator auth = new Authenticator();
			boolean exists = false;
			try {
				exists = auth.validateCryptedUser(cryptedJson);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			if (exists) {
				ObjectMapper mapper = new ObjectMapper();
				DateFormat dateFormat = new SimpleDateFormat(
						"yyyy/MM/dd HH:mm:ss");
				mapper.setDateFormat(dateFormat);
				List<User> users = null;
				try {
					users = userRep.getAllUsers();
				} catch (PersistenceException e) {
					e.printStackTrace();
				}

				mapper.setConfig(mapper.getSerializationConfig().withView(
						Views.UserGeneralView.class));
				mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);

				String cryptedUsersJson = null;
				Crypter crypter = new Crypter();

				try {
					cryptedUsersJson = crypter.encrypt(mapper
							.writeValueAsString(users));
				} catch (Exception e) {
					e.printStackTrace();
				}

				Map<String, String> map = new HashMap<String, String>();
				map.put("users", cryptedUsersJson);
				Wrapper wrapper = new Wrapper();
				String cryptedJsonOutput = null;
				try {
					cryptedJsonOutput = wrapper.wrap(map);
				} catch (Exception e) {
					e.printStackTrace();
				}

				return Response.ok(cryptedJsonOutput,
						MediaType.APPLICATION_JSON).build();
			}

			else {
				return Response.status(401).build();
			}
		}

		else {
			return Response.status(400).build();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUser(String cryptedJson) {

		if (cryptedJson != null) {
			ObjectMapper mapper = new ObjectMapper();
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			mapper.setDateFormat(dateFormat);
			Map<String, String> map = null;

			try {
				map = (Map<String, String>) mapper.readValue(cryptedJson,
						Map.class);
			} catch (IOException e5) {
				e5.printStackTrace();
			}

			if (map.get("user") != null) {
				Crypter crypter = new Crypter();
				User user = null;
				try {
					user = mapper.readValue(crypter.decrypt(map.get("user")),
							User.class);
				} catch (Exception e4) {

					e4.printStackTrace();
				}
				if (user.getEmail() != null & user.getName() != null
						& user.getNickname() != null
						& user.getSurname() != null) {

					boolean availableNick = false;

					try {
						availableNick = userRep.nicknameAvailable(user
								.getNickname());
					} catch (PersistenceException e3) {
						e3.printStackTrace();
					}

					if (availableNick) {
						long insertedId = -1;
						try {
							insertedId = userRep.addUser(user);
						} catch (Exception e) {
							throw new UncheckedPersistenceException(
									"Error adding user in database"
											+ e.getMessage());
						}

						if (insertedId != -1)
							user.setId(insertedId);
						else {
							return Response.status(500).build();
						}

						mapper = new ObjectMapper();
						mapper.setDateFormat(dateFormat);
						mapper.setConfig(mapper.getSerializationConfig()
								.withView(Views.UserGeneralView.class));
						mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION,
								false);

						String cryptedInsertedUserJson = null;
						try {
							cryptedInsertedUserJson = crypter
									.encrypt(mapper.writerWithView(
											Views.UserGeneralView.class)
											.writeValueAsString(user));
						} catch (Exception e1) {

							e1.printStackTrace();
						}

						Map<String, String> map1 = new HashMap<String, String>();
						map1.put("user", cryptedInsertedUserJson);

						String jsonOutput = null;
						try {
							jsonOutput = mapper.writeValueAsString(map1);
						} catch (JsonProcessingException e) {

							e.printStackTrace();
						}

						return Response.ok(jsonOutput,
								MediaType.APPLICATION_JSON).build();
					} else {
						return Response.status(409).build();
					}

				} else {
					return Response.status(400).build();
				}

			} else {
				return Response.status(400).build();
			}
		} else {
			return Response.status(400).build();
		}

	}

	@Override
	public String getUserRoutes(long id) {

		return null;
	}

	@Override
	public String getUserEvents(long id) {

		return null;
	}

	@Override
	@GET
	@Path("/auth")
	@Produces(MediaType.APPLICATION_JSON)
	public Response authenticateUser(@QueryParam("token") String cryptedJson) {
		ObjectMapper mapper = new ObjectMapper();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		mapper.setDateFormat(dateFormat);
		if (cryptedJson != null) {
			Crypter crypter = new Crypter();
			String json = null;
			try {
				json = crypter.decrypt(cryptedJson);
			} catch (Exception e1) {

				e1.printStackTrace();
			}

			User user = null;
			try {
				user = mapper.readValue(json, User.class);
			} catch (IOException e1) {

				e1.printStackTrace();
			}

			User user1 = null;
			if (user.getEmail() != null) {
				boolean exists = false;
				try {
					exists = userRep.userExists(user.getEmail());
				} catch (Exception e) {
					throw new UncheckedPersistenceException(
							"Error checking user account email "
									+ e.getMessage());
				}

				if (exists) {
					try {
						user1 = userRep.userFromEmail(user.getEmail());
					} catch (Exception e) {
						throw new UncheckedPersistenceException(
								"Error checking user account" + e.getMessage());
					}
				} else {
					return Response.status(401).build();
				}
			} else {
				if (user.getId() != null & user.getNickname() != null) {
					boolean exists2 = false;
					try {
						exists2 = userRep.userExists(user.getId(),
								user.getNickname());
					} catch (Exception e) {
						throw new UncheckedPersistenceException(
								"Error checking user account id and nickname "
										+ e.getMessage());
					}

					if (exists2) {
						try {
							user1 = userRep.userFromId(user.getId());
						} catch (Exception e) {
							throw new UncheckedPersistenceException(
									"Error checking user account"
											+ e.getMessage());
						}
					} else {
						return Response.status(401).build();
					}
				} else {
					return Response.status(400).build();
				}
			}

			if (user1 != null) {

				mapper.setConfig(mapper.getSerializationConfig().withView(
						Views.UserGeneralView.class));
				mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);

				String jsonOutput = null;
				try {
					jsonOutput = mapper.writerWithView(
							Views.UserGeneralView.class).writeValueAsString(
							user1);
				} catch (JsonProcessingException e) {

					e.printStackTrace();
				}
				String cryptedOutput = null;
				try {
					cryptedOutput = crypter.encrypt(jsonOutput);
				} catch (Exception e) {

					e.printStackTrace();
				}

				Map<String, String> map = new HashMap<String, String>();
				map.put("user", cryptedOutput);
				mapper = new ObjectMapper();

				mapper.setDateFormat(dateFormat);
				String cryptedjsonOutput = null;
				try {
					cryptedjsonOutput = mapper.writeValueAsString(map);
				} catch (JsonProcessingException e) {

					e.printStackTrace();
				}
				return Response.ok(cryptedjsonOutput,
						MediaType.APPLICATION_JSON).build();
			} else {
				return Response.status(500).build();
			}
		}

		else {
			return Response.status(400).build();
		}
	}

	@Override
	@GET
	@Path("/myevents")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMyEvents(@QueryParam("token") String cryptedJson) {

		if (cryptedJson != null) {
			Authenticator auth = new Authenticator();
			boolean exists = false;

			try {
				exists = auth.validateCryptedUser(cryptedJson);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (exists) {
				ObjectMapper mapper = new ObjectMapper();
				DateFormat dateFormat = new SimpleDateFormat(
						"yyyy/MM/dd HH:mm:ss");
				mapper.setDateFormat(dateFormat);
				Crypter crypter = new Crypter();
				List<Event> myevents = null;

				try {
					long userid = mapper.readValue(
							crypter.decrypt(cryptedJson), User.class).getId();

					myevents = userRep.userFromId(userid).getEventsOwned();
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (myevents != null) {
					Map<String, String> map = new HashMap<String, String>();
					mapper.setConfig(mapper.getSerializationConfig().withView(
							Views.EventGeneralView.class));
					mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION,
							false);
					String cryptedEventsJson = null;

					try {
						cryptedEventsJson = crypter.encrypt(mapper
								.writeValueAsString(myevents));
					} catch (Exception e) {
						e.printStackTrace();
					}

					map.put("events", cryptedEventsJson);
					Wrapper wrapper = new Wrapper();

					String wrappingJson = null;
					try {
						wrappingJson = wrapper.wrap(map);
					} catch (Exception e) {
						e.printStackTrace();
					}

					return Response
							.ok(wrappingJson, MediaType.APPLICATION_JSON)
							.build();

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
	@Path("/myroutes")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMyRoutes(@QueryParam("token") String cryptedJson) {

		if (cryptedJson != null) {
			Authenticator auth = new Authenticator();
			boolean exists = false;

			try {
				exists = auth.validateCryptedUser(cryptedJson);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (exists) {
				ObjectMapper mapper = new ObjectMapper();
				DateFormat dateFormat = new SimpleDateFormat(
						"yyyy/MM/dd HH:mm:ss");
				mapper.setDateFormat(dateFormat);
				Crypter crypter = new Crypter();
				List<Route> myroutes = null;

				try {
					long userid = mapper.readValue(
							crypter.decrypt(cryptedJson), User.class).getId();

					myroutes = userRep.userFromId(userid).getRouteList();
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (myroutes != null) {
					Map<String, String> map = new HashMap<String, String>();
					mapper.setConfig(mapper.getSerializationConfig().withView(
							Views.ItineraryGeneralView.class));
					mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION,
							false);
					String cryptedRoutesJson = null;

					try {
						cryptedRoutesJson = crypter.encrypt(mapper
								.writeValueAsString(myroutes));
					} catch (Exception e) {
						e.printStackTrace();
					}

					map.put("routes", cryptedRoutesJson);
					Wrapper wrapper = new Wrapper();

					String wrappingJson = null;
					try {
						wrappingJson = wrapper.wrap(map);
					} catch (Exception e) {
						e.printStackTrace();
					}

					return Response
							.ok(wrappingJson, MediaType.APPLICATION_JSON)
							.build();

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
	@Path("/myinvitations")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMyInvitations(String cryptedJson) {
		if (cryptedJson != null) {
			Authenticator auth = new Authenticator();
			boolean exists = false;

			try {
				exists = auth.validateCryptedUser(cryptedJson);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (exists) {
				ObjectMapper mapper = new ObjectMapper();
				DateFormat dateFormat = new SimpleDateFormat(
						"yyyy/MM/dd HH:mm:ss");
				mapper.setDateFormat(dateFormat);
				Crypter crypter = new Crypter();
				List<Event> myeventsInv = null;

				try {
					long userid = mapper.readValue(
							crypter.decrypt(cryptedJson), User.class).getId();

					myeventsInv = userRep.userFromId(userid).getEventsInvited();
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (myeventsInv != null) {
					Map<String, String> map = new HashMap<String, String>();
					mapper.setConfig(mapper.getSerializationConfig().withView(
							Views.EventGeneralView.class));
					mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION,
							false);
					String cryptedEventsJson = null;

					try {
						cryptedEventsJson = crypter.encrypt(mapper
								.writeValueAsString(myeventsInv));
					} catch (Exception e) {
						e.printStackTrace();
					}

					map.put("events", cryptedEventsJson);
					Wrapper wrapper = new Wrapper();

					String wrappingJson = null;
					try {
						wrappingJson = wrapper.wrap(map);
					} catch (Exception e) {
						e.printStackTrace();
					}

					return Response
							.ok(wrappingJson, MediaType.APPLICATION_JSON)
							.build();

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

}
