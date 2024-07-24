package sbs.practice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sbs.practice.service.IProjectService;

@SpringBootTest
class SbsPracticeApplicationTests {

	@Autowired
	private IProjectService projectService;

	@Test
	void contextLoads() {
	}

}
