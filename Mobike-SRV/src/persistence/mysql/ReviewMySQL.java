package persistence.mysql;
/* ciaone */
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Review;
import model.ReviewPK;
import persistence.ReviewRepository;
import persistence.exception.PersistenceException;
import persistence.jpa.SingletonEMF;

public class ReviewMySQL implements ReviewRepository {

	private EntityManagerFactory emf = SingletonEMF.get();
	private EntityManager em = emf.createEntityManager();

	public ReviewMySQL() {
	}

	@Override
	public Review reviewFromId(ReviewPK id) throws PersistenceException {
		em.getTransaction().begin();
		Review review = null;
		review = em.find(Review.class, id);
		em.getTransaction().commit();

		return review;
	}

	@Override
	public void removeReviewFromId(ReviewPK id) throws PersistenceException {
		em.getTransaction().begin();
		Review review = null;
		review = em.find(Review.class, id);
		em.remove(review);
		em.getTransaction().commit();
	}

	@Override
	public void addReview(Review r) throws PersistenceException {
		
		em.getTransaction().begin();
		em.persist(r);
		em.getTransaction().commit();
	}
	
	public void updateReview(Review r) throws PersistenceException {
		
		Review review = em.find(Review.class, r.getReviewPK());
		
		em.getTransaction().begin();
		
		review.setMessage(r.getMessage());
		review.setRate(r.getRate());
		
		em.getTransaction().commit();
	
	}

}
