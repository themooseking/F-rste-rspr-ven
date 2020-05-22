package data;

import logic.Customer;
import java.sql.*;
import java.util.ArrayList;

public class DB_Customer {
	private Connection connection;

	public DB_Customer(Connection connection) {
		this.connection = connection;
	}

	//////////////////////////////
	// READ
	//////////////////////////////

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

				customer = new Customer(id, phone, name, cpr, email, address, postalCode);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return customer;
	}
	
	public ArrayList<Customer> getCustomerList() {
		ArrayList<Customer> customerList =  new ArrayList<Customer>();
		
		try {
			String sql = "SELECT * FROM customer";
			
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				int phone = resultSet.getInt("phone");
				String name = resultSet.getString("customerName");
				String cpr = resultSet.getString("cpr");
				String email = resultSet.getString("email");
				String address = resultSet.getString("customerAddress");
				int postalCode = resultSet.getInt("postalCode");

				Customer customer = new Customer(id, phone, name, cpr, email, address, postalCode);
				
				customerList.add(customer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return customerList;
	}
}
