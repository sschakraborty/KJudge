package com.sschakraborty.platform.kjudge.core.exec.stageExecutor;

import com.sschakraborty.platform.kjudge.core.io.PropertyFileReader;
import com.sschakraborty.platform.kjudge.core.judge.cpp.Cpp14CoreJudge;
import com.sschakraborty.platform.kjudge.core.judge.java.Java8CoreJudge;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Properties;

public class RunStageExecutorTest {
	private final Properties java8Properties;
	private final Properties cpp14Properties;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	public RunStageExecutorTest() throws AbstractBusinessException {
		PropertyFileReader propertyFileReader = new PropertyFileReader();
		this.java8Properties = propertyFileReader.readJudgeProperties(Java8CoreJudge.class);
		this.cpp14Properties = propertyFileReader.readJudgeProperties(Cpp14CoreJudge.class);
	}

	@Test
	public void execute() throws AbstractBusinessException {
		RunStageExecutor stageExecutor = new RunStageExecutor(
			"RunID_1",
			java8Properties.getProperty("jvm.basePath") + "/bin",
			java8Properties.getProperty("jvm.runtime"),
			"/home/sschakraborty/Documents/exper"
		);

		stageExecutor.setArguments("-server", "Solution");
		stageExecutor.setTimeLimit(1000);
		stageExecutor.setInputFilePath("/home/sschakraborty/Documents/exper/input");
		stageExecutor.execute();
	}
}