package logic;

import java.time.LocalDate;
import java.util.ArrayList;

import ffl.InterestRate;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Proposal extends Thread {
	static int loanDurationLimit = 36; // 3 Years in months

	private DoubleProperty doubleProperty;
	private int proposalId;
	private Customer customer;
	private double interest;
	private int downPayment;
	private int loanDuration;
	private LocalDate proposalDate;
	private String proposalStatus;
	private Salesman salesman;
	private ArrayList<Car> carsList;

	public Proposal(Customer customer, Salesman salesman) {
		this.customer = customer;
		this.proposalDate = LocalDate.now();
		this.proposalStatus = "ONGOING";
		this.salesman = salesman;
		this.doubleProperty = new SimpleDoubleProperty(0);
		start();
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

		if (downPayment < calcTotalCarPrice() * 0.50) {
			customerInterest += 1.0;
		}

		if (loanDurationLimit < loanDuration) {
			customerInterest += 1.0;
		}
		
		return customerInterest + interest;
	}

	private double calcTotalCarPrice() {
		int totalCarPrice = 0;

		for (int i = 0; i < carsList.size(); i++) {
			totalCarPrice += carsList.get(i).getPrice();
		}

		return totalCarPrice;
	}

	public void run() {		
		interest = InterestRate.i().todaysRate();
		doubleProperty.set(interest);
	}
	
	public DoubleProperty doubleProperty() {
		return doubleProperty;
	}
	
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

	public void setCarsList(ArrayList<Car> carsList) {
		this.carsList = carsList;
	}

	public LocalDate getDate() {
		return proposalDate;
	}

	public int getProposalId() {
		return proposalId;
	}

	public Customer getCustomer() {
		return customer;
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
}
