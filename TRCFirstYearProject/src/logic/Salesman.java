package logic;

public class Salesman {
	
	private int phone;
	private String name;
	private String email;
	private String title;
	private int proposalLimit;
	
	public Salesman(int phone, String name, String email, String title, int proposalLimit) {
		this.phone = phone;
		this.name = name;
		this.email = email;
		this.title = title;
		this.proposalLimit = proposalLimit;
	}

	@Override
	public String toString() {
		return name;		
	}

}
