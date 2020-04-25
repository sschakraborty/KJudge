package com.sschakraborty.platform.kjudge.data;

import com.sschakraborty.platform.kjudge.shared.model.*;

import java.util.ArrayList;
import java.util.List;

public class ClassRegistry {
	private static final List<Class> CLASS_LIST = new ArrayList<>();

	static {
		CLASS_LIST.add(CodeSubmission.class);
		CLASS_LIST.add(CodingEvent.class);
//		CLASS_LIST.add(CodingEventType.class);
		CLASS_LIST.add(TimeConstraint.class);
//		CLASS_LIST.add(Problem.class);
		CLASS_LIST.add(User.class);
		CLASS_LIST.add(UserProfile.class);
	}

	public static final List<Class> getClassList() {
		return CLASS_LIST;
	}
}