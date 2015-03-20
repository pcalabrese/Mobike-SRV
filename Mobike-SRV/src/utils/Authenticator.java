package utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import persistence.UserRepository;
import persistence.mysql.UserMySQL;

import model.User;

public class Authenticator {

	public Authenticator() {
		// TODO Auto-generated constructor stub
	}

	public boolean validateCryptedUser(String cryptedJson) throws Exception{
		if (cryptedJson != null) {
			Crypter crypter = new Crypter();

			String plainJson = null;

			
				plainJson = crypter.decrypt(cryptedJson);
			

			if (plainJson != null) {
				User user = null;
				UserRepository userRep = new UserMySQL();
				ObjectMapper mapper = new ObjectMapper();
				
					user = mapper.readValue(plainJson, User.class);
				
				boolean exists = false;
				if (user.getEmail() != null) {

					
						exists = userRep.userExists(user.getEmail());
					
					return exists;
				}

				else {
					if (user.getId() != null & user.getNickname() != null) {
						
							exists = userRep.userExists(user.getId(),
									user.getNickname());
						
					}
					return exists;
				}

			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	
	public boolean validatePlainUser(String plainJson) throws Exception {
		
			if (plainJson != null) {
				User user = null;
				UserRepository userRep = new UserMySQL();
				ObjectMapper mapper = new ObjectMapper();
				
					user = mapper.readValue(plainJson, User.class);
				
				boolean exists = false;
				if (user.getEmail() != null) {

					
						exists = userRep.userExists(user.getEmail());
					
					return exists;
				}

				else {
					if (user.getId() != null & user.getNickname() != null) {
						
							exists = userRep.userExists(user.getId(),
									user.getNickname());
						
					}
					return exists;
				}

			} else {
				return false;
			}
		
	}
	
	
	
	
	
	
	
	
	public boolean isAuthorized(long id, String cryptedJson) throws Exception{
		boolean exists = false;
		
			exists = validateCryptedUser(cryptedJson);
		
		
		if(exists){
			Crypter crypter = new Crypter();
			String plainJson = null;
			
			
				plainJson = crypter.decrypt(cryptedJson);
			
			
			ObjectMapper mapper = new ObjectMapper();
			User user = null;
			
			
				user = mapper.readValue(plainJson, User.class);
			
			
			if(user != null){
				return user.getId()==id;
			}
			
			}
			return false;
		}
}


