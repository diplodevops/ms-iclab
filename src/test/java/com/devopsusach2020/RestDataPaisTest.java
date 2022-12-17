package com.devopsusach2020;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.devopsusach2020.model.Pais;
import com.devopsusach2020.rest.RestData;

public class RestDataPaisTest {
	RestData rest = new RestData();
	String paisTest = "Peru";
	Pais pais = rest.getTotalPais(paisTest);

	@Test
	void testGetPais() {
		assertEquals(paisTest, pais.getCountry());
	}

	@Test 
	void testGetActivesEsCorrecto() {
		assertTrue(pais.getConfirmed() >= pais.getActive());
	}

	@Test 
	void testGetMuertosEsCorrecto() {
		assertTrue(pais.getConfirmed() >= pais.getDeaths());
	}

	@Test 
	void testGetMRecuperadosEsCorrecto() {
		assertTrue(pais.getConfirmed() >= pais.getRecovered());
	}

	@Test 
	void testGetTotalesEsCorrecto() {
		assertTrue(pais.getConfirmed() >= pais.getActive() + pais.getDeaths() + pais.getRecovered());
	}

	@Test
	void testGetPaisVacio() {
		assertThrows(Exception.class, () -> {
			rest.getTotalPais("");
		});
	}
}
