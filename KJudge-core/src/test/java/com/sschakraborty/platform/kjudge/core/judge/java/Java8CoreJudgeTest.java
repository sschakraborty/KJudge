package com.sschakraborty.platform.kjudge.core.judge.java;

import com.sschakraborty.platform.kjudge.core.io.PropertyFileReader;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.shared.model.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Java8CoreJudgeTest {
	private final Properties masterProperties;
	private final Java8CoreJudge judge;

	public Java8CoreJudgeTest() throws AbstractBusinessException {
		PropertyFileReader propertyFileReader = new PropertyFileReader();
		this.masterProperties = propertyFileReader.readPropertiesUsingFileName("kjudge.properties");
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

		Testcase tc1 = new Testcase();
		tc1.setName("TC1");
		tc1.setInputFilePath(this.masterProperties.getProperty("kjudge.stageDirectory") + "/input");
		tc1.setExpectedOutputFilePath(this.masterProperties.getProperty("kjudge.stageDirectory") + "/expectedOutput");

		Testcase tc2 = new Testcase();
		tc2.setName("TC2");
		tc2.setInputFilePath(this.masterProperties.getProperty("kjudge.stageDirectory") + "/input");
		tc2.setExpectedOutputFilePath(this.masterProperties.getProperty("kjudge.stageDirectory") + "/expectedOutput");

		Map<Language, Integer> map = new HashMap<>();
		map.put(Language.JAVA_8, 1000);
		TimeConstraint timeConstraint = new TimeConstraint();
		timeConstraint.setTimeConstraints(map);

		Problem problem = new Problem();
		problem.setProblemHandle("HelloWorld123");
		problem.setTestcases(Arrays.asList(tc1, tc2));
		problem.setTimeConstraint(timeConstraint);

		User user = new User();
		user.setPrincipal("Java8TestUser");

		Submission submission = new Submission();
		submission.setId((int) (Math.random() * 1000));
		submission.setProblem(problem);
		submission.setCodeSubmission(codeSubmission);
		submission.setSubmitter(user);

		judge.performJudgement(submission);
	}
}