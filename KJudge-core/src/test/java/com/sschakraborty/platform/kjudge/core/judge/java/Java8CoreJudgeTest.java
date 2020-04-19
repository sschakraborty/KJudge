package com.sschakraborty.platform.kjudge.core.judge.java;

import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.shared.model.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Java8CoreJudgeTest {
	private final Java8CoreJudge judge;

	public Java8CoreJudgeTest() throws AbstractBusinessException {
		judge = new Java8CoreJudge();
	}

	@Test
	public void performJudgement() throws AbstractBusinessException {
		CodeSubmission codeSubmission = new CodeSubmission();
		codeSubmission.setLanguage(Language.JAVA_8);
		codeSubmission.setSourceCode("class Solution {\n" +
			"    public static void main(String[] args) {\n" +
			"        System.out.println(\"Hello World!\");\n" +
			"    }\n" +
			"}");

		Testcase testcase = new Testcase();
		testcase.setName("TC1");
		testcase.setInputFilePath("/home/sschakraborty/Documents/exper/input");

		Map<Language, Integer> map = new HashMap<>();
		map.put(Language.JAVA_8, 1000);
		TimeConstraint timeConstraint = new TimeConstraint();
		timeConstraint.setTimeConstraints(map);

		Problem problem = new Problem();
		problem.setProblemHandle("HelloWorld123");
		problem.setTestcases(Arrays.asList(testcase));
		problem.setTimeConstraint(timeConstraint);

		User user = new User();
		user.setPrincipal("TestUser");

		Submission submission = new Submission();
		submission.setId((int) (Math.random() * 1000));
		submission.setProblem(problem);
		submission.setCodeSubmission(codeSubmission);
		submission.setSubmitter(user);

		judge.performJudgement(submission);
	}
}