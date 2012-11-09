package net.ambientia.uftc.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class DaoBase<T> {
	
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public void save(T object) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(object);
	}

	public abstract List<T> getAll();

	public abstract Integer add(Integer parentId, T object);

	public abstract void delete(T object);

	public Integer count(){
	return getAll().size();	
		
	}
}
