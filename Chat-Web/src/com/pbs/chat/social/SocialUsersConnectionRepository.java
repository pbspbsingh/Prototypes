package com.pbs.chat.social;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;

import com.pbs.chat.component.dao.UserConnectionDAO;
import com.pbs.chat.entity.UserConnection;

public class SocialUsersConnectionRepository implements UsersConnectionRepository {

	private final ConnectionFactoryLocator connectionFactoryLocator;
	private final UserConnectionDAO userConnectionDAO;
	private ConnectionSignUp connectionSignUp;

	public SocialUsersConnectionRepository(UserConnectionDAO userConnectionDAO, ConnectionFactoryLocator connectionFactoryLocator) {
		this.userConnectionDAO = userConnectionDAO;
		this.connectionFactoryLocator = connectionFactoryLocator;
	}

	public void setConnectionSignUp(ConnectionSignUp connectionSignUp) {
		this.connectionSignUp = connectionSignUp;
	}

	@Override
	public List<String> findUserIdsWithConnection(Connection<?> connection) {
		final List<String> result = new ArrayList<>();
		final ConnectionKey connKey = connection.getKey();
		final List<UserConnection> userSessions = userConnectionDAO.getByProviderIdProviderUserId(connKey.getProviderId(),
				connKey.getProviderUserId());
		if (userSessions.size() == 0 && connectionSignUp != null) {
			final String newUserId = connectionSignUp.execute(connection);
			if (newUserId != null) {
				createConnectionRepository(newUserId).addConnection(connection);
				result.add(newUserId);
			}
		} else
			for (UserConnection u : userSessions)
				result.add(u.getUserId());
		return result;
	}

	@Override
	public Set<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserIds) {
		final Set<String> result = new HashSet<>();
		final List<UserConnection> userSessions = userConnectionDAO.getByProviderIdProviderUserIds(providerId, providerUserIds);
		for (UserConnection u : userSessions)
			result.add(u.getUserId());
		return result;
	}

	@Override
	public ConnectionRepository createConnectionRepository(String userId) {
		return new SocialConnectionRepository(userId, userConnectionDAO, connectionFactoryLocator);
	}

}
