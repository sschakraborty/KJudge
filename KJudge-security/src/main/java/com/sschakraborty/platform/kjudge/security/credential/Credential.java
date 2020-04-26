package com.sschakraborty.platform.kjudge.security.credential;

import java.io.Serializable;

public interface Credential {
	Serializable getPrincipal();

	Class<?> getPrincipalType();
}