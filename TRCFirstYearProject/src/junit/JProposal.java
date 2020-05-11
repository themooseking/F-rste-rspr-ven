package junit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ffl.Rating;
import logic.Car;
import logic.Customer;
import logic.Proposal;
import logic.Salesman;

class JProposal {
	private static Customer customer;
	private static Salesman salesman;
	private static Proposal proposal;
	private static ArrayList<Car> carsList;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		customer = new Customer(88888888, "John Brick", "3213909874", "johnshitsbricks@gmail.dk", "Brick st. 11", 7400);
		salesman = new Salesman(77777777, "Hugh Hefner", "playboy@gmail.com", "JUNIOR SALES ASSISTANT", 1500000);
		proposal = new Proposal(customer, salesman);
		
		proposal.setCar(new Car(1, "pepega car", 5000000, 300, 1985, "IN STOCK"));
		proposal.setDownPayment(carsList.get(0).getPrice());
		proposal.setInterest(0);
		proposal.setLoanDuration(0);
		customer.setCreditScore(Rating.D);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

//	@Test
//	void testCalcInterestDefault() {
//		assertEquals(0.0, proposal.calcInterest());
//	}
//	
//	@Test
//	void testCalcInterestDownPayment() {
//		proposal.setDownPayment((int) (carsList.get(0).getPrice() * 0.33));
//		
//		assertEquals(1.0, proposal.calcInterest());
//	}
//	
//	@Test
//	void testCalcInterestDownPaymentExtraCar() {
//		carsList.add(new Car(2, "møjhurtig bil", 10000000, 666, 2018, "SOLD"));
//		proposal.setDownPayment((int) (carsList.get(0).getPrice()));
//		
//		assertEquals(1.0, proposal.calcInterest());
//	}
//
//	@Test
//	void testCalcInterestLoanDuration() {	
//		proposal.setLoanDuration(42);
//		
//		assertEquals(1.0, proposal.calcInterest());
//	}
//	
//	@Test
//	void testCalcInterestCreditRatingA() {	
//		customer.setCreditScore(Rating.A);
//		
//		assertEquals(1.0, proposal.calcInterest());
//	}
//
//	@Test
//	void testCalcInterestCreditRatingB() {	
//		customer.setCreditScore(Rating.B);
//		
//		assertEquals(2.0, proposal.calcInterest());
//	}
//	
//	@Test
//	void testCalcInterestCreditRatingC() {	
//		customer.setCreditScore(Rating.C);
//		
//		assertEquals(3.0, proposal.calcInterest());
//	}
//	
//	@Test
//	void testCalcInterestSum() {	
//		proposal.setDownPayment((int) (carsList.get(0).getPrice() * 0.25));
//		proposal.setInterest(5);
//		proposal.setLoanDuration(72);
//		customer.setCreditScore(Rating.C);
//		
//		assertEquals(10.0, proposal.calcInterest());
//	}
}
