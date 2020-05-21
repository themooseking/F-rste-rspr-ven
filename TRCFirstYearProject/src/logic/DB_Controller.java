package logic;

import java.time.LocalDate;
import java.util.ArrayList;
import data.*;

public class DB_Controller {
	private DataLayer dataLayer = new DataLayer();
	private DB_Car carDB = new DB_Car(dataLayer.getConnection());
	private DB_Customer customerDB = new DB_Customer(dataLayer.getConnection());
	private DB_Proposal proposalDB = new DB_Proposal(dataLayer.getConnection());
	private DB_Salesman salesmanDB = new DB_Salesman(dataLayer.getConnection());

	/***********************************
	 * CAR
	 ***********************************/
	// CREATE CAR //
	public void createCar(Car car) {
		carDB.createCar(car);
	}

	// READ CAR //
	public ArrayList<Car> getNewCars() {
		return carDB.getNewCars();
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

	public ArrayList<String> getCarModels() {
		return carDB.getUsedCarModels();
	}

	public ArrayList<String> getCarFactoryYears(String model) {
		return carDB.getUsedCarYears(model);
	}

	// UPDATE CAR //
	public void updateCarStatus(Car car) {
		carDB.updateCarStatus(car);
	}

	/***********************************
	 * CUSTOMER
	 ***********************************/

	public Customer getCustomer(String cpr) {
		return customerDB.getCustomer(cpr);
	}

	/***********************************
	 * INTEREST
	 ***********************************/

	public void createInterest(double interest) {
		proposalDB.createInterest(interest);
	}

	public double getInterest(LocalDate date) {
		return proposalDB.getInterest(date);
	}

	/***********************************
	 * PROPOSAL
	 ***********************************/
	// CREATE PROPOSAL //
	public void createProposal(Proposal proposal) {
		proposalDB.createProposal(proposal);
	}

	// READ PROPOSAL //
	public ArrayList<Proposal> getProposalByCustomer(Customer customer) {
		ArrayList<Car> carList = carDB.getCars();
		ArrayList<Salesman> salesmanList = salesmanDB.getSalesmanList();

		ArrayList<Proposal> proposalList = proposalDB.getProposalByCustomer(customer, carList, salesmanList);

		for (Proposal prop : proposalList) {
			double interest = getInterest(prop.getProposalDate());

			prop.setInterest(interest);
		}

		return proposalList;
	}

	public ArrayList<Proposal> getProposalBySalesman(Salesman salesman) {
		ArrayList<Car> carList = carDB.getCars();
		ArrayList<Customer> customerList = customerDB.getCustomerList();

		ArrayList<Proposal> proposalList = proposalDB.getProposalBySalesman(salesman, carList, customerList);

		for (Proposal prop : proposalList) {
			double interest = getInterest(prop.getProposalDate());

			prop.setInterest(interest);
		}

		return proposalList;
	}

	public ArrayList<Proposal> getAwaitingProposals() {
		ArrayList<Salesman> salesmanList = salesmanDB.getSalesmanList();
		ArrayList<Car> carList = carDB.getCars();
		ArrayList<Customer> customerList = customerDB.getCustomerList();

		ArrayList<Proposal> proposalList = proposalDB.getAwaitingProposals(salesmanList, carList, customerList);

		for (Proposal prop : proposalList) {
			double interest = getInterest(prop.getProposalDate());

			prop.setInterest(interest);
		}

		return proposalList;
	}

	// UPDATE PROPOSAL //
	public void updateProposalStatus(Proposal proposal) {
		proposalDB.updateProposalStatus(proposal);
	}

	// DELETE PROPOSAL //
	public void deleteProposal(Proposal proposal) {
		proposalDB.deleteProposal(proposal);
	}

	/***********************************
	 * STATUS
	 ***********************************/

	public int getNumAwaiting() {
		return proposalDB.getNumAwaiting();
	}

	public int getNumOngoing(Salesman salesman) {
		return proposalDB.getNumOngoing(salesman);
	}

	/***********************************
	 * SALESMAN
	 ***********************************/

	public ArrayList<Salesman> getSalesmanList() {
		return salesmanDB.getSalesmanList();
	}

	public Salesman getSalesman(int id, String password) {
		return salesmanDB.getSalesman(id, password);
	}
}
