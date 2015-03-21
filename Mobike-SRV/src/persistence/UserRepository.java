package persistence;

import java.util.List;

import model.User;
import persistence.exception.PersistenceException;

public interface UserRepository {
	
	/**
	 * @param id
	 * @return Route
	 * @throws PersistenceException
	 */
	public User userFromId(long id) throws PersistenceException;
	
	/**
	 * @return List<User>
	 * @throws PersistenceException
	 */
	public List<User> getAllUsers() throws PersistenceException;
	
	/**
	 * @param name
	 * @return User
	 * @throws PersistenceException
	 */
	public User userFromEmail(String email) throws PersistenceException;

	/**
	 * @param u User
	 * @return id UserID
	 * @throws PersistenceException
	 */
	public long addUser(User u) throws PersistenceException;
	
	/**
	 * @param u User
	 * @throws PersistenceException
	 */
	public void removeUser(User u) throws PersistenceException;
	
	/**
	 * @param id
	 * @throws PersistenceException
	 */
	public void removeUserFromId(long id) throws PersistenceException;
	
	public boolean userExists(String email) throws PersistenceException;
	
	public boolean userExists(long id, String nickname) throws PersistenceException;

	boolean nicknameAvailable(String nickname) throws PersistenceException;
}
