package junit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import logic.Car;
import logic.DB_Controller;

public class JCar {
	DB_Controller controller = new DB_Controller();
	Car car = new Car(1, "812 Superfast", 5600000, 0, 2017, "AVAILABLE");

	@Test
	public void testTypeOfGetCar() {
		assertTrue(controller.getNewCars().get(0) instanceof Car);
	}
	
//	@Test
//	void testGetNewCarsCarStatus() {
//		assertEquals(car.getCarStatus(), controller.getNewCars().get(0).getCarStatus());
//	}
	
//	@Test
//	void testGetNewCarsFactory() {
//		assertEquals(car.getFactory(), controller.getNewCars().get(0).getFactory());
//	}
	
	@Test
	public void testGetNewCarsMilage() {
		assertEquals(car.getMilage(), controller.getNewCars().get(0).getMilage());
	}
	
	@Test
	public void testGetNewCarsModel() {
		assertEquals(car.getModel(), controller.getNewCars().get(0).getModel());
	}
	
	@Test
	public void testGetNewCarsPrice() {
		assertEquals(car.getPrice(), controller.getNewCars().get(0).getPrice());
	}
	
	@Test
	public void testToString() {
		assertEquals("[1]	812 Superfast", car.toString());
	}
	
	@Test
	public void testGetPrice() {
		assertEquals(5600000, car.getPrice());
	}
	
	@Test
	public void testCarVarPrice() {
		assertEquals(1400000, car.getVat());
	}

}
