package junit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import com.ferrari.finances.dk.rki.Rating;

import logic.Car;
import logic.Customer;
import logic.Proposal;
import logic.Salesman;
import logic.Status;

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
		proposal = new Proposal(1, car, customer, car.getPrice(), 0, LocalDate.now(), Status.AFVENTER, Rating.D.toString(), salesman);

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
		proposal.setDownPayment(new BigDecimal(0));
		
		assertEquals(new BigDecimal(6250000).setScale(2, RoundingMode.HALF_UP), proposal.totalCarPrice());
	}

	@Test
	public void testMonthlyPayment() {	
		proposal.setDownPayment(new BigDecimal(0));
		proposal.setTotalInterest(10);
		proposal.setLoanDuration(36);
		
		assertEquals(new BigDecimal(200407.49).setScale(2, RoundingMode.HALF_UP), proposal.monthlyPayment().setScale(2, RoundingMode.HALF_UP));
	}
	
	@Test
	public void testTotalInterestSum() {	
		proposal.setDownPayment(new BigDecimal(0));
		proposal.setTotalInterest(10);
		proposal.setLoanDuration(36);
		
		assertEquals(new BigDecimal(964669.80).setScale(2, RoundingMode.HALF_UP), proposal.totalInterestSum().setScale(2, RoundingMode.HALF_UP));
	}
	
	@Test
	public void testTotalProposalPrice() {	
		proposal.setDownPayment(new BigDecimal(0));
		proposal.setTotalInterest(10);
		proposal.setLoanDuration(36);
		proposal.totalProposalPrice();
		
		assertEquals(new BigDecimal(7214669.80).setScale(2, RoundingMode.HALF_UP), proposal.getProposalTotalSum().setScale(2, RoundingMode.HALF_UP));
	}
}