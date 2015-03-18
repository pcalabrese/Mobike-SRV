package utils;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import persistence.UserRepository;
import persistence.exception.PersistenceException;
import persistence.mysql.UserMySQL;
import model.User;

public class Authenticator {

	public Authenticator() {
		// TODO Auto-generated constructor stub
	}

	public boolean validateCryptedUser(String cryptedJson) {
		if (cryptedJson != null) {
			Crypter crypter = new Crypter();

			String plainJson = null;

			try {
				plainJson = crypter.decrypt(cryptedJson);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (plainJson != null) {
				User user = null;
				UserRepository userRep = new UserMySQL();
				ObjectMapper mapper = new ObjectMapper();
				try {
					user = mapper.readValue(plainJson, User.class);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				boolean exists = false;
				if (user.getEmail() != null) {

					try {
						exists = userRep.userExists(user.getEmail());
					} catch (PersistenceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return exists;
				}

				else {
					if (user.getId() != null & user.getNickname() != null) {
						try {
							exists = userRep.userExists(user.getId(),
									user.getNickname());
						} catch (PersistenceException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
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
	
	
	public boolean validatePlainUser(String plainJson) {
		
			if (plainJson != null) {
				User user = null;
				UserRepository userRep = new UserMySQL();
				ObjectMapper mapper = new ObjectMapper();
				try {
					user = mapper.readValue(plainJson, User.class);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				boolean exists = false;
				if (user.getEmail() != null) {

					try {
						exists = userRep.userExists(user.getEmail());
					} catch (PersistenceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return exists;
				}

				else {
					if (user.getId() != null & user.getNickname() != null) {
						try {
							exists = userRep.userExists(user.getId(),
									user.getNickname());
						} catch (PersistenceException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					return exists;
				}

			} else {
				return false;
			}
		
	}
	
	
	
	
	
	
	
	
	public boolean isAuthorized(long id, String cryptedJson){
		boolean exists = validatePlainUser(cryptedJson);
		
		if(exists){
			Crypter crypter = new Crypter();
			String plainJson = null;
			
			try {
				plainJson = crypter.decrypt(cryptedJson);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ObjectMapper mapper = new ObjectMapper();
			User user = null;
			
			try {
				user = mapper.readValue(plainJson, User.class);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(user != null){
				return user.getId()==id;
			}
			
			}
			return false;
		}
}


