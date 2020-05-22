package data;

import logic.Salesman;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class DB_Salesman {
	private Connection connection;

	public DB_Salesman(Connection connection) {
		this.connection = connection;
	}

	//////////////////////////////
	// READ
	//////////////////////////////

	public ArrayList<Salesman> getSalesmanList() {
		ArrayList<Salesman> salesmanList = new ArrayList<>();
		
		try {
			String sql = "SELECT * "
					+ "FROM salesman "
					+ "JOIN salesmanrank "
					+ "ON salesmanrank.title = salesman.title "
					+ "ORDER BY salesmanName";
			
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				int phone = resultSet.getInt("phone");
				String name = resultSet.getString("salesmanName");
				String email = resultSet.getString("email");
				String title = resultSet.getString("title");
				BigDecimal proposalLimit = resultSet.getBigDecimal("proposalLimit");

				Salesman salesman = new Salesman(id, phone, name, email, title, proposalLimit);
				salesmanList.add(salesman);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return salesmanList;
	}
	
	public Salesman getSalesman(int id, String password) {
		Salesman salesman = null;
		
		try {
			String sql = "SELECT * "
					+ "FROM salesman "
					+ "JOIN salesmanrank "
					+ "ON salesmanrank.title = salesman.title "
					+ "WHERE id=? "
					+ "AND sPassword=?";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			statement.setString(2, password);
			
			ResultSet resultSet = statement.executeQuery();
			
			if(resultSet.next()) {
				int phone = resultSet.getInt("phone");
				String name = resultSet.getString("salesmanName");
				String email = resultSet.getString("email");
				String title = resultSet.getString("title");
				BigDecimal proposalLimit = resultSet.getBigDecimal("proposalLimit");

				salesman = new Salesman(id, phone, name, email, title, proposalLimit);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return salesman;
	}
}
