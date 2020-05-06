package logic;

import ffl.CreditRator;
import ffl.Rating;

public class Customer extends Thread{

	private int customerId;
	private int phone;
	private String customerName;
	private String cpr;
	private String email;
	private String customerAddress;
	private int postalCode;
	private Rating creditScore;
	
	public Customer(int phone, String customerName, String cpr, String email, String customerAddress, int postalCode) {
		this.phone = phone;
		this.customerName = customerName;
		this.cpr = cpr;
		this.email = email;
		this.customerAddress = customerAddress;
		this.postalCode = postalCode;
		start();
	}
	
	public void run() {
		setCreditScore();
		System.out.println(creditScore);
	}

	public Rating getCreditScore() {
		return creditScore;
	}

	public String getCpr() {
		return cpr;
	}

	private void setCreditScore() {
		creditScore = CreditRator.i().rate(cpr);
	}

	public int getCustomerId() {
		return customerId;
	}
	
}
