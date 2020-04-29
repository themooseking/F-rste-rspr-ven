package logic;

import java.time.LocalDate;

public class Proposal {
	
	private Customer customer;
	private double interest;
	private long downPayment;
	private int loanDuration;
	private LocalDate date;
	private String status;
	private Salesman salesman;

	public Proposal(Customer customer, double interest, long downPayment, int loanDuration, LocalDate date,
			String status, Salesman salesman) {
		this.customer = customer;
		this.interest = interest;
		this.downPayment = downPayment;
		this.loanDuration = loanDuration;
		this.date = date;
		this.status = status;
		this.salesman = salesman;
	}

	public Object getDate() {
		return date;
	}

}
