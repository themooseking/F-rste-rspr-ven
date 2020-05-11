package logic;

import java.time.LocalDate;
import java.util.ArrayList;
import data.*;

public class DB_Controller {
	private DataLayer dataLayer = new DataLayer();
	private DB_Proposal proposalDB = new DB_Proposal(dataLayer.getConnection());
	private DB_Car carDB = new DB_Car(dataLayer.getConnection());
	private DB_Customer customerDB = new DB_Customer(dataLayer.getConnection());
	private DB_Salesman salesmanDB = new DB_Salesman(dataLayer.getConnection());

	/***********************************
	 * Create
	 ***********************************/

	public void createProposal(Proposal proposal) {
		proposalDB.createProposal(proposal);
	}
	
	public void createInterest(double interest){
		proposalDB.createInterest(interest);
	}

	/***********************************
	 * READ CAR
	 ***********************************/

	public ArrayList<Car> getNewCars() {
		return carDB.getNewCars();
	}
	
	public ArrayList<String> getCarModels() {
		return carDB.getUsedCarModels();
	}
	
	public ArrayList<String> getCarFactoryYears(String model) {
		return carDB.getUsedCarYears(model);
	}
	
	public ArrayList<Car> getUsedCars() {
		return carDB.getUsedCars();
	}
	
	public ArrayList<Car> getUsedCars(String model) {
		return carDB.getUsedCars(model);
	}
	
	public ArrayList<Car> getUsedCars(String model, String year) {
		return carDB.getUsedCars(model, year);
	}
	
	/***********************************
	 * READ CUSTOMER
	 ***********************************/

	public Customer getCustomer(String cpr) {
		return customerDB.getCustomer(cpr);
	}
	
	/***********************************
	 * READ PROPOSAL
	 ***********************************/

	public ArrayList<Proposal> getProposalByCustomer(Customer customer) {
		ArrayList<Car> carList = carDB.getCars();
		ArrayList<Salesman> salesmanList = salesmanDB.getSalesmanList();
		
		ArrayList<Proposal> proposalList = proposalDB.getProposalByCustomer(customer, carList, salesmanList);
		
		for(Proposal prop : proposalList) {
			double interest = getInterest(prop.getDate());
			
			prop.setInterest(interest);
		}
		
		return proposalList;
	}
	
	/***********************************
	 * READ INTEREST
	 ***********************************/

	public double getInterest(LocalDate date) {
		return proposalDB.getInterest(date);
	}
	/***********************************
	 * READ SALESMAN
	 ***********************************/
	
	public ArrayList<Salesman> getSalesmanList() {
		return salesmanDB.getSalesmanList();
	}
	
	/***********************************
	 * UPDATE
	 ***********************************/

	public void updateProposalStatus(Proposal proposal) {
		updateProposalStatus(proposal);
	}
}
