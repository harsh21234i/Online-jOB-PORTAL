package com.jobportal.job_portal;

import com.jobportal.job_portal.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@Import(TestConfig.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class JobPortalApplicationTests {

	@Test
	void contextLoads() {
		// Test passes if ApplicationContext loads successfully
	}
}
