package com.devopsusach2020;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.devopsusach2020.model.Mundial;
import com.devopsusach2020.rest.RestData;

public class RestDataMundialTest {
	RestData rest = new RestData();
	long serialVersionUID = 3908000903498620526L;
	Mundial mundial = rest.getTotalmundial();

	@Test
	void testMundial() {
		assertEquals(serialVersionUID, mundial.getSerialVersionUID());
	}

	@Test 
	void testTotalConfirmedEsCorrecto() {
		assertTrue(mundial.getTotalConfirmed() > mundial.getTotalRecovered() + mundial.getTotalDeaths());
	}

	@Test
	void testReviewClass() {
		assertTrue(mundial.getClass() == rest.getTotalmundial().getClass());
	}
}
