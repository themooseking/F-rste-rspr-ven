package logic;

import ffl.CreditRator;
import ffl.Rating;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Customer extends Thread{

	private StringProperty stringProperty;
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
		this.stringProperty = new SimpleStringProperty("");
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
		stringProperty.set(creditScore.toString());
	}
	
	public StringProperty stringProperty() {
		return stringProperty;
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
