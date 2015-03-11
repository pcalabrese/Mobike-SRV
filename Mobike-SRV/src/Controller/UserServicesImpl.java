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
import com.google.gson.*;
import com.sun.org.apache.xerces.internal.util.Status;

import utils.Crypter;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

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
	public Response getUser(@NotNull @QueryParam("id") long id, @NotNull @QueryParam("token") String cryptedJson) {
			
			if(cryptedJson != null){
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
				
				if(user.getId()==id){
					User user1 = null;
					try{
						
						user1 = userRep.userFromId(id);
					}
					catch(Exception e){
						throw new UncheckedPersistenceException("Error accessing user database" + e.getMessage());
					}
					
					mapper.setConfig(mapper.getSerializationConfig().withView(Views.UserDetailView.class));
					String jsonOutput = null;
					try {
						jsonOutput = mapper.writerWithView(Views.UserDetailView.class).writeValueAsString(user1);
					} catch (JsonProcessingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return Response.ok(jsonOutput, MediaType.APPLICATION_JSON).build();
				}
				else {
					return Response.status(401).build();
				}
				
				
			}
			else {
				return Response.status(400).build();
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
			throw new UncheckedPersistenceException("Error adding user in database" + e.getMessage());
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

	@Override
	@GET
	@Path("/auth")
	@Produces(MediaType.APPLICATION_JSON)
	public Response authenticateUser(@QueryParam("token") String cryptedJson){
		
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(cryptedJson);
		
		//if the map contains a field named "token" the request is correct and then we can go on with the decryption
		if(cryptedJson != null){
			
			// here i decrypt the String and build the User POJO to query the persistence
			Crypter crypter = new Crypter();
			String json = null;
			try {
				json = crypter.decrypt(cryptedJson);
				System.out.println(json);
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
			boolean exists;
			try {
				exists = userRep.userExists(user.getEmail());
			}
			catch (Exception e){
				throw new UncheckedPersistenceException("Error checking user account" + e.getMessage());
			}
			
			//if user exists we can get it from the database 
			if(exists){
				User user1;
				try {
					user1 = userRep.userFromEmail(user.getEmail());	
				}
				catch (Exception e){
					throw new UncheckedPersistenceException("Error checking user account" + e.getMessage());
				}
				
				mapper.setConfig(mapper.getSerializationConfig().withView(Views.UserGeneralView.class));
				mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
				
				String jsonOutput = null;
				try {
					jsonOutput = mapper.writerWithView(Views.UserGeneralView.class).writeValueAsString(user1);
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return Response.ok(jsonOutput, MediaType.APPLICATION_JSON).build();
				
				
				
			}
			else {
				return Response.status(401).build();	
			}
		}
			
		
		else{
			return Response.status(400).build();
		}
	}
		
}
