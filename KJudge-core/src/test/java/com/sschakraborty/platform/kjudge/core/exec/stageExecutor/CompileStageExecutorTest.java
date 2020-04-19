package com.sschakraborty.platform.kjudge.core.exec.stageExecutor;

import com.sschakraborty.platform.kjudge.core.io.PropertyFileReader;
import com.sschakraborty.platform.kjudge.core.judge.cpp.Cpp14CoreJudge;
import com.sschakraborty.platform.kjudge.core.judge.java.Java8CoreJudge;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Properties;

public class CompileStageExecutorTest {
	private final Properties java8Properties;
	private final Properties cpp14Properties;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	public CompileStageExecutorTest() throws AbstractBusinessException {
		PropertyFileReader propertyFileReader = new PropertyFileReader();
		this.java8Properties = propertyFileReader.readJudgeProperties(Java8CoreJudge.class);
		this.cpp14Properties = propertyFileReader.readJudgeProperties(Cpp14CoreJudge.class);
	}

	@Test
	public void execute() throws AbstractBusinessException {
		CompileStageExecutor stageExecutor = new CompileStageExecutor(
			java8Properties.getProperty("jvm.basePath") + "/bin",
			java8Properties.getProperty("jvm.compiler"),
			"/home/sschakraborty/Documents/exper",
			false
		);

		stageExecutor.setArguments("-O", "Solution.java");
		stageExecutor.execute();
	}

	@Test
	public void executeWithCompare() throws AbstractBusinessException {
		expectedException.expect(AbstractBusinessException.class);

		CompileStageExecutor stageExecutor = new CompileStageExecutor(
			java8Properties.getProperty("jvm.basePath") + "/bin",
			java8Properties.getProperty("jvm.compiler"),
			"/home/sschakraborty/Documents/exper",
			true
		);

		stageExecutor.setExpectedOutput("Hello!");
		stageExecutor.setArguments("-O", "Solution.java");
		stageExecutor.execute();
	}

	@Test
	public void executeWithMultipleAgrs() throws AbstractBusinessException {
		CompileStageExecutor stageExecutor = new CompileStageExecutor(
			cpp14Properties.getProperty("cpp.basePath") + "/",
			cpp14Properties.getProperty("cpp.compiler"),
			"/home/sschakraborty/Documents/exper",
			true
		);

		stageExecutor.setExpectedOutput("");
		stageExecutor.setArguments("-O2", "-std=c++14", "-w", "solution.cpp");
		stageExecutor.execute();
	}
}