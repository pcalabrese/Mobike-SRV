package Controller;

import persistence.UserRepository;
import persistence.exception.*;
import persistence.mysql.UserMySQL;
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
import utils.Crypter;
import java.io.IOException;
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
			Crypter crypter = new Crypter();
			String json = null;
			try {
				json = crypter.decrypt(cryptedJson);
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			ObjectMapper mapper = new ObjectMapper();
			User user = null;

			try {
				user = mapper.readValue(json, User.class);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			if (user.getId() == id) {
				User user1 = null;
				try {

					user1 = userRep.userFromId(id);
				} catch (Exception e) {
					throw new UncheckedPersistenceException(
							"Error accessing user database" + e.getMessage());
				}

				mapper.setConfig(mapper.getSerializationConfig().withView(
						Views.UserDetailView.class));
				mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
				String jsonOutput = null;
				try {
					jsonOutput = mapper.writerWithView(
							Views.UserDetailView.class).writeValueAsString(
							user1);
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return Response.ok(jsonOutput, MediaType.APPLICATION_JSON)
						.build();
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
			Crypter crypter = new Crypter();
			String json = null;

			try {
				json = crypter.decrypt(cryptedJson);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// after decryption, map the json to a User Object
			ObjectMapper mapper = new ObjectMapper();
			User user = null;
			try {
				user = mapper.readValue(json, User.class);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// check if User exists in Persistence
			boolean exists = false;
			try {
				exists = userRep.userExists(user.getId(), user.getNickname());
			} catch (PersistenceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// if exists then get the User's List, map it to json, encrypt and
			// return a Response.ok
			if (exists) {
				List<User> users = null;
				try {
					users = userRep.getAllUsers();
				} catch (PersistenceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				mapper.setConfig(mapper.getSerializationConfig().withView(
						Views.UserGeneralView.class));
				mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);

				String usersJson = null;
				try {
					usersJson = mapper.writeValueAsString(users);
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				String cryptedUsersJson = null;
				try {
					cryptedUsersJson = crypter.encrypt(usersJson);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mapper = new ObjectMapper();
				Map<String, String> map = new HashMap<String, String>();
				map.put("users", cryptedUsersJson);
				String cryptedJsonOutput = null;
				try {
					cryptedJsonOutput = mapper.writeValueAsString(map);
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return Response.ok(cryptedJsonOutput,
						MediaType.APPLICATION_JSON).build();
			}
			// else Return Response 401 Unauthorized
			else {
				return Response.status(401).build();
			}
		}
		// if param is == null return Response 400 Bad Request
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
			Map<String, String> map = null;

			try {
				map = (Map<String, String>) mapper.readValue(cryptedJson,
						Map.class);
			} catch (IOException e5) {
				// TODO Auto-generated catch block
				e5.printStackTrace();
			}
			String userCryptedJson = map.get("user");

			if (userCryptedJson != null) {
				Crypter crypter = new Crypter();
				String userJson = null;
				try {
					userJson = crypter.decrypt(userCryptedJson);
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

				if (user.getEmail() != null & user.getName() != null
						& user.getNickname() != null
						& user.getSurname() != null) {
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

					String insertedUserJson = null;
					mapper = new ObjectMapper();
					mapper.setConfig(mapper.getSerializationConfig().withView(
							Views.UserGeneralView.class));
					mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION,
							false);
					try {
						insertedUserJson = mapper.writerWithView(
								Views.UserGeneralView.class)
								.writeValueAsString(user);
					} catch (JsonProcessingException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

					String cryptedInsertedUserJson = null;
					try {
						cryptedInsertedUserJson = crypter
								.encrypt(insertedUserJson);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					Map<String, String> map1 = new HashMap<String, String>();
					map1.put("user", cryptedInsertedUserJson);

					String jsonOutput = null;
					try {
						jsonOutput = mapper.writeValueAsString(map1);
					} catch (JsonProcessingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					return Response.ok(jsonOutput, MediaType.APPLICATION_JSON)
							.build();

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
		if (cryptedJson != null) {
			Crypter crypter = new Crypter();
			String json = null;
			try {
				json = crypter.decrypt(cryptedJson);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			User user = null;
			try {
				user = mapper.readValue(json, User.class);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			User user1 = null;
			if(user.getEmail()!=null){
				boolean exists = false;
				try {
					exists = userRep.userExists(user.getEmail());
				} catch (Exception e) {
					throw new UncheckedPersistenceException(
							"Error checking user account email " + e.getMessage());
				}
				
				if (exists) {
					try {
						user1 = userRep.userFromEmail(user.getEmail());
					} catch (Exception e) {
						throw new UncheckedPersistenceException(
								"Error checking user account" + e.getMessage());
					}
				}
				else {
					return Response.status(401).build();
				}
			}
			else {
				if(user.getId() != null & user.getNickname()!=null){
					boolean exists2 = false;
					try {
						exists2 = userRep.userExists(user.getId(), user.getNickname());
					} catch (Exception e) {
						throw new UncheckedPersistenceException(
								"Error checking user account id and nickname " + e.getMessage());
					}
					
					if (exists2) {
						try {
							user1 = userRep.userFromId(user.getId());
						} catch (Exception e) {
							throw new UncheckedPersistenceException(
									"Error checking user account" + e.getMessage());
						}
					} else {
						return Response.status(401).build();
					}	
				}
				else{
					return Response.status(400).build();
				}
			}

			if (user1 != null) {
				
				mapper.setConfig(mapper.getSerializationConfig().withView(Views.UserGeneralView.class));
				mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);

				String jsonOutput = null;
				try {
					jsonOutput = mapper.writerWithView(
							Views.UserGeneralView.class).writeValueAsString(
							user1);
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String cryptedOutput = null;
				try {
					cryptedOutput = crypter.encrypt(jsonOutput);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Map<String, String> map = new HashMap<String, String>();
				map.put("user", cryptedOutput);
				mapper = new ObjectMapper();
				String cryptedjsonOutput = null;
				try {
					cryptedjsonOutput = mapper.writeValueAsString(map);
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return Response.ok(cryptedjsonOutput,
						MediaType.APPLICATION_JSON).build();
			} 
			else {
				return Response.status(500).build();
			}
		} 
		
		else {
			return Response.status(400).build();
		}
	}
}
