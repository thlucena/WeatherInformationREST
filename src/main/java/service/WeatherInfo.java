package service;

import java.util.Date;

import net.aksingh.owmjapis.model.CurrentWeather;
import net.aksingh.owmjapis.model.HourlyWeatherForecast;
import net.aksingh.owmjapis.model.param.City;
import net.aksingh.owmjapis.model.param.Cloud;
import net.aksingh.owmjapis.model.param.Main;
import net.aksingh.owmjapis.model.param.Weather;
import net.aksingh.owmjapis.model.param.WeatherData;
import net.aksingh.owmjapis.model.param.Wind;

public class WeatherInfo {
	public WeatherInfo(CurrentWeather data) {
		this.initializeWeather(data.getMainData(), data.getWeatherList().get(0),
				data.getWindData(), data.getCloudData());
		
		
		this.city =  data.getCityName();
		this.country = data.getSystemData().getCountryCode();
		this.dateTime = data.getDateTime();
		this.cityLat = data.getCoordData().getLatitude();
		this.cityLon = data.getCoordData().getLongitude();
	}
	
	public WeatherInfo(HourlyWeatherForecast data, int idx) {
		WeatherData wd = data.getDataList().get(idx);
		this.initializeWeather(wd.getMainData(), wd.getWeatherList().get(0),
				wd.getWindData(), wd.getCloudData());
		
		City cityData = data.getCityData();
		this.city = cityData.getName();
		this.country = cityData.getCountryCode();
		this.dateTime = wd.getDateTime();
		this.cityLat = cityData.getCoordData().getLatitude();
		this.cityLon = cityData.getCoordData().getLongitude();
	}
	
	public WeatherInfo() {
	}

	private void initializeWeather(Main mainData, Weather weatherData, 
			Wind windData, Cloud cloudData) {
		this.temp = new Temperature(mainData.getTemp());
		this.tempMax = new Temperature(mainData.getTempMax());
		this.tempMin = new Temperature(mainData.getTempMin());
		this.pressure = mainData.getPressure();
		this.humidity = mainData.getHumidity();
		
		this.weatherCondition = weatherData.getMainInfo();
		this.weatherDescription = weatherData.getDescription();
		
		this.windSpeed = windData.getSpeed();
		this.windDeg = windData.getDegree();
		
		this.cloudiness = cloudData.getCloud();
	}
	
	private String city, country;
	private double cityLat, cityLon;
	private Date dateTime;
	private Temperature temp, tempMin, tempMax;
	private double pressure;
	private double humidity;
	private String weatherCondition;
	private String weatherDescription;
	private double windSpeed, windDeg;
	private double cloudiness;
	
	@Override
	public String toString() {
//		DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols(Locale.getDefault());
//		formatSymbols.setDecimalSeparator('.');
//		DecimalFormat df = new DecimalFormat("#.##", formatSymbols);
//		df.setRoundingMode(RoundingMode.CEILING);
//		
//		String info = "";
//		info += "Weather data from " + city + ", " + country +
//				" (" + cityLat + ", " + cityLon + ") on " + this.dateTime + "\n";
//		info += weatherCondition + " - " + weatherDescription + "\n";
//		info += "Temperature: " + df.format(temp.getCelsius()) +
//				" ºC (" + df.format(tempMin.getCelsius()) + " ºC min / " +
//				df.format(tempMax.getCelsius()) + " ºC max)\n";
//		info += "Pressure: " + pressure + " hPa\n";
//		info += "Humidity: " + humidity + "%\n";
//		info += "Wind: " + windSpeed + " km/h - " + windDeg + "º\n";
//		info += "Cloudiness: " + cloudiness + "%\n\n";
		String info = "";
		info += "Weather data from " + city + ", " + country +
				" (" + cityLat + ", " + cityLon + ") on " + this.dateTime + "\n";
		info += weatherCondition + " - " + weatherDescription + "\n";
		info += "Temperature: " + temp.getCelsius() +
				" ºC (" + tempMin.getCelsius() + " ºC min / " +
				tempMax.getCelsius() + " ºC max)\n";
		info += "Pressure: " + pressure + " hPa\n";
		info += "Humidity: " + humidity + "%\n";
		info += "Wind: " + windSpeed + " km/h - " + windDeg + "º\n";
		info += "Cloudiness: " + cloudiness + "%\n\n";
		return info;
	}
}
