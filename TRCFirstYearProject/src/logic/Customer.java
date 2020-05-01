package logic;

public class Customer {

	private int phone;
	private String customerName;
	private String cpr;
	private String email;
	private String customerAddress;
	private int postalCode;
	private char creditScore;
	
	public Customer(int phone, String customerName, String cpr, String email, String customerAddress, int postalCode, char creditScore) {
		this.phone = phone;
		this.customerName = customerName;
		this.cpr = cpr;
		this.email = email;
		this.customerAddress = customerAddress;
		this.postalCode = postalCode;
		this.creditScore = creditScore;
	}

}
