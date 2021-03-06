package net.ambientia.uftc.dao;

import java.util.List;

import net.ambientia.uftc.domain.Challenge;
import net.ambientia.uftc.domain.ChallengeSportEvent;
import net.ambientia.uftc.domain.Uftc;
import net.ambientia.uftc.domain.User;
import net.ambientia.uftc.domain.Workout;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ChallengeDao extends DaoBase<Challenge> {

//	@Autowired
//	private SessionFactory sessionFactory;
	
	@Override
	@Transactional(propagation = Propagation.NEVER)
	public List<Challenge> getAll() {
		Query query = getCurrentSession().createQuery("FROM Challenge");
		return query.list();
	}
	
	public List<Challenge> getAllChallengesByOwner(User user) {
		Query query = getCurrentSession()
				.createQuery("FROM Challenge WHERE userId = :userId");
		query.setParameter("userId", user.getId());
		List<Challenge> challenges = query.list();
		return challenges;
	}

	public Integer add(Integer parentId, Challenge challenge) {
		challenge.setUftc((Uftc) getCurrentSession().get(Uftc.class, parentId));
		return (Integer) getCurrentSession().save(challenge);
	}

	public void delete(Challenge challenge) {
		Uftc uftc = challenge.getUftc();
		uftc.getChallenges().remove(challenge);
		getCurrentSession().delete(challenge);
	}

	public List<ChallengeSportEvent> getChallengeSportEvents(Challenge challenge) {
		return challenge.getChallengeSportEvents();
	}

	public Challenge getById(int challengeId) {
		Challenge challenge = (Challenge) getCurrentSession().get(Challenge.class,
				challengeId);
		Hibernate.initialize(challenge.getUsers());
		Hibernate.initialize(challenge.getNotApprovedUsers());
		Hibernate.initialize(challenge.getChallengeSportEvents());
		return challenge;
	}

	public boolean entityIsLocked(Challenge editedChallenge) {
		Challenge persistentChallenge = getById(editedChallenge.getId());
		if (persistentChallenge.getVersion() > editedChallenge.getVersion())
			return true;
		return false;
	}

}
