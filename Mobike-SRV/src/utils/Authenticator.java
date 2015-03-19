package utils;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import persistence.UserRepository;
import persistence.exception.PersistenceException;
import persistence.exception.UncheckedPersistenceException;
import persistence.mysql.UserMySQL;
import utils.exception.AuthenticationException;
import utils.exception.UncheckedAuthenticationException;
import utils.exception.UncheckedCryptingException;
import model.User;

public class Authenticator {

	public Authenticator() {
		// TODO Auto-generated constructor stub
	}

	public boolean validateCryptedUser(String cryptedJson) throws AuthenticationException {
		if (cryptedJson != null) {
			Crypter crypter = new Crypter();

			String plainJson = null;

			try {
				plainJson = crypter.decrypt(cryptedJson);
			} catch (Exception e) {
				throw new AuthenticationException(e.getMessage());
			}

			if (plainJson != null) {
				User user = null;
				UserRepository userRep = new UserMySQL();
				ObjectMapper mapper = new ObjectMapper();
				try {
					user = mapper.readValue(plainJson, User.class);
				} catch (IOException e) {
					throw new AuthenticationException(e.getMessage());
				}
				boolean exists = false;
				if (user.getEmail() != null) {

					try {
						exists = userRep.userExists(user.getEmail());
					} catch (PersistenceException e) {
						throw new UncheckedPersistenceException(e.getMessage());
					}
					return exists;
				}

				else {
					if (user.getId() != null & user.getNickname() != null) {
						try {
							exists = userRep.userExists(user.getId(),
									user.getNickname());
						} catch (PersistenceException e) {
							throw new UncheckedPersistenceException(e.getMessage());
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
	
	
	public boolean validatePlainUser(String plainJson) throws AuthenticationException {
		
			if (plainJson != null) {
				User user = null;
				UserRepository userRep = new UserMySQL();
				ObjectMapper mapper = new ObjectMapper();
				try {
					user = mapper.readValue(plainJson, User.class);
				} catch (IOException e) {
					throw new AuthenticationException(e.getMessage());
				}
				boolean exists = false;
				if (user.getEmail() != null) {

					try {
						exists = userRep.userExists(user.getEmail());
					} catch (PersistenceException e) {
						throw new UncheckedPersistenceException(e.getMessage());
					}
					return exists;
				}

				else {
					if (user.getId() != null & user.getNickname() != null) {
						try {
							exists = userRep.userExists(user.getId(),
									user.getNickname());
						} catch (PersistenceException e) {
							throw new UncheckedPersistenceException(e.getMessage());
						}
					}
					return exists;
				}

			} else {
				return false;
			}
		
	}
	
	
	
	
	
	
	
	
	public boolean isAuthorized(long id, String cryptedJson) throws AuthenticationException{
		boolean exists = false;
		try {
			exists = validateCryptedUser(cryptedJson);
		} catch (AuthenticationException e1) {
			throw new UncheckedAuthenticationException(e1.getMessage());
		}
		
		if(exists){
			Crypter crypter = new Crypter();
			String plainJson = null;
			
			try {
				plainJson = crypter.decrypt(cryptedJson);
			} catch (Exception e) {
				throw new UncheckedCryptingException(e.getMessage(), e);
			}
			
			ObjectMapper mapper = new ObjectMapper();
			User user = null;
			
			try {
				user = mapper.readValue(plainJson, User.class);
			} catch (IOException e) {
				throw new AuthenticationException(e.getMessage());
			}
			
			if(user != null){
				return user.getId()==id;
			}
			
			}
			return false;
		}
}


