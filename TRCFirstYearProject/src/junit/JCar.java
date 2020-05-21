package junit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Before;
import org.junit.Test;

import logic.Car;

public class JCar {
	private Car car;

	@Before
	public void setUp() throws Exception {
		car = new Car(1, "812 Superfast", new BigDecimal(5600000.0000), 0, 2017, "AVAILABLE");
	}

	@Test
	public void testToString() {
		assertEquals("[1]	812 Superfast", car.toString());
	}

	@Test
	public void testGetPrice() {
		assertEquals(new BigDecimal(5600000), car.getPrice());
	}

	@Test
	public void testCarVarPrice() {
		assertEquals(new BigDecimal(1400000.00).setScale(2, RoundingMode.HALF_UP),
				car.getVat().setScale(2, RoundingMode.HALF_UP));
	}
}