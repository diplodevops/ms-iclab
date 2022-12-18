
package com.devopsusach2020;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.devopsusach2020.rest.RestData;

@SpringBootTest
public class EstadoPais {
    RestData rest = new RestData();

	@Test
	void estadoMuertesPais() throws Exception {
		
		assertEquals(true,rest.ValidaApiCovid().getStatusAPI());
	    assertTrue(rest.getTotalPais("Chile").getDeaths() >= 0);
	}
	@Test
	void estadoConfirmadoPais() throws Exception {
		
		assertEquals(true,rest.ValidaApiCovid().getStatusAPI());
	    assertTrue(rest.getTotalPais("Chile").getConfirmed() >= 0);
	}
	@Test
	void estadoMensajePais() throws Exception {
		
		assertEquals(true,rest.ValidaApiCovid().getStatusAPI());
	    assertEquals("ok", rest.getTotalPais("Chile").getMensaje());
	}
}