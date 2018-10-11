import sys
import json
import requests
from urllib.parse import urljoin
import argparse

def parse_arguments():
	parser = argparse.ArgumentParser(description="WeatherCentralAPI arguments")

	main_group = parser.add_mutually_exclusive_group(required=True)
	main_group.add_argument('--current', '-c', action='store_true',
		help="get current weather in a certain location")
	main_group.add_argument('--forecast', '-f', action='store_true',
		help="get forecast for certain location sometime in the next five days")

	location_group = parser.add_mutually_exclusive_group(required=True)
	location_group.add_argument('--city', help="name of city for which you want "
		"the current weather or forecast")

	parser.add_argument('--hours', help="how many hours from now "
		"you want the forecast to be")

	args = parser.parse_args()

	if args.forecast and not args.hours:
		parser.error("--forecast requires --hours argument")

	return args

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

api_url_base = 'http://localhost:8080/WeatherCentralREST/'
args = parse_arguments()
params = {'cityName': args.city}

if args.current:
	api_url = urljoin(api_url_base, 'currentInfo')
else:
	api_url = urljoin(api_url_base, 'futureInfo')
	params['hours'] = args.hours

try:
	print("Requesting information from server")
	response = requests.get(api_url, params=params, timeout=15)
except requests.ConnectionError as e:
	print("Unable to connect to server due to network "
		"problem", file=sys.stderr)
except requests.Timeout as e:
	print("Request timed out", file=sys.stderr)
except requests.TooManyRedirects as e:
	print("Too many redirects", file=sys.stderr)
else:
	print("Server responded in", response.elapsed)
	if response.status_code == requests.codes.ok:
		weather = response.json()
		print_weather(weather)
	elif response.status_code == requests.codes.server_error:
		print("[{}] Internal server error for url: {}".format(
			response.status_code, response.url))
	elif response.status_code == requests.codes.not_found:
		print("[{}] URL not found: {}".format(
			response.status_code, response.url))
	else:
		response.raise_for_status()
