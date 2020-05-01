package logic;

public class Salesman {
	
	private int phone;
	private String salesmanName;
	private String email;
	private String title;
	private int proposalLimit;
	
	public Salesman(int phone, String salesmanName, String email, String title, int proposalLimit) {
		this.phone = phone;
		this.salesmanName = salesmanName;
		this.email = email;
		this.title = title;
		this.proposalLimit = proposalLimit;
	}

	@Override
	public String toString() {
		return salesmanName;		
	}

}
