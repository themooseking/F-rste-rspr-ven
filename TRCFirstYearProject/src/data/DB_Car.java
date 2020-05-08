package data;

import java.util.ArrayList;
import logic.Car;
import java.sql.*;

public class DB_Car {
	private Connection connection;

	public DB_Car(Connection connection) {
		this.connection = connection;
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
				int factoryYear = 2020;
				String carStatus = resultSet.getString("carStatus");
				
				Car car = new Car(model, price, mileage, factoryYear, carStatus);
				carList.add(car);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return carList;
	}
	
//	public Car getNewCar(String carModel, int carYear) {
//		Car car = null;
//		
//		try {
//			String sql = "SELECT * "
//					+ "FROM car "
//					+ "WHERE carStatus='AVAILABLE' "
//					+ "AND mileage=0 "
//					+ "AND model=?"
//					+ "AND factoryYear=?";
//			
//			PreparedStatement statement = connection.prepareStatement(sql);
//			statement.setString(1, carModel);
//			statement.setInt(2, carYear);
//
//			ResultSet resultSet = statement.executeQuery();
//
//			if (resultSet.next()) {
//				String model = resultSet.getString("model");
//				int price = resultSet.getInt("price");
//				int mileage = resultSet.getInt("mileage");
//				int factoryYear = resultSet.getInt("factoryYear");
//				String carStatus = resultSet.getString("carStatus");
//
//				car = new Car(model, price, mileage, factoryYear, carStatus);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return car;
//	}

	/***********************************
	 * READ USED CAR
	 ***********************************/

	public ArrayList<Car> getAvailableUsedCars() {
		ArrayList<Car> carList = new ArrayList<Car>();

		try {
			String sql = "SELECT * "
					+ "FROM car "
					+ "WHERE carStatus='AVAILABLE' "
					+ "AND mileage!=0 "
					+ "ORDER BY model";

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				String model = resultSet.getString("model");
				int price = resultSet.getInt("price");
				int mileage = resultSet.getInt("mileage");
				int factoryYear = resultSet.getInt("factoryYear");
				String carStatus = resultSet.getString("carStatus");

				Car car = new Car(model, price, mileage, factoryYear, carStatus);

				carList.add(car);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return carList;
	}

	public ArrayList<String> getUsedCarModels() {
		ArrayList<String> modelList = new ArrayList<>();

		try {
			String sql = "SELECT DISTINCT model "
					+ "FROM car "
					+ "WHERE carStatus='AVAILABLE' "
					+ "AND mileage!=0 "
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
					+ "AND mileage!=0 "
					+ "AND model=?"
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
}
