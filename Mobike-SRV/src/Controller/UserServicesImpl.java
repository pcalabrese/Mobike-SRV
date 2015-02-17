package Controller;

import persistence.UserRepository;
import persistence.exception.*;
import persistence.mysql.UserMySQL;
import model.User;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.*;

@Path("/users")
public class UserServicesImpl implements UserServices {

	protected UserRepository userRep;
	
	public UserServicesImpl() {
		userRep = new UserMySQL();
	}

	
	@Override
	@GET
	@Path("/getUser")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUser(@NotNull @QueryParam("id") long id, @NotNull @QueryParam("token") String token) {
		if(token.equals("MW21B4YS4VEEZ5M1CDTBRYBC2FESXWKOXQBV92EAB591CA5CDC77D6E787E596F5E")){
			User u = null;
			try {
				u = userRep.userFromId(id);
			}
			catch(Exception e){
				throw new UncheckedPersistenceException("Error accessing user database");
			}
			
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
			return gson.toJson(u, User.class);
		}
		else{
			throw new UncheckedPersistenceException("Request not Valid");
		}
		
	}

	@Override
	public String getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String createUser(String json) {
		Gson gson = new GsonBuilder().create();
		User u = gson.fromJson(json,User.class);
		long insertedId = -1;
		try{
			insertedId = userRep.addUser(u);
		}
		catch (Exception e){
			throw new UncheckedPersistenceException("Error adding user in database");
		}
		
		return "" + insertedId + "";
	}

	@Override
	public String getUserRoutes(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUserEvents(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@POST
	@Path("/auth")
	@Produces(MediaType.TEXT_PLAIN)
	public String authenticateUser(String json){
		Gson gson = new GsonBuilder().create();
		User u = gson.fromJson(json, User.class);
		boolean exists;
		User u1 = null;
		String id;
		
		try {
			exists = userRep.userExists(u.getEmail());
		}
		catch (Exception e){
			throw new UncheckedPersistenceException("Error checking user account");
		}
		if(exists){
			try {
				u1 = userRep.userFromEmail(u.getEmail());
				id = String.valueOf(u1.getId());
			}
			catch (Exception e){
				throw new UncheckedPersistenceException("Error checking user account");
			}
		}
		else {
			 try{
				 id = String.valueOf(userRep.addUser(u));
			 }
			 catch (Exception e){
					throw new UncheckedPersistenceException("Error checking user account");
				}
		}
		
		return id;
	}

}
