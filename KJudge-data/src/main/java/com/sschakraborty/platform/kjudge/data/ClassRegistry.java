package com.sschakraborty.platform.kjudge.data;

import com.sschakraborty.platform.kjudge.shared.model.CodeSubmission;

import java.util.ArrayList;
import java.util.List;

public class ClassRegistry {
	private static final List<Class> classList = new ArrayList<>();

	static {
		classList.add(CodeSubmission.class);
//		classList.add(CodingEvent.class);
//		classList.add(CodingEventType.class);
//		classList.add(IOConstraint.class);
//		classList.add(Problem.class);
	}

	public static final List<Class> getClassList() {
		return classList;
	}
}
