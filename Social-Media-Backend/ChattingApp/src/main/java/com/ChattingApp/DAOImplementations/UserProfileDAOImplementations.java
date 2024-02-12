package com.ChattingApp.DAOImplementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ChattingApp.DAO.UserProfileDAO;
import com.ChattingApp.Entity.UserProfile;
import com.ChattingApp.Entity.UserRelationship;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository
public class UserProfileDAOImplementations implements UserProfileDAO {
	
	private EntityManager entityManager;
	
	@Autowired
	public UserProfileDAOImplementations(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	@Override
	public void saveUsername(UserProfile userDetail) {
		entityManager.persist(userDetail);
		
	}

	@Override
	public UserProfile findProfileByEmail(String email) {
				
		TypedQuery<UserProfile> query = entityManager.createQuery(
	            "SELECT up FROM UserProfile up WHERE up.user.email = :email",
	            UserProfile.class
	        );
	        query.setParameter("email", email);

	        try {
	            return query.getSingleResult();
	        } catch (NoResultException e) {
	            return null;
	        }
		
	}

	@Override
	public List<UserProfile> searchUsernameByUsername(String username) {
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserProfile> criteriaQuery = criteriaBuilder.createQuery(UserProfile.class);

        Root<UserProfile> root = criteriaQuery.from(UserProfile.class);
        criteriaQuery.select(root)
                     .where(criteriaBuilder.like(root.get("username"), username + "%"));

        TypedQuery<UserProfile> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(0);
        query.setMaxResults(10);
        return query.getResultList();
		
	}

	@Override
	public void updateUserProfile(UserProfile userDetail) {
		entityManager.merge(userDetail);
	}
	
	@Override
	public UserProfile findProfileByUsername(String username) {

		TypedQuery<UserProfile> query = entityManager.createQuery(
	            "SELECT up FROM UserProfile up WHERE up.username = :username",
	            UserProfile.class
	        );
	        query.setParameter("username", username);

	        try {
	            return query.getSingleResult();
	        } catch (NoResultException e) {
	            return null;
	        }
	}
	
	@Override
	public void saveUserRelationship(UserRelationship userRelationship) {
		entityManager.persist(userRelationship);
	}
	@Override
	public UserRelationship findUserRelationshipById(long id) {
		return entityManager.find(UserRelationship.class, id);
	}
	@Override
	public boolean deleteUserRelationshpiById(long id) {
		UserRelationship userRelationship = findUserRelationshipById(id);		
		if(userRelationship != null) {
			entityManager.remove(userRelationship);
			return true;
		}
		return false;
			
	}
	@Override
	public long findIdOfRelationship(UserProfile follower, UserProfile following) {
		try {
			TypedQuery<Long> query = entityManager.createQuery(
				    "SELECT ur.id FROM UserRelationship ur WHERE ur.follower = :follower AND ur.following = :following", Long.class);
				query.setParameter("follower", follower);
				query.setParameter("following", following);
				Long relationshipId = query.getSingleResult();

            return relationshipId;
        } catch (NoResultException e) {
            return -1; // Relationship not found
        }
		
	}
	@Override
	public void uploadPicture(String image, String username) {
		UserProfile userProfile = entityManager.find(UserProfile.class, username);
		byte[] byteArray = image.getBytes();
        userProfile.setImageData(byteArray);
        entityManager.merge(userProfile);
	}
	@Override
	public List<UserRelationship> findUserRelationship(UserProfile userProfile) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserRelationship> query = builder.createQuery(UserRelationship.class);
        Root<UserRelationship> root = query.from(UserRelationship.class);

        query.select(root).where(
            builder.or(
                builder.equal(root.get("follower"), userProfile),
                builder.equal(root.get("following"), userProfile)
            )
        );

        return entityManager.createQuery(query).getResultList();
	}
}
