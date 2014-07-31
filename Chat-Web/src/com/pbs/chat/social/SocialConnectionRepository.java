package com.pbs.chat.social;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.DuplicateConnectionException;
import org.springframework.social.connect.NotConnectedException;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.pbs.chat.component.dao.UserConnectionDAO;
import com.pbs.chat.entity.UserConnection;

public class SocialConnectionRepository implements ConnectionRepository {

	private final String userId;
	private final UserConnectionDAO userConnectionDAO;
	private final ConnectionFactoryLocator connectionFactoryLocator;

	public SocialConnectionRepository(String userId, UserConnectionDAO userConnectionDAO, ConnectionFactoryLocator connectionFactoryLocator) {
		this.userId = userId;
		this.userConnectionDAO = userConnectionDAO;
		this.connectionFactoryLocator = connectionFactoryLocator;
	}

	@Override
	public MultiValueMap<String, Connection<?>> findAllConnections() {
		final MultiValueMap<String, Connection<?>> connections = new LinkedMultiValueMap<String, Connection<?>>();

		final List<UserConnection> userConnections = userConnectionDAO.getByUserId(userId);
		for (UserConnection userConnection : userConnections) {
			final Connection<?> connection = createConnection(userConnection);
			final String providerId = userConnection.getProviderId();
			if (connections.get(providerId).size() == 0) {
				connections.put(providerId, new LinkedList<Connection<?>>());
			}
			connections.add(providerId, connection);
		}
		return connections;
	}

	private Connection<?> createConnection(UserConnection userConnection) {
		final String providerId = userConnection.getProviderId();
		final ConnectionData data = new ConnectionData(providerId, userConnection.getProviderUserId(), userConnection.getDisplayName(),
				userConnection.getProfileUrl(), userConnection.getImageUrl(), userConnection.getAccessToken(), userConnection.getSecret(),
				userConnection.getRefreshToken(), userConnection.getExpireTime());
		final Connection<?> connection = connectionFactoryLocator.getConnectionFactory(providerId).createConnection(data);
		return connection;
	}

	@Override
	public List<Connection<?>> findConnections(String providerId) {
		final List<Connection<?>> connectionList = new ArrayList<Connection<?>>();
		final List<UserConnection> userConnections = userConnectionDAO.getByUserIdProviderId(userId, providerId);
		for (UserConnection userConnection : userConnections) {
			connectionList.add(createConnection(userConnection));
		}
		return connectionList;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <A> List<Connection<A>> findConnections(Class<A> apiType) {
		final String providerId = connectionFactoryLocator.getConnectionFactory(apiType).getProviderId();
		return (List) findConnections(providerId);
	}

	@Override
	public MultiValueMap<String, Connection<?>> findConnectionsToUsers(MultiValueMap<String, String> providerUserIds) {
		if (providerUserIds == null || providerUserIds.isEmpty()) {
			throw new IllegalArgumentException("Unable to execute find: no providerUsers provided");
		}
		final MultiValueMap<String, Connection<?>> connections = new LinkedMultiValueMap<String, Connection<?>>();
		for (String providerId : providerUserIds.keySet()) {
			final List<UserConnection> userConnections = userConnectionDAO.getByUserIdProviderIdProviderUserIds(userId, providerId,
					providerUserIds.get(providerId));
			if (connections.get(providerId).size() == 0) {
				connections.put(providerId, new LinkedList<Connection<?>>());
			}
			for (UserConnection userConnection : userConnections)
				connections.add(providerId, createConnection(userConnection));
		}
		return connections;
	}

	@Override
	public Connection<?> getConnection(ConnectionKey connectionKey) {
		final String providerId = connectionKey.getProviderId();
		final String providerUserId = connectionKey.getProviderUserId();
		final List<UserConnection> connections = userConnectionDAO.getByUserIdProviderIdProviderUserIds(userId, providerId,
				Arrays.asList(providerUserId));
		return connections != null && connections.size() > 0 ? createConnection(connections.get(0)) : null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <A> Connection<A> getConnection(Class<A> apiType, String providerUserId) {
		String providerId = connectionFactoryLocator.getConnectionFactory(apiType).getProviderId();
		return (Connection<A>) getConnection(new ConnectionKey(providerId, providerUserId));
	}

	@Override
	@SuppressWarnings("unchecked")
	public <A> Connection<A> getPrimaryConnection(Class<A> apiType) {
		String providerId = connectionFactoryLocator.getConnectionFactory(apiType).getProviderId();
		final List<Connection<?>> connections = findConnections(providerId);
		if (connections != null && connections.size() > 0)
			return (Connection<A>) connections.get(0);
		else
			throw new NotConnectedException(providerId);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <A> Connection<A> findPrimaryConnection(Class<A> apiType) {
		String providerId = connectionFactoryLocator.getConnectionFactory(apiType).getProviderId();
		final List<Connection<?>> connections = findConnections(providerId);
		if (connections != null && connections.size() > 0)
			return (Connection<A>) connections.get(0);
		return null;
	}

	@Override
	public void addConnection(Connection<?> connection) {
		try {
			int maxRank = 1;
			final ConnectionData data = connection.createData();
			final List<UserConnection> userSessions = userConnectionDAO.getByUserIdProviderId(userId, data.getProviderId());
			if (!userSessions.isEmpty()) {
				final UserConnection user = userSessions.get(userSessions.size() - 1);
				maxRank = user.getRank() + 1;
			}

			UserConnection userConnection = new UserConnection();
			userConnection.setUserId(userId);
			userConnection.setProviderId(data.getProviderId());
			userConnection.setProviderUserId(data.getProviderUserId());
			userConnection.setRank(maxRank);
			userConnection.setDisplayName(data.getDisplayName());
			userConnection.setProfileUrl(data.getProfileUrl());
			userConnection.setImageUrl(data.getImageUrl());
			userConnection.setAccessToken(data.getAccessToken());
			userConnection.setSecret(data.getSecret());
			userConnection.setRefreshToken(data.getRefreshToken());
			userConnection.setExpireTime(data.getExpireTime());

			userConnectionDAO.save(userConnection);
		} catch (DuplicateKeyException e) {
			throw new DuplicateConnectionException(connection.getKey());
		}
	}

	@Override
	public void updateConnection(Connection<?> connection) {
		final ConnectionData data = connection.createData();
		final List<UserConnection> userSessions = userConnectionDAO.getByUserIdProviderIdProviderUserIds(userId, data.getProviderId(),
				Arrays.asList(data.getProviderUserId()));
		for (UserConnection userConnection : userSessions) {
			userConnection.setDisplayName(data.getDisplayName());
			userConnection.setProfileUrl(data.getProfileUrl());
			userConnection.setImageUrl(data.getImageUrl());
			userConnection.setAccessToken(data.getAccessToken());
			userConnection.setSecret(data.getSecret());
			userConnection.setRefreshToken(data.getRefreshToken());
			userConnection.setExpireTime(data.getExpireTime());

			userConnectionDAO.update(userConnection);
		}
	}

	@Override
	public void removeConnections(String providerId) {
		userConnectionDAO.deleteByUserIdProviderId(userId, providerId);
	}

	@Override
	public void removeConnection(ConnectionKey connectionKey) {
		userConnectionDAO.deleteByUserIdProviderIdProviderUserId(userId, connectionKey.getProviderId(), connectionKey.getProviderUserId());
	}

}
