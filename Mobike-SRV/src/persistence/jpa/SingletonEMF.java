package persistence.jpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author bruno, paolo, marco, andrea
 * @version 1.0
 */
public final class SingletonEMF {

	
	private static final EntityManagerFactory emfInstance =
		Persistence.createEntityManagerFactory("Mobike-SRV");
	
	/**
	 * default empty constructor
	 */
	private SingletonEMF(){}
	
	
	
	/**
	 * @return
	 */
	public static EntityManagerFactory get(){
		return emfInstance;
	}
	
	
	
	  /**
	 * 
	 */
	public static void closeEntityManager() {
	        emfInstance.close();
	    }
	
}
