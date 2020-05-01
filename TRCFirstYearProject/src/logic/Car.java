package logic;

public class Car {
	
	private String model;
	private int price;
	private int milage;
	private int factory;
	private String carStatus;
	
	public Car(String model, int price, int milage, int factory, String carStatus) {
		this.model = model;
		this.price = price;
		this.milage = milage;
		this.factory = factory;
		this.carStatus = carStatus;
	}
}
