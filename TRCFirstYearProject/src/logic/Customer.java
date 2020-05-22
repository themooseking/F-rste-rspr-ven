package logic;

import com.ferrari.finances.dk.rki.CreditRator;
import com.ferrari.finances.dk.rki.Rating;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Customer extends Thread{

	private int customerId;
	private int phone;
	private int postalCode;
	private String customerName;
	private String cpr;
	private String email;
	private String customerAddress;
	private Rating creditScore;
	private StringProperty creditScoreProperty;
	
	public Customer(int customerId, int phone, String customerName, String cpr, String email, String customerAddress, int postalCode) {
		this.customerId = customerId;
		this.phone = phone;
		this.customerName = customerName;
		this.cpr = removeDashFromCpr(cpr);
		this.email = email;
		this.customerAddress = customerAddress;
		this.postalCode = postalCode;
		this.creditScoreProperty = new SimpleStringProperty("");
		start();
	}
	
	public void run() {
		creditScore = CreditRator.i().rate(cpr);
		creditScoreProperty.set(creditScore.toString());
	}

	static public String removeDashFromCpr(String cpr) {
		String cprNoDash = cpr;
		
		if(10 < cpr.length()) {
			cprNoDash = cpr.substring(0, 6) + cpr.substring(7, 11);
		}
		
		return cprNoDash;
	}
	
	public StringProperty creditScoreProperty() {
		return creditScoreProperty;
	}
	
	//////////////////////////////
	// GETTERS
	//////////////////////////////
	
	public Rating getCreditScore() {
		return creditScore;
	}

	public String getCpr() {
		return cpr;
	}

	public int getCustomerId() {
		return customerId;
	}

	public int getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public String getCustomerName() {
		return customerName;
	}
	
	public int getPostalCode() {
		return postalCode;
	}
	
	//////////////////////////////
	// SETTERS
	//////////////////////////////	

	public void setCreditScore(Rating creditScore) {
		this.creditScore = creditScore;
	}
	
	public void setCpr(String cpr) {
		this.cpr = removeDashFromCpr(cpr);
	}
	
	@Override
	public String toString() {
		return customerName;		
	}	
}
