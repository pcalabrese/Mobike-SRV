package persistence.jpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class SingletonEMF {

	private static final EntityManagerFactory emfInstance =
		Persistence.createEntityManagerFactory("biblioJPA");
	
	private SingletonEMF(){}
	
	
	public static EntityManagerFactory get(){
		return emfInstance;
	}
	
	
	  public static void closeEntityManager() {
	        emfInstance.close();
	    }
}
