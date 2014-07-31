package com.pbs.chat.main;

import javax.servlet.Filter;

import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpringInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	public SpringInitializer() {
		if (System.getProperty("spring.profiles.active") == null)
			System.setProperty("spring.profiles.active", "production");
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { WebAppConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected String getServletName() {
		return "spring-mvc";
	}

	@Override
	protected Filter[] getServletFilters() {
		/*final HiddenHttpMethodFilter hiddenFilter = new HiddenHttpMethodFilter();
		hiddenFilter.setBeanName("hiddenHttpMethodFilter");*/
		return new Filter[] { new DelegatingFilterProxy("springSecurityFilterChain") };
	}
}
