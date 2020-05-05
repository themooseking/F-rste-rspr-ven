package junit;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logic.Customer;
import logic.Proposal;
import logic.Salesman;

class JProposal {
	
	private static Proposal proposal;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
//		Customer customer = new Customer(88888888, "John Brick", 321390-9875, "johnshitsbricks@gmail.dk", "Brick st. 11", 7400);
		double interest = 5.5;
		long downPayment = 700000;
		int loanDuration = 90;
		LocalDate date = LocalDate.now();
		String status = "ONGOING";
		Salesman salesman = new Salesman(77777777, "Hugh Hefner", "playboy@gmail.com", "JUNIOR SALES ASSISTANT", 1500000);
//		proposal = new Proposal(customer, interest, downPayment, loanDuration, date, status, salesman);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testProposalConstructor() {		
//		assertEquals(LocalDate.now(), proposal.getDate());
	}
	
	@Test
	void testBankInterest() {		
//		assertEquals(expected, actual);
	}

}
