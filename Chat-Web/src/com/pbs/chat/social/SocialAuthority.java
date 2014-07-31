package com.pbs.chat.social;

import org.springframework.security.core.GrantedAuthority;

public class SocialAuthority implements GrantedAuthority {

	private static final long serialVersionUID = -5358227026287011608L;

	private final String authority;

	public SocialAuthority(String authority) {
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return authority;
	}

	@Override
	public String toString() {
		return "SocialAuthority [authority=" + authority + "]";
	}
}
