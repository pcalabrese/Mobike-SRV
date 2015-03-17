package persistence.mysql;

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

	@Override
	public void editReview(Review r, String message, int rate)
			throws PersistenceException {
		Review review = em.find(Review.class, r.getId());
		em.getTransaction().begin();
		if (message != null) {
			review.setMessage(message);
		}
		if (rate != -1){
			review.setRate(rate);
		}
		em.getTransaction().commit();

	}

}
