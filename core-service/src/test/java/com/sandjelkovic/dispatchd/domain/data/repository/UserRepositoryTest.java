package com.sandjelkovic.dispatchd.domain.data.repository;

import com.sandjelkovic.dispatchd.DispatchdApplication;
import com.sandjelkovic.dispatchd.domain.data.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = {"testing"})
@SpringBootTest(classes = {DispatchdApplication.class})
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void testFindByUsername() {
		User user = new User();
		user.setUsername("root");
		user.setEmail("root@root.com");
		user.setPassw("rootpass");
		userRepository.save(user);

		Optional<User> retrieved = userRepository.findOneByUsername("root");
		assertTrue("Should be present", retrieved.isPresent());
		assertEquals("Should be root", "root", retrieved.get().getUsername());

		userRepository.delete(user);
	}
}
