package com.pbs.chat.component.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.security.SocialAuthenticationServiceRegistry;

import com.pbs.chat.component.dao.UserConnectionDAO;
import com.pbs.chat.social.ConnectionSignupImpl;
import com.pbs.chat.social.SignInAdapterImpl;
import com.pbs.chat.social.SocialUsersConnectionRepository;

@Configuration
@PropertySource("classpath:${spring.profiles.active}/application.properties")
public class SpringSocialConfig {

	@Autowired
	private UserConnectionDAO userConnectionDAO;

	@Autowired
	private Environment environment;

	@Bean
	public ConnectionFactoryLocator connectionFactoryLocator() {
		ConnectionFactoryRegistry registry = new SocialAuthenticationServiceRegistry();
		registry.addConnectionFactory(new FacebookConnectionFactory(environment.getRequiredProperty("facebook.appId"), environment
				.getRequiredProperty("facebook.appSecret")));
		return registry;
	}

	@Bean
	public UsersConnectionRepository usersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator, ConnectionSignUp signup) {
		final SocialUsersConnectionRepository repo = new SocialUsersConnectionRepository(userConnectionDAO, connectionFactoryLocator);
		repo.setConnectionSignUp(signup);
		return repo;
	}

	@Bean
	@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
	public ConnectionRepository connectionRepository(UsersConnectionRepository usersConnectionRepository) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new IllegalStateException("Unable to get a ConnectionRepository: no user signed in");
		}
		return usersConnectionRepository.createConnectionRepository(authentication.getName());
	}

	@Bean
	public ConnectionSignUp connectionSignUp() {
		return new ConnectionSignupImpl();
	}
	
	@Bean
	public SignInAdapterImpl signInAdapter(){
		return new SignInAdapterImpl();
	}

	@Bean
	public ProviderSignInController signInController(ConnectionFactoryLocator connectionFactoryLocator, UsersConnectionRepository usersConnectionRepository,
			SignInAdapter adapter){
		final ProviderSignInController signInController = new ProviderSignInController(connectionFactoryLocator, usersConnectionRepository, adapter);
		signInController.setPostSignInUrl("/mobile/index.html");
		return signInController;
	}
}
