package com.sschakraborty.platform.kjudge.data.modelTest;

import com.sschakraborty.platform.kjudge.data.GenericDAO;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.shared.model.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GeneralModelTest {
	private static GenericDAO genericDAO;

	@BeforeClass
	public static void init() {
		try {
			genericDAO = new GenericDAO();
		} catch (AbstractBusinessException e) {
		}
	}

	public GenericDAO getGenericDAO() throws AbstractBusinessException {
		return genericDAO;
	}

	@Test
	public void codeSubmissionInsert() throws AbstractBusinessException {
		GenericDAO genericDAO = getGenericDAO();
		CodeSubmission codeSubmission = new CodeSubmission();
		codeSubmission.setLanguage(Language.CPP_17);
		codeSubmission.setSourceCode("#include<iostream>");
		genericDAO.save(codeSubmission);
	}

	@Test
	public void userInsert() throws AbstractBusinessException {
		GenericDAO genericDAO = getGenericDAO();
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

		genericDAO.save(user);
	}

	@Test
	public void codingEventInsert() throws AbstractBusinessException {
		GenericDAO genericDAO = getGenericDAO();

		CodingEvent codingEvent = new CodingEvent();
		codingEvent.setEventHandle("JUNE420");
		codingEvent.setEventName("Some Great Event");
		codingEvent.setEndTime(LocalDateTime.now());
		codingEvent.setStartTime(LocalDateTime.now());
		codingEvent.setEventType(CodingEventType.CONTEST);
		codingEvent.setParticipationType(ParticipationType.FREE_GLOBAL);

		IOConstraint ioConstraint = new IOConstraint();
		ioConstraint.setDescription("");
		Map<Language, Integer> map = new HashMap<>();
		ioConstraint.setOutputLimits(map);

		MemoryConstraint memoryConstraint = new MemoryConstraint();
		memoryConstraint.setDescription("");
		memoryConstraint.setMemoryConstraints(map);

		TimeConstraint timeConstraint = new TimeConstraint();
		ioConstraint.setDescription("");
		map.put(Language.JAVA_8, 1000);
		map.put(Language.JAVA_11, 1200);
		timeConstraint.setTimeConstraints(map);

		Problem problem = new Problem();
		problem.setTimeConstraint(timeConstraint);
		problem.setMemoryConstraint(memoryConstraint);
		problem.setIoConstraint(ioConstraint);
		problem.setDescription("");
		problem.setProblemHandle("P001");
		final User user = new User();
		final User existing = genericDAO.fetch(User.class, "USER1234").get(0);
		user.setPrincipal(existing.getPrincipal());
		user.setPassword(existing.getPassword());
		problem.setCreator(user);
		problem.setName("Problem Extensive");

		problem.setCodingEvent(codingEvent);
		codingEvent.setProblemList(Arrays.asList(problem));

		genericDAO.saveOrUpdate(codingEvent);
	}

	@Test
	public void insertTimeConstraints() throws AbstractBusinessException {
		GenericDAO genericDAO = getGenericDAO();

		TimeConstraint timeConstraint = new TimeConstraint();
		timeConstraint.setDescription("Hello World!");

		Map<Language, Integer> map = new HashMap<>();
		map.put(Language.JAVA_8, 2000);
		map.put(Language.CPP_14, 500);
		timeConstraint.setTimeConstraints(map);

		genericDAO.save(timeConstraint);
	}

	@Test
	public void insertMemoryConstraints() throws AbstractBusinessException {
		GenericDAO genericDAO = getGenericDAO();

		MemoryConstraint memoryConstraint = new MemoryConstraint();
		memoryConstraint.setDescription("Hello World!");

		Map<Language, Integer> map = new HashMap<>();
		map.put(Language.JAVA_8, 2000);
		map.put(Language.CPP_14, 500);
		memoryConstraint.setMemoryConstraints(map);

		genericDAO.save(memoryConstraint);
	}

	@Test
	public void insertIOConstraints() throws AbstractBusinessException {
		GenericDAO genericDAO = getGenericDAO();

		IOConstraint ioConstraint = new IOConstraint();
		ioConstraint.setDescription("Hello World!");

		Map<Language, Integer> map = new HashMap<>();
		map.put(Language.JAVA_8, 2000);
		map.put(Language.CPP_14, 500);
		ioConstraint.setOutputLimits(map);

		genericDAO.save(ioConstraint);
	}
}