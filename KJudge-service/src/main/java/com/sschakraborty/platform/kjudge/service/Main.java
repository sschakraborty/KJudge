package com.sschakraborty.platform.kjudge.service;

import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;

public class Main {
	public static void main(String[] args) {
		try {
			final Application application = new Application(12400);
			application.init();
		} catch (AbstractBusinessException e) {
			// TODO: Log it!
		}
	}
}