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
			String sql = "INSERT INTO proposal VALUES (?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setInt(1, proposal.getCustomer().getCustomerId());
			statement.setFloat(2, (float) proposal.getInterest());
			statement.setInt(3, proposal.getDownPayment());
			statement.setInt(4, proposal.getLoanDuration());
			statement.setDate(5, Date.valueOf(proposal.getDate()));
			statement.setString(6, proposal.getProposalStatus());
			statement.setInt(7, proposal.getSalesman().getId());
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/***********************************
	 * READ
	 ***********************************/

	public ArrayList<Proposal> getProposalByCustomer(Customer customer) {
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
				double interest = (double) resultSet.getFloat("interest");
				int downPayment = resultSet.getInt("downPayment");
				int loanDuration = resultSet.getInt("loanDuration");
				LocalDate proposalDate = resultSet.getDate("proposalDate").toLocalDate();
				String proposalStatus = resultSet.getString("proposalStatus");

				Proposal proposal = new Proposal(proposalId, customer, interest, downPayment, loanDuration, 
						proposalDate, proposalStatus, null, null);
				
				proposalList.add(proposal);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return proposalList;
	}
	
	/***********************************
	 * UPDATE
	 ***********************************/

	public void updateProposalStatus(Proposal proposal) {
		try {
			String sql = "UPDATE proposal SET proposalStatus=? WHERE id=?";

//			Statement statement = connection.createStatement();
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
