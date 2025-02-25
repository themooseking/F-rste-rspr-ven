package logic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import com.ferrari.finances.dk.bank.InterestRate;
import com.ferrari.finances.dk.rki.Rating;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Proposal extends Thread {
	static int loanDurationLimit = 36; // 3 Years in months

	private int proposalId;
	private int loanDuration;
	private double interest;
	private double totalInterest;
	private BigDecimal downPayment;
	private BigDecimal proposalTotalSum;
	private DoubleProperty interestProperty;
	private LocalDate proposalDate;
	private Car car;
	private Customer customer;
	private Salesman salesman;
	private Status proposalStatus;
	private Rating creditScore;
	private DB_Controller controller = new DB_Controller();

	public Proposal(Customer customer, Salesman salesman) {
		this.customer = customer;
		this.proposalDate = LocalDate.now();
		this.salesman = salesman;
		this.interestProperty = new SimpleDoubleProperty(0);
		start();
	}

	public Proposal(int proposalId, Car car, Customer customer, BigDecimal downPayment, int loanDuration,
			LocalDate proposalDate, Status proposalStatus, String creditScore, Salesman salesman) {
		this.proposalId = proposalId;
		this.car = car;
		this.customer = customer;
		this.downPayment = downPayment;
		this.loanDuration = loanDuration;
		this.proposalDate = proposalDate;
		this.creditScore = Rating.valueOf(creditScore);
		this.proposalStatus = proposalStatus;
		this.salesman = salesman;
		this.interest = controller.getInterest(proposalDate);
		calcInterest();
	}

	public void run() {
		double dbInterest = controller.getInterest(proposalDate);

		if (dbInterest <= -1.0) {
			interest = InterestRate.i().todaysRate();
			controller.createInterest(interest);
		} else {
			interest = dbInterest;
		}

		interest = Math.round(interest * 10000.0) / 10000.0;
		interestProperty.set(interest);
	}
	
	public double calcInterest() {
		totalInterest = 0;
		if (creditScore == null) {
			creditScore = customer.getCreditScore();
		}

		switch (creditScore) {
		case A:
			totalInterest += 1.0;
			break;

		case B:
			totalInterest += 2.0;
			break;

		case C:
			totalInterest += 3.0;
			break;
		default:
			break;
		}

		if (downPayment.compareTo(car.getPrice().multiply(new BigDecimal(0.50))) <= 0) {
			totalInterest += 1.0;
		}

		if (loanDurationLimit < loanDuration) {
			totalInterest += 1.0;
		}

		interest = Math.round(interest * 10000.0) / 10000.0;
		totalInterest += interest;

		return totalInterest;
	}

	public DoubleProperty interestProperty() {
		return interestProperty;
	}

	public BigDecimal totalCarPrice() {
		return car.getPrice().add(car.getVat()).subtract(downPayment);
	}

	/*****************************************************
	 * Calculates monthly payments with a yearly accrual of interest, and monthly
	 * payment
	 * <p/>
	 * 
	 * <ul>
	 * <li><b>interest:</b> r_month = (1 + r_year)^(1/12) - 1;</li>
	 * 
	 * <li><b> payment:</b> y = G * (r / 1 - (1 + r)^(-n));</li>
	 * <ul>
	 * <li>where G is Principal,</li>
	 * <li>r is interest,</li>
	 * <li>n is number of due dates</li>
	 * </ul>
	 *****************************************************/

	public BigDecimal monthlyPayment() {
		double r = monthlyRate();
		BigDecimal payment = totalCarPrice().multiply(new BigDecimal(r / (1 - Math.pow(1 + r, -loanDuration))));

		return payment.setScale(4, RoundingMode.HALF_UP);
	}

	public BigDecimal totalInterestSum() {
		BigDecimal totalInterestSum = monthlyPayment().multiply(new BigDecimal(loanDuration));
		return totalInterestSum.subtract(totalCarPrice());
	}

	public void totalProposalPrice() {
		proposalTotalSum = totalCarPrice().add(totalInterestSum());
	}

	public void checkLimit() {
		int limitCheck = salesman.getProposalLimit().compareTo(proposalTotalSum);
		int salesChiefCheck = salesman.getProposalLimit().compareTo(new BigDecimal(-1));

		if (salesChiefCheck != 0 && limitCheck <= 0) {
			proposalStatus = Status.AFVENTER;
		} else {
			proposalStatus = Status.IGANG;
		}
		
		controller.createProposal(this);
	}
	
	public BigDecimal monthlyRateAmount(BigDecimal remainingLoanAmount) {
		return remainingLoanAmount.multiply(new BigDecimal(monthlyRate()));
	}
	
	public BigDecimal repayment(BigDecimal monthlyRateAmount) {
		BigDecimal annuity = monthlyPayment();
		
		return annuity.subtract(monthlyRateAmount);
	}
	
	public BigDecimal remainingLoanAmount(BigDecimal loanAmount, BigDecimal repayment) {
		return loanAmount.subtract(repayment);
	}

	private double monthlyRate() {
		return Math.pow((1.0 + totalInterest / 100.0), 1.0 / 12.0) - 1;
	}
	
	//////////////////////////////
	// GETTERS
	//////////////////////////////

	public LocalDate getProposalDate() {
		return proposalDate;
	}

	public int getProposalId() {
		return proposalId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public Car getCar() {
		return car;
	}

	public Status getProposalStatus() {
		return proposalStatus;
	}

	public BigDecimal getDownPayment() {
		return downPayment;
	}

	public int getLoanDuration() {
		return loanDuration;
	}

	public Salesman getSalesman() {
		return salesman;
	}

	public String getSalesmanTitel() {
		return salesman.getTitle();
	}

	public double getInterest() {
		return interest;
	}

	public double getTotalInterest() {
		double interest = Math.round(totalInterest * 100.0) / 100.0;
		
		return interest;
	}

	public BigDecimal getProposalTotalSum() {
		totalProposalPrice();
		BigDecimal totalSum = proposalTotalSum.setScale(2, RoundingMode.HALF_UP);
		
		return totalSum;
	}

	public Rating getCreditScore() {
		return creditScore;
	}

	//////////////////////////////
	// SETTERS
	//////////////////////////////

	public void setInterest(double interest) {
		this.interest = interest;
	}
	
	public void setTotalInterest(double totalInterest) {
		this.totalInterest = totalInterest;
	}


	public void setProposalTotalSum(BigDecimal proposalTotalSum) {
		this.proposalTotalSum = proposalTotalSum;
	}

	public void setDownPayment(BigDecimal downPayment) {
		this.downPayment = downPayment;
	}

	public void setLoanDuration(int loanDuration) {
		this.loanDuration = loanDuration;
	}

	public void setProposalStatus(Status proposalStatus) {
		this.proposalStatus = proposalStatus;
		controller.updateProposalStatus(this);
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public void setCreditScore(Rating creditScore) {
		this.creditScore = creditScore;
	}
}
