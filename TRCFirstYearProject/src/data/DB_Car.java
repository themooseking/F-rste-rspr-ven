package data;

import java.util.ArrayList;
import java.util.Calendar;

import logic.Car;
import java.sql.*;

public class DB_Car {
	private Connection connection;

	public DB_Car(Connection connection) {
		this.connection = connection;
	}
	
	/***********************************
	 * READ CAR
	 ***********************************/

	public ArrayList<Car> getCars() {
		ArrayList<Car> carList = new ArrayList<Car>();

		try {
			String sql = "SELECT * FROM car";

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String model = resultSet.getString("model");
				int price = resultSet.getInt("price");
				int mileage = 0;
				int factoryYear = Calendar.getInstance().get(Calendar.YEAR);
				String carStatus = resultSet.getString("carStatus");
				
				Car car = new Car(id, model, price, mileage, factoryYear, carStatus);
				carList.add(car);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return carList;
	}
	
	/***********************************
	 * READ NEW CAR
	 ***********************************/

	public ArrayList<Car> getNewCars() {
		ArrayList<Car> carList = new ArrayList<Car>();

		try {
			String sql = "SELECT * "
					+ "FROM car "
					+ "WHERE carStatus='NEW' "
					+ "ORDER BY model";

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String model = resultSet.getString("model");
				int price = resultSet.getInt("price");
				int mileage = 0;
				int factoryYear = Calendar.getInstance().get(Calendar.YEAR);
				String carStatus = resultSet.getString("carStatus");
				
				Car car = new Car(id, model, price, mileage, factoryYear, carStatus);
				carList.add(car);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return carList;
	}

	/***********************************
	 * READ USED CAR
	 ***********************************/

	public ArrayList<String> getUsedCarModels() {
		ArrayList<String> modelList = new ArrayList<>();

		try {
			String sql = "SELECT DISTINCT model "
					+ "FROM car "
					+ "WHERE carStatus='AVAILABLE' "
					+ "ORDER BY model";

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				String model = resultSet.getString("model");

				modelList.add(model);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return modelList;
	}
	
	public ArrayList<String> getUsedCarYears(String model) {
		ArrayList<String> yearList = new ArrayList<>();

		try {
			String sql = "SELECT DISTINCT factoryYear "
					+ "FROM car "
					+ "WHERE carStatus='AVAILABLE' "
					+ "AND model=? "
					+ "ORDER BY factoryYear";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, model);

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				String year = Integer.toString(resultSet.getInt("factoryYear"));

				yearList.add(year);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return yearList;
	}
	
	public ArrayList<Car> getUsedCars() {
		ArrayList<Car> carList = new ArrayList<Car>();

		try {
			String sql = "SELECT * "
					+ "FROM car "
					+ "WHERE carStatus='AVAILABLE'";

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String model = resultSet.getString("model");
				int price = resultSet.getInt("price");
				int mileage = resultSet.getInt("mileage");
				int factoryYear = resultSet.getInt("factoryYear");
				String carStatus = resultSet.getString("carStatus");

				Car car = new Car(id, model, price, mileage, factoryYear, carStatus);

				carList.add(car);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return carList;
	}
	
	public ArrayList<Car> getUsedCars(String model) {
		ArrayList<Car> carList = new ArrayList<Car>();

		try {
			String sql = "SELECT * "
					+ "FROM car "
					+ "WHERE carStatus='AVAILABLE' "
					+ "AND model=?";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, model);

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				int price = resultSet.getInt("price");
				int mileage = resultSet.getInt("mileage");
				int factoryYear = resultSet.getInt("factoryYear");
				String carStatus = resultSet.getString("carStatus");

				Car car = new Car(id, model, price, mileage, factoryYear, carStatus);

				carList.add(car);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return carList;
	}
	
	public ArrayList<Car> getUsedCars(String model, String year) {
		ArrayList<Car> carList = new ArrayList<Car>();
		int factoryYear = Integer.parseInt(year);
		
		try {
			String sql = "SELECT * "
					+ "FROM car "
					+ "WHERE carStatus='AVAILABLE' "
					+ "AND model=? "
					+ "AND factoryYear=?";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, model);
			statement.setInt(2, factoryYear);

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				int price = resultSet.getInt("price");
				int mileage = resultSet.getInt("mileage");
				String carStatus = resultSet.getString("carStatus");

				Car car = new Car(id, model, price, mileage, factoryYear, carStatus);

				carList.add(car);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return carList;
	}
}
