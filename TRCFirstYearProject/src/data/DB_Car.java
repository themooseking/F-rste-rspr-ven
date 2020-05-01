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
	
	public ArrayList<Car> getAvailableNewCars() {
		ArrayList<Car> carList = new ArrayList<Car>();

		try {
			String sql = "SELECT * FROM car WHERE carStatus='AVAILABLE' AND mileage=0 ORDER BY model";
			
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
	
	public ArrayList<String> getNewCarModels() {
		ArrayList<String> modelList = new ArrayList<>();

		try {
			String sql = "SELECT DISTINCT model FROM car WHERE carStatus='AVAILABLE' AND mileage=0 ORDER BY model";
			
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
	
	
	
	/***********************************
	 * READ USED CAR
	 ***********************************/
	
	public ArrayList<Car> getAvailableUsedCars() {
		ArrayList<Car> carList = new ArrayList<Car>();

		try {
			String sql = "SELECT * FROM car WHERE carStatus='AVAILABLE' AND mileage!=0 ORDER BY model";
			
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
			String sql = "SELECT DISTINCT model FROM car WHERE carStatus='AVAILABLE' AND mileage!=0 ORDER BY model";
			
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
}
