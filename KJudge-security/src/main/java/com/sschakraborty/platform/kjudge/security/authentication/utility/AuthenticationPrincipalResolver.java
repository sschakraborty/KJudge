package com.sschakraborty.platform.kjudge.security.authentication.utility;

import com.sschakraborty.platform.kjudge.data.GenericDAO;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;

import java.io.Serializable;

public class AuthenticationPrincipalResolver {
	private final GenericDAO genericDAO;

	public AuthenticationPrincipalResolver(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public <T> T resolvePrincipal(Class<T> principalType, Serializable principal) throws AbstractBusinessException {
		return genericDAO.fetch(principalType, principal).get(0);
	}
}