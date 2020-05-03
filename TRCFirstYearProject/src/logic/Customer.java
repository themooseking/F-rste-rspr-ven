package logic;

import ffl.CreditRator;
import ffl.Rating;

public class Customer {

	private int id;
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
		setCreditScore();
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

	public int getId() {
		return id;
	}
	
}
