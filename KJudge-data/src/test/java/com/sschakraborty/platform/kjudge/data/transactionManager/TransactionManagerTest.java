package com.sschakraborty.platform.kjudge.data.transactionManager;

import com.sschakraborty.platform.kjudge.data.dataProvider.DataProvider;
import com.sschakraborty.platform.kjudge.data.dataProvider.DefaultDataProvider;
import com.sschakraborty.platform.kjudge.data.unit.StatefulTransactionUnit;
import com.sschakraborty.platform.kjudge.data.unit.StatelessTransactionUnit;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.shared.model.CodeSubmission;
import com.sschakraborty.platform.kjudge.shared.model.Language;
import org.junit.Assert;
import org.junit.Test;

public class TransactionManagerTest {
	@Test
	public void executeJob() throws AbstractBusinessException {
		DataProvider dataProvider = new DefaultDataProvider();
		TransactionManager transactionManager = new TransactionManager(dataProvider);

		CodeSubmission submission = transactionManager.executeStatefulJob(new StatefulTransactionJob() {
			@Override
			public CodeSubmission execute(StatefulTransactionUnit transactionUnit) throws AbstractBusinessException {
				CodeSubmission codeSubmission = new CodeSubmission();
				codeSubmission.setLanguage(Language.CPP_14);
				codeSubmission.setSourceCode("#include<iostream> int main() { std::cout << \"Test code\"; }");

				transactionUnit.getSession().save(codeSubmission);

				return codeSubmission;
			}
		});

		CodeSubmission fetch = transactionManager.executeStatelessJob(new StatelessTransactionJob() {
			@Override
			public CodeSubmission execute(StatelessTransactionUnit transactionUnit) throws AbstractBusinessException {
				return (CodeSubmission) transactionUnit.getSession().get(CodeSubmission.class, submission.getId());
			}
		});

		Assert.assertEquals(submission.getSourceCode(), fetch.getSourceCode());
	}
}