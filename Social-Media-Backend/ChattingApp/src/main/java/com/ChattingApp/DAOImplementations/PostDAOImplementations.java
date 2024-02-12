package com.ChattingApp.DAOImplementations;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ChattingApp.Constants.Constants;
import com.ChattingApp.DAO.PostDAO;
import com.ChattingApp.Entity.CommentPost;
import com.ChattingApp.Entity.LikePost;
import com.ChattingApp.Entity.Post;
import com.ChattingApp.Entity.UserProfile;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;

@Repository
public class PostDAOImplementations implements PostDAO {

	private EntityManager entityManager;

	@Autowired
	public PostDAOImplementations(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void savePost(Post post) {
		entityManager.persist(post);
	}

	@Override
	public void savePostComment(CommentPost comment) {
		entityManager.persist(comment);
	}

	@Override
	public void updatePost(Post post) {
		entityManager.merge(post);
	}

	@Override
	public Post findPostById(int id) {

		TypedQuery<Post> query = entityManager.createQuery("SELECT p FROM Post p WHERE p.id = :id", Post.class);
		query.setParameter("id", id);

		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	@Override
	public void savePostLike(LikePost like) {
		entityManager.persist(like);

	}

	@Override
	public void deletePostById(int id) {
		Post post = findPostById(id);
		if (post != null)
			entityManager.remove(post);
	}

	@Override
	public CommentPost findCommentById(int id) {
		return entityManager.find(CommentPost.class, id);
	}

	@Override
	public void deleteCommentById(int id) {
		CommentPost commentPost = findCommentById(id);
		
		if(commentPost != null)
			entityManager.remove(commentPost);
	}

	@Override
	public LikePost findLikeById(int id) {
		return entityManager.find(LikePost.class, id);
	}

	@Override
	public void deleteLikeById(int id) {
		LikePost like = findLikeById(id);
		
		if(like != null)
			entityManager.remove(like);
	}

	@Override
	public List<Post> findPostsByUsername(String username, int page) {
		
		int pageSize = Constants.getPageSize(); // Set your desired page size
        int offset = (page-1) * pageSize;

        return entityManager.createQuery(
                "SELECT p FROM Post p WHERE p.ownerUsername.username = :username", Post.class)
                .setParameter("username", username)
                .setFirstResult(offset)
                .setMaxResults(pageSize)
                .getResultList();
	}

	@Override
	public int getIdOfLikePost(LikePost like) {
		
		TypedQuery<Integer> query = entityManager.createQuery(
	            "SELECT lp.id FROM LikePost lp WHERE lp.targetPost = :targetPost AND lp.likeOwner = :likeOwner", Integer.class);
	        query.setParameter("targetPost", like.getTargetPost());
	        query.setParameter("likeOwner", like.getLikeOwner());
	        try {
	            return query.getSingleResult();
	        } catch (NoResultException e) {
	            return -1; // Or throw appropriate exception
	        }
	}

	@Override
	public List<CommentPost> getCommentsOfPost(int postId, int page) {
		
		int pageSize = Constants.getPageSize(); // Set your desired page size
        int offset = (page-1) * pageSize;
        
	    return entityManager.createQuery("SELECT c FROM CommentPost c WHERE c.targetPost.id = :postId", CommentPost.class)
                .setParameter("postId", postId)
                .setFirstResult(offset)
                .setMaxResults(pageSize)
                .getResultList();
	}

	@Override
	public List<Post> findPostOfFollowings(List<UserProfile> userProfiles, int page) {
		
		int pageSize = Constants.getPageSize(); // Set your desired page size
        int offset = (page-1) * pageSize;
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Post> query = builder.createQuery(Post.class);
        Root<Post> root = query.from(Post.class);
        
        // Joining UserProfile with its ownerUsername
        Join<Post, UserProfile> userProfileJoin = root.join("ownerUsername");
        
        query.select(root).distinct(true)
             .where(userProfileJoin.in(userProfiles))
             .orderBy(builder.desc(root.get("id")));

        TypedQuery<Post> typedQuery = entityManager.createQuery(query)
        										  .setFirstResult(offset)
                                                  .setMaxResults(Constants.getPageSize());
        return typedQuery.getResultList();
	}

	@Override
	public int findNumberOfPostOfFollowings(List<UserProfile> userProfiles) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
	    CriteriaQuery<Long> query = builder.createQuery(Long.class);
	    Root<Post> root = query.from(Post.class);

	    // Joining UserProfile with its ownerUsername
	    Join<Post, UserProfile> userProfileJoin = root.join("ownerUsername");

	    query.select(builder.countDistinct(root))
	         .where(userProfileJoin.in(userProfiles));

	    TypedQuery<Long> typedQuery = entityManager.createQuery(query);
	    return typedQuery.getSingleResult().intValue();
	}	

}
