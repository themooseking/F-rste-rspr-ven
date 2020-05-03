package data;

import logic.Proposal;

import java.sql.*;

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

			statement.setInt(1, proposal.getCustomer().getId());
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

	/***********************************
	 * UPDATE
	 ***********************************/

	public void updateProposalStatus(Proposal proposal) {
		try {
			String sql = "UPDATE proposal SET proposalStatus=? WHERE id=?";

//			Statement statement = connection.createStatement();
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, proposal.getProposalStatus());
			statement.setInt(2, proposal.getId());

			if (statement.executeUpdate() == 0)
				System.out.println("No matches to be updated!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
