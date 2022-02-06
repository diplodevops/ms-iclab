package com.devopsusach2020;

import com.devopsusach2020.model.Pais;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DevOpsUsach2020ApplicationTests {

	@Test
	void contextLoads() {
		Pais p = new Pais();
		assertNotNull(p);
	}
	


}
