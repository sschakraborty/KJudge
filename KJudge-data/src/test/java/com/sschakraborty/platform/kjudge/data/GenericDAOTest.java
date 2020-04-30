package com.sschakraborty.platform.kjudge.data;

import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.shared.model.CodeSubmission;
import com.sschakraborty.platform.kjudge.shared.model.Language;
import com.sschakraborty.platform.kjudge.shared.model.User;
import com.sschakraborty.platform.kjudge.shared.model.UserProfile;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class GenericDAOTest {
	private GenericDAO genericDAO;

	public GenericDAOTest() throws AbstractBusinessException {
		genericDAO = new GenericDAO();
	}

	@Test
	public void fullFetchTest() throws AbstractBusinessException {
		GenericDAO genericDAO = new GenericDAO();
		UserProfile userProfile = new UserProfile();
		userProfile.setUserPrincipal("USER1234");
		userProfile.setFirstName("Subhadra");
		userProfile.setLastName("Chakraborty");
		userProfile.setDisplayName("~[pi]");
		userProfile.setEmails(Arrays.asList(
			"sschakraborty@hotmail.com",
			"subhadrasundar@gmail.com"
		));
		userProfile.setPhoneNumbers(Arrays.asList(
			"+91-1234567890"
		));

		User user = new User();
		user.setUserProfile(userProfile);
		user.setPrincipal("USER1234");
		user.setPassword("USER1234_ENCRYPTED");

		genericDAO.saveOrUpdate(user);

		UserProfile newUserProfile = genericDAO.fetchFull(UserProfile.class, "USER1234");
		Assert.assertNotNull(newUserProfile.getEmails());
		Assert.assertNotNull(newUserProfile.getEmails());
	}

	@Test
	public void testGenericDAO() throws AbstractBusinessException {
		CodeSubmission codeSubmission1 = new CodeSubmission();
		codeSubmission1.setLanguage(Language.PYTHON_3);
		codeSubmission1.setSourceCode("print(\"Hello!\")");

		CodeSubmission codeSubmission2 = new CodeSubmission();
		codeSubmission2.setLanguage(Language.PYTHON_2);
		codeSubmission2.setSourceCode("print \"Hello!\"");

		CodeSubmission[] codeSubmission = genericDAO.save(codeSubmission1, codeSubmission2);

		codeSubmission1.setSourceCode("Wrong Source Code");
		codeSubmission2.setSourceCode("Wrong Source Code");

		CodeSubmission[] fetchSubmissions = genericDAO.update(codeSubmission1, codeSubmission2);

		Assert.assertEquals(codeSubmission[0].getLanguage(), fetchSubmissions[0].getLanguage());
		Assert.assertEquals(codeSubmission[1].getLanguage(), fetchSubmissions[1].getLanguage());
		Assert.assertEquals(codeSubmission[0].getSourceCode(), fetchSubmissions[0].getSourceCode());
		Assert.assertEquals(codeSubmission[1].getSourceCode(), fetchSubmissions[1].getSourceCode());

		List<CodeSubmission> list = genericDAO.fetch(CodeSubmission.class, codeSubmission1.getId(), codeSubmission2.getId());
		fetchSubmissions[0] = list.get(0);
		fetchSubmissions[1] = list.get(1);

		Assert.assertEquals(codeSubmission[0].getLanguage(), fetchSubmissions[0].getLanguage());
		Assert.assertEquals(codeSubmission[1].getLanguage(), fetchSubmissions[1].getLanguage());
		Assert.assertEquals(codeSubmission[0].getSourceCode(), fetchSubmissions[0].getSourceCode());
		Assert.assertEquals(codeSubmission[1].getSourceCode(), fetchSubmissions[1].getSourceCode());

		codeSubmission1.setSourceCode("print(\"Hello!\")");
		codeSubmission2.setSourceCode("print \"Hello!\"");

		fetchSubmissions = genericDAO.saveOrUpdate(codeSubmission1, codeSubmission2);

		Assert.assertEquals(codeSubmission[0].getLanguage(), fetchSubmissions[0].getLanguage());
		Assert.assertEquals(codeSubmission[1].getLanguage(), fetchSubmissions[1].getLanguage());
		Assert.assertEquals(codeSubmission[0].getSourceCode(), fetchSubmissions[0].getSourceCode());
		Assert.assertEquals(codeSubmission[1].getSourceCode(), fetchSubmissions[1].getSourceCode());

		list = genericDAO.fetch(CodeSubmission.class, codeSubmission1.getId(), codeSubmission2.getId());
		fetchSubmissions[0] = list.get(0);
		fetchSubmissions[1] = list.get(1);

		Assert.assertEquals(codeSubmission[0].getLanguage(), fetchSubmissions[0].getLanguage());
		Assert.assertEquals(codeSubmission[1].getLanguage(), fetchSubmissions[1].getLanguage());
		Assert.assertEquals(codeSubmission[0].getSourceCode(), fetchSubmissions[0].getSourceCode());
		Assert.assertEquals(codeSubmission[1].getSourceCode(), fetchSubmissions[1].getSourceCode());

		fetchSubmissions = genericDAO.delete(codeSubmission1, codeSubmission2);

		Assert.assertEquals(codeSubmission[0].getLanguage(), fetchSubmissions[0].getLanguage());
		Assert.assertEquals(codeSubmission[1].getLanguage(), fetchSubmissions[1].getLanguage());
		Assert.assertEquals(codeSubmission[0].getSourceCode(), fetchSubmissions[0].getSourceCode());
		Assert.assertEquals(codeSubmission[1].getSourceCode(), fetchSubmissions[1].getSourceCode());

		list = genericDAO.fetch(CodeSubmission.class, codeSubmission1.getId(), codeSubmission2.getId());
		fetchSubmissions[0] = list.get(0);
		fetchSubmissions[1] = list.get(1);

		Assert.assertNull(fetchSubmissions[0]);
		Assert.assertNull(fetchSubmissions[1]);
	}
}