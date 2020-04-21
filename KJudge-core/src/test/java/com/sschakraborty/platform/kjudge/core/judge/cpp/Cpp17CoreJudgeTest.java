package com.sschakraborty.platform.kjudge.core.judge.cpp;

import com.sschakraborty.platform.kjudge.core.io.PropertyFileReader;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.shared.model.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Cpp17CoreJudgeTest {
	private final Properties masterProperties;
	private final Cpp17CoreJudge judge;

	public Cpp17CoreJudgeTest() throws AbstractBusinessException {
		PropertyFileReader propertyFileReader = new PropertyFileReader();
		this.masterProperties = propertyFileReader.readPropertiesUsingFileName("kjudge.properties");
		judge = new Cpp17CoreJudge();
	}

	@Test
	public void performJudgement() throws AbstractBusinessException {
		CodeSubmission codeSubmission = new CodeSubmission();
		codeSubmission.setLanguage(Language.CPP_17);
		codeSubmission.setSourceCode("#include <iostream>\n" +
			"\n" +
			"int main() {\n" +
			"    std::cout << \"Hello World!\";\n" +
			"    return 0;\n" +
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
		map.put(Language.CPP_17, 100);
		TimeConstraint timeConstraint = new TimeConstraint();
		timeConstraint.setTimeConstraints(map);

		Problem problem = new Problem();
		problem.setProblemHandle("HelloWorld123");
		problem.setTestcases(Arrays.asList(tc1, tc2));
		problem.setTimeConstraint(timeConstraint);

		User user = new User();
		user.setPrincipal("Cpp17TestUser");

		Submission submission = new Submission();
		submission.setId((int) (Math.random() * 1000));
		submission.setProblem(problem);
		submission.setCodeSubmission(codeSubmission);
		submission.setSubmitter(user);

		judge.performJudgement(submission);
	}
}