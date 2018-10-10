package service;

public class Temperature {
Double kelvin;
	
	Temperature() {
		this.kelvin = null;
	}
	
	Temperature(double k) {
		if (k < 0) {
			throw new IllegalArgumentException(
					"Kelvin temperatures not valid below zero");
		}
		
		this.kelvin = k;
	}
	
	double getCelsius() {
		return Temperature.kelvinToCelsius(this.kelvin);
	}
	
	double getKelvin() {
		return this.kelvin;
	}
	
	double getFahrenheit() {
		return Temperature.kelvinToFahrenheit(this.kelvin);
	}
	
	
	static double kelvinToCelsius(double k) {
		return k - 273.15;
	}
	
	static double kelvinToFahrenheit(double k) {
		return k * 9d/5d - 459.67d;
	}
}
