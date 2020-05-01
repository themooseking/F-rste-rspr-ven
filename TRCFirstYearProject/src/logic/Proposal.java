package logic;

import java.time.LocalDate;
import java.util.ArrayList;

import ffl.InterestRate;

public class Proposal {
	static int loanDurationLimit = 36; // 3 Years in months

	private Customer customer;
	private double interest;
	private int downPayment;
	private int loanDuration;
	private LocalDate proposalDate;
	private String proposalStatus;
	private Salesman salesman;
	private ArrayList<Car> carsList;

	public Proposal(Customer customer, double interest, int downPayment, int loanDuration, LocalDate proposalDate,
			String proposalStatus, Salesman salesman, ArrayList<Car> carsList) {
		this.customer = customer;
		this.interest = interest;
		this.downPayment = downPayment;
		this.loanDuration = loanDuration;
		this.proposalDate = proposalDate;
		this.proposalStatus = proposalStatus;
		this.salesman = salesman;
		this.carsList = carsList;
		setInterest();
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

	public Object getDate() {
		return proposalDate;
	}
	
	public double getInterest() {
		return interest;
	}

	private void setInterest() {
		interest = InterestRate.i().todaysRate();
	}
}
