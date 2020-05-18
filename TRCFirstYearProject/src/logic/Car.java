package logic;

public class Car {
	
	private int carId;
	private String model;
	private int price;
	private int milage;
	private int factory;
	private String carStatus;
	
	public Car(int id, String model, int price, int milage, int factory, String carStatus) {
		this.carId = id;
		this.model = model;
		this.price = price;
		this.milage = milage;
		this.factory = factory;
		this.carStatus = carStatus;
	}

	public int getId() {
		return carId;
	}

	public int getPrice() {
		return price;
	}

	public String getModel() {
		return model;
	}

	public int getMilage() {
		return milage;
	}

	public int getFactory() {
		return factory;
	}

	public String getCarStatus() {
		return carStatus;
	}	
	
	public double getVat() {
		return getPrice() * 0.25;
	}	
	
	@Override
	public String toString() {
		return "[" + Integer.toString(carId) + "]	" + model;
	}
}
