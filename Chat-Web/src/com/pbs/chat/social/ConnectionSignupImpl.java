package com.pbs.chat.social;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;

import com.pbs.chat.component.dao.UserInfoDAO;
import com.pbs.chat.entity.UserInfo;

public class ConnectionSignupImpl implements ConnectionSignUp {

	@Autowired
	private UserInfoDAO userInfoDAO;

	protected Log logger = LogFactory.getLog(getClass());

	@Override
	public String execute(Connection<?> connection) {
		final UserProfile userProfile = connection.fetchUserProfile();
		logger.info("Signing up the new user automatically.");
		try {
			final UserInfo info = new UserInfo();
			info.setEmail(userProfile.getEmail());
			info.setFirstName(userProfile.getFirstName());
			info.setLastName(userProfile.getLastName());
			info.setAuthority("ROLE_USER");
			info.setProviderId(connection.getKey().getProviderId());
			info.setProviderUserId(connection.getKey().getProviderUserId());

			userInfoDAO.save(info);
			logger.info("Created UserInfo model: " + info);
		} catch (Exception e) {
			logger.error("Excpetion while creating the user profile", e);
			return null;
		}
		return userProfile.getEmail();
	}

}
