package junit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

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
	private static Car car;

	@BeforeEach
	void setUp() throws Exception {
		customer = new Customer(1, 88888888, "John Brick", "3213909874", "johnbrick@gmail.dk", "Brick st. 11", 7400);
		salesman = new Salesman(1, 77777777, "Hugh Hefner", "playboy@gmail.com", "JUNIOR SALES ASSISTANT", 1500000);
		car = new Car(1, "Ferrari GT12", 5000000, 5, 2020, "NEW");
		proposal = new Proposal(1, car, customer, car.getPrice(), 0, LocalDate.now(), "AWAITING", Rating.D.toString(), salesman);

		proposal.setInterest(0);
	}

	@Test
	void testCalcInterestDefault() {
		assertEquals(0.0, proposal.calcInterest());
	}
	
	@Test
	void testCalcInterestDownPayment() {
		proposal.setDownPayment((int) (car.getPrice() * 0.33));
		
		assertEquals(1.0, proposal.calcInterest());
	}

	@Test
	void testCalcInterestLoanDuration() {	
		proposal.setLoanDuration(42);
		
		assertEquals(1.0, proposal.calcInterest());
	}
	
	@Test
	void testCalcInterestCreditRatingA() {	
		proposal.setCreditScore(Rating.A);
		
		assertEquals(1.0, proposal.calcInterest());
	}

	@Test
	void testCalcInterestCreditRatingB() {	
		proposal.setCreditScore(Rating.B);
		
		assertEquals(2.0, proposal.calcInterest());
	}
	
	@Test
	void testCalcInterestCreditRatingC() {	
		proposal.setCreditScore(Rating.C);
		
		assertEquals(3.0, proposal.calcInterest());
	}
	
	@Test
	void testCalcInterestSum() {	
		proposal.setDownPayment((int) (car.getPrice() * 0.25));
		proposal.setInterest(5);
		proposal.setLoanDuration(72);
		proposal.setCreditScore(Rating.C);
		
		assertEquals(10.0, proposal.calcInterest());
	}
}
