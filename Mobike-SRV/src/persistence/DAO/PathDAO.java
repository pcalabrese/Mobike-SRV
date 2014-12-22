package persistence.DAO;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Path;
import model.PathRepository;

import javax.persistence.*;

import persistence.jpa.SingletonEMF;

public class PathDAO implements PathRepository{

	private EntityManagerFactory emf = SingletonEMF.get();
	private EntityManager em = emf.createEntityManager();
	
	public PathDAO() {}
	
	
	
	
	public Path pathFromId(long id){
		
		Path autore = null;
		
		Query query = em.createQuery("select a from Autore a where a.cod=:codAutore");
		query.setParameter("idPath",id);
		autore =(Path) query.getSingleResult();
		
		return autore;
		
	}




	@Override
	public List<Path> allPath() {
		
		List<Path> paths = new ArrayList<Path>();
		
		Query query = em.createQuery("select p from Path p");
		
		paths =query.getResultList();
		
		return paths;
	}




	@Override
	public Path pathFromName(String name) {
		
		Path path = null;
		Query query = em.createQuery("select p from Path p where p.nome=:nome");
		query.setParameter("name",name);
		path =(Path) query.getSingleResult();
		
		return path;
	}




	@Override
	public boolean addPath(Path p) {
		try{
			em.getTransaction().begin();
			em.persist(p);
			em.getTransaction().commit();
		}catch(IllegalArgumentException e){
			e.getStackTrace();
		}
		finally{
			em.close();
		}
	}




	@Override
	public void removePath(Path p) {
		
		
		try{
			em.getTransaction().begin();
			Path autore = em.find(Path.class,p.getId());
			if(autore!=null && autore.equals(p))
				em.remove(p);
			em.getTransaction().commit();
		}catch(IllegalArgumentException e){
			e.getStackTrace();
		}
		
		finally{
			em.close();
		}
		
		
		
	}




	@Override
	public void removePathFromId(long id) {
		
		try{
			em.getTransaction().begin();
			Path p = em.find(Path.class,id);
			if(p!=null)
				em.remove(p);
			em.getTransaction().commit();
		}catch(IllegalArgumentException e){
			e.getStackTrace();
		}
		
		finally{
			em.close();
		}
		
		
	}
	
}



	


	