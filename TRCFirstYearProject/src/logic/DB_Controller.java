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
		return carDB.getAvailableNewCars();
	}
	
	public ArrayList<String> getNewCarModels() {
		return carDB.getNewCarModels();
	}
	
	public ArrayList<Car> getUsedCars() {
		return carDB.getAvailableNewCars();
	}
	
	public ArrayList<String> getUsedCarModels() {
		return carDB.getNewCarModels();
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
