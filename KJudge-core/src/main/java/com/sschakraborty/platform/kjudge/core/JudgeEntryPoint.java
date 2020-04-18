package com.sschakraborty.platform.kjudge.core;

import com.sschakraborty.platform.kjudge.core.judge.clang.CLang11CoreJudge;
import com.sschakraborty.platform.kjudge.core.judge.clang.CLang99CoreJudge;
import com.sschakraborty.platform.kjudge.core.judge.cpp.Cpp11CoreJudge;
import com.sschakraborty.platform.kjudge.core.judge.cpp.Cpp14CoreJudge;
import com.sschakraborty.platform.kjudge.core.judge.cpp.Cpp17CoreJudge;
import com.sschakraborty.platform.kjudge.core.judge.java.Java11CoreJudge;
import com.sschakraborty.platform.kjudge.core.judge.java.Java8CoreJudge;
import com.sschakraborty.platform.kjudge.core.judge.python.Python2CoreJudge;
import com.sschakraborty.platform.kjudge.core.judge.python.Python3CoreJudge;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.shared.model.Submission;
import com.sschakraborty.platform.kjudge.shared.model.SubmissionResult;

public class JudgeEntryPoint {
	private final JudgeSelector judgeSelector;

	public JudgeEntryPoint() throws AbstractBusinessException {
		this.judgeSelector = new JudgeSelector();
		this.initializeJudges();
	}

	private void initializeJudges() throws AbstractBusinessException {
		this.judgeSelector.addJudge(new CLang99CoreJudge());
		this.judgeSelector.addJudge(new CLang11CoreJudge());
		this.judgeSelector.addJudge(new Cpp11CoreJudge());
		this.judgeSelector.addJudge(new Cpp14CoreJudge());
		this.judgeSelector.addJudge(new Cpp17CoreJudge());
		this.judgeSelector.addJudge(new Java8CoreJudge());
		this.judgeSelector.addJudge(new Java11CoreJudge());
		this.judgeSelector.addJudge(new Python2CoreJudge());
		this.judgeSelector.addJudge(new Python3CoreJudge());
	}

	public SubmissionResult performJudgement(Submission submission) throws AbstractBusinessException {
		Judge judge = this.judgeSelector.select(submission);
		if (judge != null) {
			return judge.performJudgement(submission);
		}
		return null;
	}
}