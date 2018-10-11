import os
import time
from weather_central import WeatherCentral
import argparse

parser = argparse.ArgumentParser(description="Weather Watch arguments")

parser.add_argument('--city', required=True,
	help="name of city for which you want "
	"to watch the weather")

parser.add_argument('--interval', required=True,
	help="interval in seconds to "
	"update weather information", type=int)

args = parser.parse_args()

natal_weather = WeatherCentral(args.city)
interval = args.interval

start_time = time.time()
while True:
	cw = natal_weather.get_current_weather()
	if cw:
		WeatherCentral.print_weather(cw)

	time.sleep(interval - ((time.time() - start_time) % interval))
	os.system('clear')