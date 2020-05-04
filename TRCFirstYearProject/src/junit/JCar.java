package junit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import logic.Car;
import logic.DB_Controller;

class JCar {
	
	DB_Controller controller = new DB_Controller();
	Car car = new Car("812 Superfast", 5600000, 0, 2017, "AVAILABLE");

	@Test
	void testTypeOfGetCar() {
		assertTrue(controller.getNewCars().get(0) instanceof Car);
	}
	
	@Test
	void testGetNewCarsCarStatus() {
		assertEquals(car.getCarStatus(), controller.getNewCars().get(0).getCarStatus());
	}
	
	@Test
	void testGetNewCarsFactory() {
		assertEquals(car.getFactory(), controller.getNewCars().get(0).getFactory());
	}
	
	@Test
	void testGetNewCarsMilage() {
		assertEquals(car.getMilage(), controller.getNewCars().get(0).getMilage());
	}
	
	@Test
	void testGetNewCarsModel() {
		assertEquals(car.getModel(), controller.getNewCars().get(0).getModel());
	}
	
	@Test
	void testGetNewCarsPrice() {
		assertEquals(car.getPrice(), controller.getNewCars().get(0).getPrice());
	}
	
	@Test
	void testToString() {
		assertEquals("812 Superfast", car.toString());
	}
	
	@Test
	void testGetPrice() {
		assertEquals(5600000, car.getPrice());
	}

}
