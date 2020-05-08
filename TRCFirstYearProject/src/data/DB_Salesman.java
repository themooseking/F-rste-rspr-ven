package data;

import logic.Salesman;
import java.sql.*;
import java.util.ArrayList;

public class DB_Salesman {
	private Connection connection;

	public DB_Salesman(Connection connection) {
		this.connection = connection;
	}

	/***********************************
	 * READ
	 ***********************************/

	public ArrayList<Salesman> getSalesmanList() {
		ArrayList<Salesman> salesmanList = new ArrayList<>();
		
		try {
			String sql = "SELECT * "
					+ "FROM salesman "
					+ "JOIN rank "
					+ "ON rank.title = salesman.title";
			
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			if(resultSet.next()) {
				int id = resultSet.getInt("id");
				int phone = resultSet.getInt("phone");
				String name = resultSet.getString("salesmanName");
				String email = resultSet.getString("email");
				String title = resultSet.getString("title");
				int proposalLimit = resultSet.getInt("proposalLimit");

				Salesman salesman = new Salesman(phone, name, email, title, proposalLimit);
				salesmanList.add(salesman);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return salesmanList;
	}
}
