package com.sschakraborty.platform.kjudge.core.judge.java;

import com.sschakraborty.platform.kjudge.core.io.PropertyFileReader;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.shared.model.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Java11CoreJudgeTest {
	private final Properties masterProperties;
	private final Java11CoreJudge judge;

	public Java11CoreJudgeTest() throws AbstractBusinessException {
		PropertyFileReader propertyFileReader = new PropertyFileReader();
		this.masterProperties = propertyFileReader.readPropertiesUsingFileName("kjudge.properties");
		judge = new Java11CoreJudge();
	}

	@Test
	public void performJudgement() throws AbstractBusinessException {
		CodeSubmission codeSubmission = new CodeSubmission();
		codeSubmission.setLanguage(Language.JAVA_11);
		codeSubmission.setSourceCode("class Solution {\n" +
			"    public static void main(String[] args) {\n" +
			"        var str = \"Hello World! - Java 11\";\n" +
			"        System.out.println(str);\n" +
			"    }\n" +
			"}");

		Testcase tc1 = new Testcase();
		tc1.setName("TC1");
		tc1.setInputFilePath(this.masterProperties.getProperty("kjudge.stageDirectory") + "/input");

		Testcase tc2 = new Testcase();
		tc2.setName("TC2");
		tc2.setInputFilePath(this.masterProperties.getProperty("kjudge.stageDirectory") + "/input");

		Map<Language, Integer> map = new HashMap<>();
		map.put(Language.JAVA_11, 1000);
		TimeConstraint timeConstraint = new TimeConstraint();
		timeConstraint.setTimeConstraints(map);

		Problem problem = new Problem();
		problem.setProblemHandle("HelloWorld123");
		problem.setTestcases(Arrays.asList(tc1, tc2));
		problem.setTimeConstraint(timeConstraint);

		User user = new User();
		user.setPrincipal("Java11TestUser");

		Submission submission = new Submission();
		submission.setId((int) (Math.random() * 1000));
		submission.setProblem(problem);
		submission.setCodeSubmission(codeSubmission);
		submission.setSubmitter(user);

		judge.performJudgement(submission);
	}
}