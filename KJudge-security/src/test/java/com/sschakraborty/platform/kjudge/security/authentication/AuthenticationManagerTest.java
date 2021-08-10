package com.sschakraborty.platform.kjudge.security.authentication;

import com.sschakraborty.platform.kjudge.data.GenericDAO;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.security.authentication.utility.AuthenticationPrincipalResolver;
import com.sschakraborty.platform.kjudge.security.credential.DatabaseAuthenticationCredential;
import com.sschakraborty.platform.kjudge.security.crypto.SHAUtility;
import com.sschakraborty.platform.kjudge.shared.model.User;
import com.sschakraborty.platform.kjudge.shared.model.UserProfile;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
@Ignore
public class AuthenticationManagerTest {
	@Test
	public void test() throws AbstractBusinessException {
		GenericDAO genericDAO = new GenericDAO();
		AuthenticationPrincipalResolver resolver = new AuthenticationPrincipalResolver(genericDAO);
		AuthenticationManager manager = new AuthenticationManager(resolver);

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
		user.setPassword(SHAUtility.sha256("HelloWorldPassword"));

		genericDAO.saveOrUpdate(user);

		DatabaseAuthenticationCredential credential = new DatabaseAuthenticationCredential();
		credential.setPassword("HelloWorldPassword");
		credential.setUserPrincipal("USER1234");

		manager.authenticate(credential);
	}
}