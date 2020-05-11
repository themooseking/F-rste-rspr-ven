package logic;

import java.time.LocalDate;

import ffl.InterestRate;
import ffl.Rating;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Proposal extends Thread {
	static int loanDurationLimit = 36; // 3 Years in months

	private DB_Controller controller = new DB_Controller();
	private DoubleProperty doubleProperty;
	private int proposalId;
	private Car car;
	private Customer customer;
	private double interest;
	private int downPayment;
	private int loanDuration;
	private LocalDate proposalDate;
	private String proposalStatus;
	private Rating creditScore;
	private Salesman salesman;
	private double proposalTotalSum;

	public Proposal(Customer customer, Salesman salesman) {
		this.customer = customer;
		this.proposalDate = LocalDate.now();
		this.proposalStatus = "ONGOING";
		this.salesman = salesman;
		this.doubleProperty = new SimpleDoubleProperty(0);
		start();
	}
	
	public Proposal(int proposalId, Car car, Customer customer, int downPayment, int loanDuration, 
			LocalDate proposalDate, String proposalStatus, String creditScore, Salesman salesman){
		this.proposalId = proposalId;
		this.car = car;
		this.customer = customer;
		this.downPayment = downPayment;
		this.loanDuration = loanDuration;
		this.proposalDate = proposalDate;
		this.creditScore = Rating.valueOf(creditScore);
		this.proposalStatus = proposalStatus;
		this.salesman = salesman;
	}

	public double calcInterest() {
		double customerInterest = 0;

		switch (customer.getCreditScore()) {
		case A:
			customerInterest += 1.0;
			break;

		case B:
			customerInterest += 2.0;
			break;

		case C:
			customerInterest += 3.0;
			break;
		default:
			break;
		}

		if (downPayment < car.getPrice() * 0.50) {
			customerInterest += 1.0;
		}

		if (loanDurationLimit < loanDuration) {
			customerInterest += 1.0;
		}
		
		return customerInterest + interest;
	}
	
	public void run() {		
		interest = InterestRate.i().todaysRate();
		doubleProperty.set(interest);
		controller.createInterest(interest);
	}
	
	public DoubleProperty doubleProperty() {
		return doubleProperty;
	}
	
	
	public double totalCarPrice() {
		return car.getPrice() + car.getVat() - downPayment;
	}
	
	public double monthlyPayment() {
		double r = Math.pow((1.0 + calcInterest() / 100.0), 1.0 / 12.0) - 1;
		return totalCarPrice() * (r / (1 - Math.pow(1 + r, -loanDuration)));
	}
	
	public double totalInterestSum() {
		return monthlyPayment() * loanDuration - totalCarPrice();
	}
	
	public double totalProposalPrice() {
		return totalCarPrice() + totalInterestSum();
	}
	
	private void checkInterestDB() {
		double interest = controller.getInterest(proposalDate);
		
		if(interest <= -1.0) {
			start();
		} else {
			this.interest = interest;
		}
	}
	
	/***********************************
	 * SETTERS
	 ***********************************/
	
	public void setInterest(double interest) {
		this.interest = interest;
	}
	
	public void setDownPayment(int downPayment) {
		this.downPayment = downPayment;
	}

	public void setLoanDuration(int loanDuration) {
		this.loanDuration = loanDuration;
	}

	public void setProposalStatus(String proposalStatus) {
		this.proposalStatus = proposalStatus;
	}
	
	public void setCar(Car car) {
		this.car = car;
	}
	
	/***********************************
	 * GETTERS
	 ***********************************/

	public LocalDate getDate() {
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

	public String getProposalStatus() {
		return proposalStatus;
	}

	public int getDownPayment() {
		return downPayment;
	}

	public int getLoanDuration() {
		return loanDuration;
	}

	public Salesman getSalesman() {
		return salesman;
	}

	public double getInterest() {
		return interest;
	}
	
	public double getProposalTotalSum() {
		double totalCarPriceWithVAT = car.getPrice() * 1.25;
		double loanInterestRate = calcInterest();
		proposalTotalSum = totalCarPriceWithVAT*(loanInterestRate/(1 - (Math.pow((1 + loanInterestRate), -loanDuration))));
		
		return proposalTotalSum;
	}
}
