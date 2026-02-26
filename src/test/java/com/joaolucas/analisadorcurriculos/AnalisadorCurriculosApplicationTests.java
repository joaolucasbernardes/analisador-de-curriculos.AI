package com.joaolucas.analisadorcurriculos;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
		"spring.ai.google.genai.api-key=test_key",
		"app.security.api-key=test_key"
})
class AnalisadorCurriculosApplicationTests {

	@Test
	void contextLoads() {
	}

}
