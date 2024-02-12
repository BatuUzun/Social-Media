package com.ChattingApp.DAOImplementations;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ChattingApp.DAO.UserDAO;
import com.ChattingApp.Entity.User;
import jakarta.persistence.EntityManager;


@Repository
public class UserDAOImplementations implements UserDAO {

	private EntityManager entityManager;
	
	@Autowired
	public UserDAOImplementations(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void deleteByEmail(String email) {
		User user= entityManager.find(User.class, email);
		entityManager.remove(user);
	}

	@Override
	public User updateUser(User user) {
		User userOnTarget= entityManager.merge(user);
		return userOnTarget;
	}

	@Override
	public User findByEmail(String email) {
		return entityManager.find(User.class, email);	
	}

	@Override
	public void saveUser(User user) {
		entityManager.persist(user);
	}

	@Override
	public void updateIsVerifiedEmailByEmail(String email, boolean isVerifiedEmail) {
        User user = entityManager.find(User.class, email);
        if (user != null) {
            user.setIs_verified_email(isVerifiedEmail);
            entityManager.merge(user);
        }
    }

	

}
