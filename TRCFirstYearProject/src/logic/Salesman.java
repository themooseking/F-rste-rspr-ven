package logic;

import java.math.BigDecimal;

public class Salesman {
	
	private int salesmanId;
	private int phone;
	private String salesmanName;
	private String email;
	private String title;
	private BigDecimal proposalLimit;
	
	public Salesman(int salesmanId, int phone, String salesmanName, String email, String title, BigDecimal proposalLimit) {
		this.salesmanId = salesmanId;
		this.phone = phone;
		this.salesmanName = salesmanName;
		this.email = email;
		this.title = title;
		this.proposalLimit = proposalLimit;
	}

	public int getSalesmanId() {
		return salesmanId;
	}
	
	public String getTitle() {
		return title;
	}

	public BigDecimal getProposalLimit() {
		return proposalLimit;
	}

	public void setProposalLimit(BigDecimal proposalLimit) {
		this.proposalLimit = proposalLimit;
	}

	@Override
	public String toString() {
		return salesmanName;		
	}
}
