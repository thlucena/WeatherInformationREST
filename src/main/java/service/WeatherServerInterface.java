package service;

import net.aksingh.owmjapis.api.APIException;

public interface WeatherServerInterface {
	
	public String getCurrentWeatherInfo(String cityName) throws APIException;
    
    public String getFutureWeatherInfo(String cityName, int numHours) throws APIException;
}
