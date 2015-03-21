package persistence.mysql;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import model.User;
import persistence.UserRepository;
import persistence.exception.PersistenceException;
import persistence.jpa.SingletonEMF;

public class UserMySQL implements UserRepository {

	private EntityManagerFactory emf = SingletonEMF.get();
	private EntityManager em = emf.createEntityManager();

	public UserMySQL() {
	}

	@Override
	public User userFromId(long id) throws PersistenceException {
		User user = null;
		TypedQuery<User> query = em.createQuery(
				"select u from User u where u.id=:id", User.class);
		query.setParameter("id", id);
		user = query.getSingleResult();
		return user;
	}
	
	@Override
	public boolean nicknameAvailable(String nickname) throws PersistenceException {
		TypedQuery<User> query = em.createNamedQuery("User.findByNickname", User.class);
		query.setParameter("nickname", nickname);
		
		if(query.getResultList().isEmpty())
			return true;
		else
			return false;
		
		
	}

	@Override
	public List<User> getAllUsers() throws PersistenceException {
		List<User> users = new ArrayList<User>();
		TypedQuery<User> query = em.createQuery("select u from User u",
				User.class);
		users = query.getResultList();
		return users;
	}

	@Override
	public User userFromEmail(String email) throws PersistenceException {
		List<User> users = new ArrayList<User>();
		User user = null;
		TypedQuery<User> query = em.createQuery(
				"select u from User u where u.email=:email", User.class);
		query.setParameter("email", email);
		users = query.getResultList();
		if (!users.isEmpty())
			user = users.get(0);
		return user;
	}

	@Override
	public long addUser(User u) throws PersistenceException {
		long insertedId = -1;
		try {
			em.getTransaction().begin();
			em.persist(u);
			em.getTransaction().commit();
			insertedId = u.getId();

		} catch (IllegalArgumentException e) {
			throw new PersistenceException(e.getMessage());
		} finally {

			em.close();
		}
		return insertedId;
	}

	@Override
	public void removeUser(User u) throws PersistenceException {

		try {
			em.getTransaction().begin();
			User user = em.find(User.class, u.getId());
			if (user != null && user.equals(u))
				em.remove(u);
			em.getTransaction().commit();
		} catch (IllegalArgumentException e) {
			throw new PersistenceException(e.getMessage());
		}

		finally {
			em.close();
		}
	}

	@Override
	public void removeUserFromId(long id) throws PersistenceException {
		try {
			em.getTransaction().begin();
			User u = em.find(User.class, id);
			if (u != null)
				em.remove(u);
			em.getTransaction().commit();
		} catch (IllegalArgumentException e) {
			throw new PersistenceException(e.getMessage());
		}

		finally {
			em.close();
		}
	}

	@Override
	public boolean userExists(String email) throws PersistenceException {
		boolean output = false;
		TypedQuery<User> query = em.createQuery(
				"select u from User u where u.email=:email", User.class);
		query.setParameter("email", email);

		if (!(query.getResultList().isEmpty()))
			output = true;

		return output;
	}

	@Override
	public boolean userExists(long id, String nickname)
			throws PersistenceException {
		boolean output = false;
		TypedQuery<User> query = em.createNamedQuery("User.findByIdAndNickname", User.class);
		query.setParameter("id", id);
		query.setParameter("nickname", nickname);
		
		
		

		if(!(query.getResultList().isEmpty()))
			output = true;
		
		return output;

	}

}
