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
		this.cpr = removeDashFromCpr(cpr);
		this.email = email;
		this.customerAddress = customerAddress;
		this.postalCode = postalCode;
		start();
	}
	
	private String removeDashFromCpr(String cpr) {
		String cprNoDash = cpr;
		
		if(10 < cpr.length()) {
			cprNoDash = cpr.substring(0, 6) + cpr.substring(7, 11);
		}
		
		return cprNoDash;
	}
	
	public void run() {
		creditScore = CreditRator.i().rate(cpr);
	}

	public void setCreditScore(Rating creditScore) {
		this.creditScore = creditScore;
	}
	
	public void setCpr(String cpr) {
		this.cpr = removeDashFromCpr(cpr);
	}

	public Rating getCreditScore() {
		return creditScore;
	}

	public String getCpr() {
		return cpr;
	}

	public int getCustomerId() {
		return customerId;
	}
	
}
