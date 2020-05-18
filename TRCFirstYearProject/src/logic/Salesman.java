package logic;

public class Salesman {
	
	private int salesmanId;
	private int phone;
	private String salesmanName;
	private String email;
	private String title;
	private int proposalLimit;
	
	public Salesman(int salesmanId, int phone, String salesmanName, String email, String title, int proposalLimit) {
		this.salesmanId = salesmanId;
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

	public int getSalesmanId() {
		return salesmanId;
	}
	
	public String getTitle() {
		return title;
	}

	public int getProposalLimit() {
		return proposalLimit;
	}

	public void setProposalLimit(int proposalLimit) {
		this.proposalLimit = proposalLimit;
	}
}
