/**
 * 
 */
package model;

 import java.util.List;

/**
 * @author Paolo
 *
 */
public class User {
	
	/**
	 * @param id
	 */
	

	private int Id;
	private String name;
	private String surname;
	private String username;
	private String email;
	private String password;
	private List<Integer> pathsId;
	
	
	public User(int id) {
		super();
		this.Id = id;
	}
	
	/**
	 * @param id
	 * @param name
	 * @param surname
	 * @param username
	 * @param email
	 * @param password
	 * @param pathsId
	 */
	public User(int id, String name, String surname, String username,
			String email, String password, List<Integer> pathsId) {
		super();
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.email = email;
		this.password = password;
		this.pathsId = pathsId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}
	
	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * @return the pathsId
	 */
	public List<Integer> getPathsId() {
		return pathsId;
	}
	
	/**
	 * @param pathsId the pathsId to set
	 */
	public void setPathsId(List<Integer> pathsId) {
		this.pathsId = pathsId;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return Id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		Id = id;
	}
	
	
	

}
