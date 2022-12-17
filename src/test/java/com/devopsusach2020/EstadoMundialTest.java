package com.devopsusach2020;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.devopsusach2020.rest.RestData;

@SpringBootTest
public class EstadoMundialTest {
    RestData rest = new RestData();

	@Test
	void estadoMundialTotalconfirmed() {
		assertEquals(true,rest.ValidaApiCovid().getStatusAPI());
		assertTrue(rest.getTotalMundial().getTotalConfirmed() >= 0);
	}

    @Test
	void estadoMundialTotalDeath() {
		assertEquals(true,rest.ValidaApiCovid().getStatusAPI());
		assertTrue(rest.getTotalMundial().getTotalDeaths() >= 0);
	}

    @Test
	void estadoMundialTotalRecovered() {
		assertEquals(true,rest.ValidaApiCovid().getStatusAPI());
		assertTrue(rest.getTotalMundial().getTotalRecovered() >= 0);
	}

}