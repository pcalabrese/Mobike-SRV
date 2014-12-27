package persistence.mysql;


import java.util.ArrayList;
import java.util.List;
import model.Path;
import persistence.PathRepository;
import javax.persistence.*;
import persistence.jpa.SingletonEMF;

public class PathMySQL implements PathRepository{

	private EntityManagerFactory emf = SingletonEMF.get();
	private EntityManager em = emf.createEntityManager();
	
	public PathMySQL() {}
	
	
	
	
	public Path pathFromId(long id){
		
		Path path = null;
		Query query = em.createQuery("select p from Path p where p.Id=:id");
		query.setParameter("id",id);
		path =(Path) query.getSingleResult();
		
		return path;
		
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
	public void addPath(Path p) throws PersistenceException {
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
			Path path = em.find(Path.class,p.getId());
			if(path!=null && path.equals(p))
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



	


	