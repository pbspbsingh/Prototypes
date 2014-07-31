package com.pbs.chat.social;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.IllegalAddException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;

import com.pbs.chat.component.dao.UserInfoDAO;
import com.pbs.chat.entity.UserInfo;

public class SignInAdapterImpl implements SignInAdapter {

	private Log logger = LogFactory.getLog(getClass());

	@Autowired
	private UserInfoDAO userInfoDao;

	private static final String PASSWORD = "P@$$w0rd";

	@Override
	public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
		try {
			final UserInfo userInfo = userInfoDao.getByEmail(userId);
			if (userInfo != null) {
				signInUser(userInfo);
			}
		} catch (Exception e) {
			logger.error("Excetion while authenticating the user", e);
			throw new IllegalAddException("Failed to authenticate the user");
		}
		return null;
	}

	private Authentication signInUser(UserInfo userInfo) {
		final User user = new User(userInfo.getEmail(), PASSWORD, Arrays.asList(new SocialAuthority(userInfo.getAuthority())));
		final Authentication authentication = new UsernamePasswordAuthenticationToken(user, PASSWORD, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return authentication;
	}

}
