package junit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logic.Customer;

class JCustomer {
	private Customer customer;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		customer = new Customer(88888888, "John Brick", "3213909874", "johnshitsbricks@gmail.dk", "Brick st. 11", 7400);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testConstructorDefault() {
		Customer customer = new Customer(88888888, "John Brick", "3213909874", "johnshitsbricks@gmail.dk", "Brick st. 11", 7400);
		assertEquals("3213909874", customer.getCpr());
	}
	
	@Test
	void testConstructorWithDash() {
		Customer customerWithDash = new Customer(88888888, "John Brick", "321390-9874", "johnshitsbricks@gmail.dk", "Brick st. 11", 7400);
		assertEquals("3213909874", customerWithDash.getCpr());
	}
	
	@Test
	void testCprDashRemover() {
		customer.setCpr("321390-9874");
		assertEquals("3213909874", customer.getCpr());
	}
}
