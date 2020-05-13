package data;

import logic.Car;
import logic.Customer;
import logic.Proposal;
import logic.Salesman;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class DB_Proposal {
	private Connection connection;

	public DB_Proposal(Connection connection) {
		this.connection = connection;
	}

	/***********************************
	 * CREATE
	 ***********************************/

	public void createProposal(Proposal proposal) {
		try {
			String sql = "INSERT INTO proposal VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setInt(1, proposal.getCar().getId());
			statement.setInt(2, proposal.getCustomer().getCustomerId());
			statement.setInt(3, proposal.getDownPayment());
			statement.setInt(4, proposal.getLoanDuration());
			statement.setDate(5, Date.valueOf(proposal.getDate()));
			statement.setString(6, proposal.getProposalStatus());
			statement.setString(7, proposal.getCustomer().getCreditScore().name());
			statement.setInt(8, proposal.getSalesman().getSalesmanId());
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void createInterest(double interest) {
		try {
			String sql = "INSERT INTO bankinterest VALUES (?, ?)";

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setDate(1, Date.valueOf(LocalDate.now()));
			statement.setFloat(2, (float) interest);
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/***********************************
	 * READ PROPOSAL
	 ***********************************/

	public ArrayList<Proposal> getProposalByCustomer(Customer customer, ArrayList<Car> carList,
			ArrayList<Salesman> salesmanList) {
		ArrayList<Proposal> proposalList = new ArrayList<>();

		try {
			String sql = "SELECT * " 
					+ "FROM proposal " 
					+ "WHERE customer=?";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, customer.getCustomerId());

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int proposalId = resultSet.getInt("id");
				
				Car car = null;
				for (int i = 0; i < carList.size(); i++) {
					if (carList.get(i).getId() == resultSet.getInt("car")) {
						car = carList.get(i);
						break;
					}
				}
				
				int downPayment = resultSet.getInt("downPayment");
				int loanDuration = resultSet.getInt("loanDuration");
				LocalDate proposalDate = resultSet.getDate("proposalDate").toLocalDate();
				String proposalStatus = resultSet.getString("proposalStatus");
				String creditScore = resultSet.getString("creditScore");

				Salesman salesman = null;
				for (int i = 0; i < salesmanList.size(); i++) {
					if (salesmanList.get(i).getSalesmanId() == resultSet.getInt("salesman")) {
						salesman = salesmanList.get(i);
						break;
					}
				}
				
				Proposal proposal = new Proposal(proposalId, car, customer, downPayment, loanDuration,
						proposalDate, proposalStatus, creditScore, salesman);

				proposalList.add(proposal);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return proposalList;
	}
	
	public ArrayList<Proposal> getProposalBySalesman(Salesman salesman, ArrayList<Car> carList,
			ArrayList<Customer> customerList) {
		ArrayList<Proposal> proposalList = new ArrayList<>();

		try {
			String sql = "SELECT * " 
					+ "FROM proposal " 
					+ "WHERE salesman=?";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, salesman.getSalesmanId());

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int proposalId = resultSet.getInt("id");
				
				Car car = null;
				for (int i = 0; i < carList.size(); i++) {
					if (carList.get(i).getId() == resultSet.getInt("car")) {
						car = carList.get(i);
						break;
					}
				}
				
				Customer customer = null;
				for (int i = 0; i < customerList.size(); i++) {
					if (customerList.get(i).getCustomerId() == resultSet.getInt("customer")) {
						customer = customerList.get(i);
						break;
					}
				}
				
				int downPayment = resultSet.getInt("downPayment");
				int loanDuration = resultSet.getInt("loanDuration");
				LocalDate proposalDate = resultSet.getDate("proposalDate").toLocalDate();
				String proposalStatus = resultSet.getString("proposalStatus");
				String creditScore = resultSet.getString("creditScore");
				
				Proposal proposal = new Proposal(proposalId, car, customer, downPayment, loanDuration,
						proposalDate, proposalStatus, creditScore, salesman);

				proposalList.add(proposal);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return proposalList;
	}
	
	public ArrayList<Proposal> getAwaitingProposals(ArrayList<Salesman> salesmanList, ArrayList<Car> carList,
			ArrayList<Customer> customerList) {
		ArrayList<Proposal> proposalList = new ArrayList<>();

		try {
			String sql = "SELECT * " 
					+ "FROM proposal " 
					+ "WHERE proposalStatus='APPROVED' "
					+ "OR proposalStatus='AWAITING'";

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				int proposalId = resultSet.getInt("id");
				
				Car car = null;
				for (int i = 0; i < carList.size(); i++) {
					if (carList.get(i).getId() == resultSet.getInt("car")) {
						car = carList.get(i);
						break;
					}
				}
				
				Customer customer = null;
				for (int i = 0; i < customerList.size(); i++) {
					if (customerList.get(i).getCustomerId() == resultSet.getInt("customer")) {
						customer = customerList.get(i);
						break;
					}
				}
				
				int downPayment = resultSet.getInt("downPayment");
				int loanDuration = resultSet.getInt("loanDuration");
				LocalDate proposalDate = resultSet.getDate("proposalDate").toLocalDate();
				String proposalStatus = resultSet.getString("proposalStatus");
				String creditScore = resultSet.getString("creditScore");
				
				Salesman salesman = null;
				for (int i = 0; i < salesmanList.size(); i++) {
					if (salesmanList.get(i).getSalesmanId() == resultSet.getInt("salesman")) {
						salesman = salesmanList.get(i);
						break;
					}
				}
				
				Proposal proposal = new Proposal(proposalId, car, customer, downPayment, loanDuration,
						proposalDate, proposalStatus, creditScore, salesman);

				proposalList.add(proposal);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return proposalList;
	}
	
	/***********************************
	 * READ INTEREST
	 ***********************************/

	public double getInterest(LocalDate date) {
		double interest = -1.0;
		
		try {
			String sql = "SELECT interest " 
					+ "FROM bankinterest " 
					+ "WHERE interestDate=?";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setDate(1, Date.valueOf(date));

			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				interest = resultSet.getFloat("interest");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return interest;
	}
	
	/***********************************
	 * UPDATE
	 ***********************************/

	public void updateProposalStatus(Proposal proposal) {
		try {
			String sql = "UPDATE proposal SET proposalStatus=? WHERE id=?";

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, proposal.getProposalStatus());
			statement.setInt(2, proposal.getProposalId());

			if (statement.executeUpdate() == 0)
				System.out.println("No matches to be updated!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
