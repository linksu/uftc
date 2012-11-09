package net.ambientia.uftc.service;

import java.util.EnumSet;
import java.util.List;

import net.ambientia.uftc.dao.ChallengeDao;
import net.ambientia.uftc.dao.UserDao;
import net.ambientia.uftc.domain.Challenge;
import net.ambientia.uftc.domain.Uftc;
import net.ambientia.uftc.domain.User;
import net.ambientia.uftc.domain.User.FieldTypes;
import net.ambientia.uftc.domain.Workout;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

	// protected static Logger logger = Logger.getLogger("service");

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ChallengeDao challengeDao;
	
	private SessionFactory sessionFactory;

	public List<User> getAll() {
		return userDao.getAll();
	}
	
	public User getById(int id){
		return userDao.getById(id);
	}

	public void add(User user) {
		//Integer uftcId = user.getUftc().getId();
		userDao.add(1, user);
	}

	public void save(User user) {
		userDao.save(user);
	}
	
	public User getUserByUsername(String username) {
		List<User> users = getAll();
		User user = new User();
		for (int i = 0; i < users.size(); i++) {
			User currentUser = users.get(i);
			if(currentUser.getUsername().equals(username))
				user = currentUser;	
		} 
		return user;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public boolean isValid(User user) {
		return user.validate().size() == 0;

	}

	public EnumSet<FieldTypes> getValidationErrorList(User user) {
		return user.validate();
	}

	public void setUserUftc(User user, Uftc uftc) {
		user.setUftc(uftc);
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}



	public User setNewPropertiesToExistingUser(User user) {
		User persistedUser = getById(user.getId());
		persistedUser.setFirstName(user.getFirstName());
		persistedUser.setLastName(user.getLastName());
		persistedUser.setUsername(user.getUsername());	
		persistedUser.setUsername(user.getRetypePassword());	
		return persistedUser;
	}

	public boolean entityIsLocked(User user) {
		// TODO Auto-generated method stub
		return userDao.entityIsLocked(user);
	}

	public void addUserToChallenge(Challenge challenge,User user) {
		challenge.getUsers().add(user);
		challengeDao.save(challenge);
		
	}
}
