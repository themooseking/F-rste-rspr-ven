package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataLayer {
	private String databaseName;
	private Connection connection; 

	public DataLayer() {
		databaseName = "TRCDB";

		loadJdbcDriver();
		openConnection(databaseName);
	}
	
	private boolean loadJdbcDriver() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			return true;
		} catch (ClassNotFoundException e) {
			System.out.println("Could not load JDBC driver!");
			return false;
		}
	}

	private boolean openConnection(String databaseName) {
		try {
			String connectionString = "jdbc:sqlserver://localhost:1433;" + "instanceName=SQLEXPRESS;" + "databaseName="
					+ databaseName + ";" + "integratedSecurity=true;";

			connection = null;
			connection = DriverManager.getConnection(connectionString);

			return true;
		} catch (SQLException e) {
			System.out.println("Could not connect to database!");
			System.out.println(e.getMessage());

			return false;
		}
	}
	
	/***********************************
	 * GETTERS
	 ***********************************/

	public Connection getConnection() {
		return connection;
	}
}