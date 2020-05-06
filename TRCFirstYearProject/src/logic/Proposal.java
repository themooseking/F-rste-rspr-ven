package logic;

import java.time.LocalDate;
import java.util.ArrayList;

import ffl.InterestRate;

public class Proposal extends Thread {
	static int loanDurationLimit = 36; // 3 Years in months

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
		start();
	}

	public double calcInterest() {
		double customerInterest = 0;

		switch (customer.getCreditScore()) {
		case A:
			customerInterest += 0.01;
			break;

		case B:
			customerInterest += 0.02;
			break;

		case C:
			customerInterest += 0.03;
			break;
		default:
			break;
		}

		if (downPayment < calcTotalCarPrice() * 0.50) {
			customerInterest += 0.01;
		}

		if (loanDurationLimit < loanDuration) {
			customerInterest += 0.01;
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
		setInterest();
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

	private void setInterest() {
		interest = InterestRate.i().todaysRate();
	}
}
