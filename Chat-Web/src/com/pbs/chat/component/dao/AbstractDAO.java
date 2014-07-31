package com.pbs.chat.component.dao;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractDAO<T> {

	protected Log logger = LogFactory.getLog(getClass());

	protected HibernateTemplate hibernateTemplate;

	@Autowired
	protected SessionFactory sessionFactory;

	@PostConstruct
	public void post() {
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	@Transactional
	public Serializable save(T t) {
		logger.info("Got request to save object: " + t);

		final Serializable id = hibernateTemplate.save(t);

		logger.info("Object has been saved succesfully, new Id is: " + id);
		return id;
	}

	@Transactional
	public void update(T t) {
		logger.info("Got request to update object: " + t);

		hibernateTemplate.update(t);

		logger.info("Object has been udpated succesfully");
	}

}
