package logic;

import java.util.ArrayList;
import data.*;

public class DB_Controller {
	private DataLayer dataLayer = new DataLayer();
	private DB_Proposal proposalDB = new DB_Proposal(dataLayer.getConnection());
	private DB_Car carDB = new DB_Car(dataLayer.getConnection());

	/***********************************
	 * Create
	 ***********************************/

	public void createProposal(Proposal proposal) {
		proposalDB.createProposal(proposal);
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
	 * UPDATE
	 ***********************************/

	public void updateProposalStatus(Proposal proposal) {
		updateProposalStatus(proposal);
	}
	
	/***********************************
	 * DELETE
	 ***********************************/

}
