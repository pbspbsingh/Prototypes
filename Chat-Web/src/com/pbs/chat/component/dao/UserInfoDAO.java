package com.pbs.chat.component.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pbs.chat.entity.UserInfo;

@Repository
public class UserInfoDAO extends AbstractDAO<UserInfo> {

	@Transactional(readOnly = true)
	public UserInfo getByEmail(String emailId) {
		final List<?> list = sessionFactory.getCurrentSession()
				.createQuery("FROM UserInfo ui where ui.email=:email")
				.setString("email", emailId)
				.list();
		return list.size() > 0 ? (UserInfo) list.get(0) : null;
	}

}
