package net.ambientia.uftc.dao;

import java.util.List;
import net.ambientia.uftc.domain.Challenge;
import net.ambientia.uftc.domain.ChallengeSportEvent;
import net.ambientia.uftc.domain.Uftc;
import net.ambientia.uftc.domain.User;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("challengeDao")
@Transactional
public class ChallengeDao extends DaoBase<Challenge> {
	

	@Override
	@Transactional(propagation = Propagation.NEVER)
	public List<Challenge> getAll() {

		Session session = getCurrentSession();

		Query query = session.createQuery("FROM Challenge");

		return query.list();
	}

	public Integer add(Integer parentId, Challenge challenge) {
		Session session = getCurrentSession();
		challenge.setUftc((Uftc) session.get(Uftc.class, parentId));
		return (Integer) session.save(challenge);	
	}
	

	public void delete(Challenge challenge) {
		Session session = getCurrentSession();
		
		Uftc uftc = challenge.getUftc();
		uftc.getChallenges().remove(challenge);		
		
		session.delete(challenge);
			

	}


	public List<ChallengeSportEvent> getChallengeSportEvents(Challenge challenge) {
		return challenge.getChallengeSportEvents();
	}


	public Challenge getById(int challengeId) {

			Session session = getCurrentSession();
			 Challenge challenge= (Challenge) session.get(Challenge.class, challengeId);
			 return challenge;
		}
	
	public boolean entityIsLocked(Challenge editedChallenge) {
		Challenge persistentChallenge = getById(editedChallenge.getId());
		if (persistentChallenge.getVersion() > editedChallenge.getVersion())
			return true;
		return false;
	}
	
	

}
