package service;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.core.OWM;
import net.aksingh.owmjapis.model.CurrentWeather;
import net.aksingh.owmjapis.model.HourlyWeatherForecast;

@Stateless
@Path("/")
public class WeatherServerResource implements WeatherServerInterface {
	
	final private static String apiKey = "785d7d7d943ffc783b0e903610246440";
	private static OWM openWeatherMap;
	
	public WeatherServerResource() {
		if (openWeatherMap == null) {
			openWeatherMap = new OWM(apiKey);
			System.out.println("Weather resource object constructed!");
		}
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String serverHome() {
		return "Server running!";
	}
	
	@GET
	@Path("/currentInfo")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCurrentWeatherInfo(@QueryParam("cityName") String cityName) throws APIException {
		System.out.println("getCurrentWeatherInfo() called.");
		CurrentWeather cwData = openWeatherMap.currentWeatherByCityName(cityName);
		return new WeatherInfo(cwData).toJson();
	}
	
	@GET
	@Path("/futureInfo")
	@Produces(MediaType.APPLICATION_JSON)
	public String getFutureWeatherInfo(@QueryParam("cityName") String cityName, @QueryParam("hours") int numHours) throws APIException {
		System.out.println("getFutureWeatherInfo() called.");
		if (numHours < 0) {
			throw new IllegalArgumentException();
		}
		
		int threeHourIntervalIndex = numHours / 3;
		if (threeHourIntervalIndex == 0) {
			return getCurrentWeatherInfo(cityName);
		}
		
		threeHourIntervalIndex -= 1; // first 3 hour interval is index 0
		HourlyWeatherForecast hwData = openWeatherMap.hourlyWeatherForecastByCityName(cityName);
		if (threeHourIntervalIndex >= hwData.getDataCount()) {
			throw new IllegalArgumentException("Number of hours exceeds maximum of 5 days forecast");
		}
		return new WeatherInfo(hwData, threeHourIntervalIndex).toJson();
	}

}
