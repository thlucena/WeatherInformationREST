package service;

public class Temperature {
Double kelvin;
Double celsius;
Double fahrenheit;
	
	Temperature() {
		this.kelvin = null;
		this.celsius = null;
		this.fahrenheit = null;
	}
	
	Temperature(double k) {
		if (k < 0) {
			throw new IllegalArgumentException(
					"Kelvin temperatures not valid below zero");
		}
		
		this.kelvin = k;
		this.celsius = Temperature.kelvinToCelsius(this.kelvin);
		this.fahrenheit = Temperature.kelvinToFahrenheit(this.kelvin);
	}
	
	double getCelsius() {
		return this.celsius;
	}
	
	double getKelvin() {
		return this.kelvin;
	}
	
	double getFahrenheit() {
		return this.fahrenheit;
	}
	
	
	static double kelvinToCelsius(double k) {
		return k - 273.15;
	}
	
	static double kelvinToFahrenheit(double k) {
		return k * 9d/5d - 459.67d;
	}
}
