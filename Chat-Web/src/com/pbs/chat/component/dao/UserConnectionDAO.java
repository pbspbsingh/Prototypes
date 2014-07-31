package com.pbs.chat.component.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pbs.chat.entity.UserConnection;

@Repository
public class UserConnectionDAO extends AbstractDAO<UserConnection> {

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<UserConnection> getByUserId(String userId) {
		logger.info("Got request to fetch userconnection by userid " + userId);
		return sessionFactory
				.getCurrentSession()
				.createQuery("FROM UserConnection uc where uc.userId=:userId order by uc.providerId, uc.rank")
				.setString("userId", userId).list();
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<UserConnection> getByUserIdProviderId(String userId, String providerId) {
		logger.info(String.format("Got request to fetch userconnection by userid %s and providerid %s", userId, providerId));
		return sessionFactory
				.getCurrentSession()
				.createQuery("FROM UserConnection uc where uc.userId=:userId and uc.providerId=:providerId order by uc.rank")
				.setString("userId", userId)
				.setString("providerId", providerId)
				.list();
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<UserConnection> getByUserIdProviderIdProviderUserIds(String userId, String providerId, Collection<String> providerUserIds) {
		logger.info(String.format("Got request to fetch userconnection by userid %s, providerid %s & providerUserIds %s", 
				userId, providerId, providerUserIds));
		return sessionFactory
				.getCurrentSession()
				.createQuery("FROM UserConnection uc where uc.userId=:userId and uc.providerId=:providerId and uc.providerUserId in (:providerUserIds) order by uc.rank")
				.setString("userId", userId)
				.setString("providerId", providerId)
				.setParameterList("providerUserIds", providerUserIds)
				.list();
	}
	
	@Transactional
	public int deleteByUserIdProviderId(String userId, String providerId) {
		logger.info(String.format("Got a delete request userId: %s and providerId: %s", userId, providerId));
		return sessionFactory
			.getCurrentSession()
			.createQuery("delete from UserConnection uc where uc.userId=:userId and uc.providerId=:providerId")
			.setString("userId", userId)
			.setString("providerId", providerId)
			.executeUpdate();
	}

	@Transactional
	public int deleteByUserIdProviderIdProviderUserId(String userId, String providerId, String providerUserId) {
		logger.info(String.format("Got a delete request userId: %s, providerId: %s and providerUserId %s", userId, providerId, providerUserId));
		return sessionFactory
			.getCurrentSession()
			.createQuery("delete from UserConnection uc where uc.userId=:userId and uc.providerId=:providerId and uc.providerUserId=:providerUserId")
			.setString("userId", userId)
			.setString("providerId", providerId)
			.setString("providerUserId", providerUserId)
			.executeUpdate();
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<UserConnection> getByProviderIdProviderUserId(String providerId, String providerUserId) {
		logger.info(String.format("Got request to fetch userconnection by providerid %s and providerUserid %s", providerId, providerUserId));
		return hibernateTemplate.getSessionFactory()
				.getCurrentSession()
				.createQuery("FROM UserConnection uc where uc.providerId=:providerId and uc.providerUserId=:providerUserId order by uc.rank")
				.setString("providerId", providerId)
				.setString("providerUserId", providerUserId)
				.list();
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<UserConnection> getByProviderIdProviderUserIds(String providerId, Collection<String> providerUserIds) {
		logger.info(String.format("Got request to fetch userconnection by providerid %s and providerUserids %s", providerId, providerUserIds));
		return hibernateTemplate.getSessionFactory()
				.getCurrentSession()
				.createQuery("FROM UserConnection uc where uc.providerId=:providerId and uc.providerUserId in (:providerUserIds) order by uc.rank")
				.setString("providerId", providerId)
				.setParameterList("providerUserId", providerUserIds)
				.list();
		
	}
}
