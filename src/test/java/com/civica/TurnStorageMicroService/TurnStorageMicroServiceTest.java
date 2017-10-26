package com.civica.TurnStorageMicroService;

import com.civica.grads.boardgames.web.TurnStorageMicroServiceApplication;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TurnStorageMicroServiceApplication.class)
@WebAppConfiguration
public class TurnStorageMicroServiceTest {

	@Test
	public void turnHasMoves() {
		
	}

}
