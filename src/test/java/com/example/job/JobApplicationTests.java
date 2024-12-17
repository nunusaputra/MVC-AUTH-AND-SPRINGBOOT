package com.example.job;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.job.dto.UserDTO;
import com.example.job.repository.JobRepository;
import com.example.job.repository.UsersRepository;

@SpringBootTest
class JobApplicationTests {
	private JobRepository jobRepository;
	private UsersRepository usersRepository;

	@Autowired
	public JobApplicationTests(UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}

	@Test
	void contextLoads() {
		Integer expected = 5;

		Integer actualResult = jobRepository.getJob().size();

		assertEquals(expected, actualResult);
	}

	@Test
	void loginTest() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		UserDTO user = usersRepository.getUsers("wisnu@gmail.com");

		String actualResult = user.getPassword();

		assertTrue(encoder.matches("wisnu123", actualResult));

	}

}
