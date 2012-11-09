package net.ambientia.uftc.service;

import java.util.EnumSet;
import java.util.List;

import net.ambientia.uftc.dao.UftcDao;
import net.ambientia.uftc.domain.Uftc;
import net.ambientia.uftc.domain.Uftc.FieldTypes;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UftcService {
	@Autowired
	private UftcDao uftcDao;
	
	private SessionFactory sessionFactory;

	@Transactional(propagation = Propagation.NEVER)
	public List<Uftc> getAll() {
		return uftcDao.getAll();
	}

	public void add(Uftc uftc) {
		uftcDao.add(1, uftc);
	}

	public void save(Uftc uftc) {
		uftcDao.save(uftc);
	}

	public void setUftcDao(UftcDao uftcDao) {
		this.uftcDao = uftcDao;
	}

	public Uftc getById(int id){
		return uftcDao.getById(id);
	}
	
	public boolean validate(Uftc uftc) {
		return uftc.validate().size() == 0;
	}

	public EnumSet<FieldTypes> getValidationErrorList(Uftc uftc) {
		return uftc.validate();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
