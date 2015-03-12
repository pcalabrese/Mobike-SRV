package persistence;

import persistence.exception.PersistenceException;
import model.Review;
import model.ReviewPK;

public interface ReviewRepository {

	public Review reviewFromId(ReviewPK id) throws PersistenceException;
	
	public void removeReviewFromId(ReviewPK id) throws PersistenceException;
	
	public void addReview(Review r) throws PersistenceException;

	
}
