package com.sschakraborty.platform.kjudge.data.dataProvider;

import com.sschakraborty.platform.kjudge.data.unit.StatefulTransactionUnit;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.shared.model.CodeSubmission;
import com.sschakraborty.platform.kjudge.shared.model.Language;
import org.junit.Assert;
import org.junit.Test;

public class DefaultDataProviderTest {
	@Test
	public void statefulTransaction() throws AbstractBusinessException {
		DefaultDataProvider defaultDataProvider = new DefaultDataProvider();
		StatefulTransactionUnit transaction = defaultDataProvider.statefulTransaction();

		CodeSubmission codeSubmission = new CodeSubmission();
		codeSubmission.setLanguage(Language.CPP_17);
		codeSubmission.setSourceCode("#include<iostream>");

		transaction.getSession().saveOrUpdate(codeSubmission);
		transaction.getTransaction().commit();
		transaction.getSession().close();

		transaction = defaultDataProvider.statefulTransaction();
		CodeSubmission fetched = transaction.getSession().get(CodeSubmission.class, codeSubmission.getId());
		transaction.getSession().close();

		Assert.assertEquals(codeSubmission.getId(), fetched.getId());
		Assert.assertEquals(codeSubmission.getLanguage(), fetched.getLanguage());
		Assert.assertEquals(codeSubmission.getSourceCode(), fetched.getSourceCode());

		transaction = defaultDataProvider.statefulTransaction();
		transaction.getSession().delete(codeSubmission);
		transaction.getTransaction().commit();
		transaction.getSession().close();
	}
}