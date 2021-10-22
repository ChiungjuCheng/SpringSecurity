package com.example.security.demo.auth;

import java.util.Collection;
import java.util.logging.Logger;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;

/**
 * 決定current request是否具有權限
 * @author user
 */
public class CustomAccessDecisionVoter implements AccessDecisionVoter<Object> {

	private Logger LOG = Logger.getLogger(CustomAccessDecisionVoter.class.getName());

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	@Override
	public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {

		if(authentication instanceof TokenAuthentication) {
			LOG.info("test");
		}
		
		if ("user1".equals(authentication.getName())) {
			return ACCESS_GRANTED;
		} else {
			return ACCESS_DENIED;
		}

	}

}
