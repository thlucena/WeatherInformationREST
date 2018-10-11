import sys
import requests
from urllib.parse import urljoin

class WeatherCentral():
	def __init__(self, city):
		self.city = city
		self._api_url_base = 'http://localhost:8080/WeatherCentralREST/'

	def get_current_weather(self):
		params = {'cityName': self.city}
		api_url = urljoin(self._api_url_base, 'currentInfo')
		weather = WeatherCentral._request_weather(api_url, params)
		return weather

	def get_weather_forecast(self, hours):
		params = {'cityName': self.city, 'hours': hours}
		api_url = urljoin(self._api_url_base, 'futureInfo')
		weather = WeatherCentral._request_weather(api_url, params)
		return weather

	def _request_weather(uri, params):
		try:
			print("Requesting information from server", file=sys.stderr)
			response = requests.get(uri, params=params, timeout=15)
		except requests.ConnectionError as e:
			print("Unable to connect to server due to network "
				"problem", file=sys.stderr)
		except requests.Timeout as e:
			print("Request timed out", file=sys.stderr)
		except requests.TooManyRedirects as e:
			print("Too many redirects", file=sys.stderr)
		else:
			print("Server responded in", response.elapsed, file=sys.stderr)
			if response.status_code == requests.codes.ok:
				weather = response.json()
				return weather
			elif response.status_code == requests.codes.server_error:
				print("[{}] Internal server error for url: {}".format(
					response.status_code, response.url), file=sys.stderr)
			elif response.status_code == requests.codes.not_found:
				print("[{}] URL not found: {}".format(
					response.status_code, response.url), file=sys.stderr)
			else:
				response.raise_for_status()

		return None

	def print_weather(w):
		print("The weather in {}, {} ({}, {})".format(
			w['city'], w['country'], w['cityLat'], w['cityLon']))
		print("In {}, should be as follows:".format(w['dateTime']))
		print("{} - {}".format(w['weatherCondition'], w['weatherDescription']))
		print("Temperatures of about {:.2f}ºC".format(w['temp']['celsius']))
		print("Pressure of {}hPa".format(w['pressure']))
		print("Humidity at {}%".format(w['humidity']))
		print("Wind speeds of {}km/h".format(w['windSpeed']))
		print("And cloudiness of {}%".format(w['cloudiness']))