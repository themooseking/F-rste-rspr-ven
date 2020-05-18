package junit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import ffl.Rating;
import logic.Car;
import logic.Customer;
import logic.Proposal;
import logic.Salesman;

public class JProposal {

	private Customer customer;
	private Salesman salesman;
	private Proposal proposal;
	private Car car;

	@Before
	public void setUp() throws Exception {
		customer = new Customer(1, 88888888, "John Brick", "3213909874", "johnbrick@gmail.dk", "Brick st. 11", 7400);
		salesman = new Salesman(1, 77777777, "Hugh Hefner", "playboy@gmail.com", "JUNIOR SALES ASSISTANT", new BigDecimal(1500000));
		car = new Car(1, "Ferrari GT12", new BigDecimal(5000000), 5, 2020, "NEW");
		proposal = new Proposal(1, car, customer, car.getPrice(), 0, LocalDate.now(), "AWAITING", Rating.D.toString(), salesman);

		proposal.setInterest(0);
	}

	@Test
	public void testCalcInterestDefault() {
		assertEquals(0.0, proposal.calcInterest());
	}

	@Test
	public void testCalcInterestDownPayment() {
		proposal.setDownPayment(car.getPrice().multiply(new BigDecimal(0.33)));

		assertEquals(1.0, proposal.calcInterest());
	}

	@Test
	public void testCalcInterestLoanDuration() {	
		proposal.setLoanDuration(42);

		assertEquals(1.0, proposal.calcInterest());
	}

	@Test
	public void testCalcInterestCreditRatingA() {	
		proposal.setCreditScore(Rating.A);

		assertEquals(1.0, proposal.calcInterest());
	}

	@Test
	public void testCalcInterestCreditRatingB() {	
		proposal.setCreditScore(Rating.B);

		assertEquals(2.0, proposal.calcInterest());
	}

	@Test
	public void testCalcInterestCreditRatingC() {	
		proposal.setCreditScore(Rating.C);

		assertEquals(3.0, proposal.calcInterest());
	}

	@Test
	public void testCalcInterestSum() {	
		proposal.setDownPayment(car.getPrice().multiply(new BigDecimal(0.25)));
		proposal.setInterest(5);
		proposal.setLoanDuration(72);
		proposal.setCreditScore(Rating.C);

		assertEquals(10.0, proposal.calcInterest());
	}

	@Test
	public void testTotalCarPrice() {	
		proposal.setDownPayment(new BigDecimal(1000000));

		assertEquals(new BigDecimal(5250000), proposal.totalCarPrice());

	}

//	@Test
//	public void testMonthlyPayment() {	
//		proposal.setDownPayment(1000000);
//		proposal.setTotalInterest(totalInterest);
//		proposal.setLoanDuration(loanDuration);
//		
//	}
//	
//	@Test
//	public void testTotalInterestSum() {	
//		
//	}
//	
//	@Test
//	public void testTotalProposalPrice() {	
//		
//	}


	//Salesman's loan limit is below the requested loan amount
	@Test
	public void testCheckLimitOver() {
		proposal.setProposalTotalSum(new BigDecimal(5250000));
		proposal.checkLimit();

		assertEquals("AWAITING", proposal.getProposalStatus());		
	}

	//Salesman's is qualified to make a loan of the requested loan amount
		@Test
		public void testCheckLimitUnder() {
			proposal.setProposalTotalSum(new BigDecimal(5250000));
			salesman.setProposalLimit(new BigDecimal(10000000));
			proposal.checkLimit();

			assertEquals("ONGOING", proposal.getProposalStatus());		
		}
}