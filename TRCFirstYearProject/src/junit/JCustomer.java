package junit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;

import logic.Customer;

public class JCustomer {

	private Customer customer;

	@Before
	public void setUp() throws Exception {
		customer = new Customer(1, 88888888, "John Brick", "3213909874", "johnshitsbricks@gmail.dk", "Brick st. 11", 7400);
	}

	@Test
	public void testConstructorDefault() {
		assertEquals("3213909874", customer.getCpr());
	}

	@Test
	public void testConstructorWithDash() {
		Customer customerWithDash = new Customer(1, 88888888, "John Brick", "321390-9874", "johnshitsbricks@gmail.dk", "Brick st. 11", 7400);
		assertEquals("3213909874", customerWithDash.getCpr());
	}

	@Test
	public void testCprDashRemover() {
		String cpr = "321390-9874";
		assertEquals("3213909874", Customer.removeDashFromCpr(cpr));
	}

}