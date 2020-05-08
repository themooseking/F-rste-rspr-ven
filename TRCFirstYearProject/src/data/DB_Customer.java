package data;

import logic.Customer;
import java.sql.*;

public class DB_Customer {
	private Connection connection;

	public DB_Customer(Connection connection) {
		this.connection = connection;
	}

	/***********************************
	 * READ
	 ***********************************/

	public Customer getCustomer(String cpr) {
		Customer customer = null;
		
		try {
			String sql = "SELECT * "
					+ "FROM customer "
					+ "WHERE cpr=?";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, cpr);

			ResultSet resultSet = statement.executeQuery();

			if(resultSet.next()) {
				int id = resultSet.getInt("id");
				int phone = resultSet.getInt("phone");
				String name = resultSet.getString("customerName");
				String email = resultSet.getString("email");
				String address = resultSet.getString("customerAddress");
				int postalCode = resultSet.getInt("postalCode");

				customer = new Customer(phone, name, cpr, email, address, postalCode);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return customer;
	}
}
