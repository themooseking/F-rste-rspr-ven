package logic;

import java.math.BigDecimal;

public class Car {
	
	private int carId;
	private String model;
	private BigDecimal price;
	private int milage;
	private int factory;
	private String carStatus;
	
	public Car(int id, String model, BigDecimal price, int milage, int factory, String carStatus) {
		this.carId = id;
		this.model = model;
		this.price = price;
		this.milage = milage;
		this.factory = factory;
		this.carStatus = carStatus;
	}
	
	public void setCarId(int carId) {
		this.carId = carId;
	}
	
	public void setCarStatus(String carStatus) {
		this.carStatus = carStatus;
	}

	public int getCarId() {
		return carId;
	}

	public BigDecimal getPrice() {
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
	
	public BigDecimal getVat() {
		return getPrice().multiply(new BigDecimal(0.25));
	}	
	
	@Override
	public String toString() {
		return "[" + Integer.toString(carId) + "]	" + model;
	}
}
