package com.pbs.chat.main;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpringInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	public SpringInitializer() {
		System.setProperty("spring.profiles.active", "development");
	}
	
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { WebAppConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected String getServletName() {
		return "spring-mvc";
	}

	/*@Override
	protected Filter[] getServletFilters() {
		return new Filter[] { new DelegatingFilterProxy("springSecurityFilterChain") };
	}*/
}
