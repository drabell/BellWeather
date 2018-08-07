# BellWeather V.1.0.2, Release date: 08/06/2018

OVERVIEW
Android app BellWeather developed by Dr. Alexander Bell (NY, USA) 
enables Location Search and 6-Days Weather Forecast. 
The app consumes RESTful WebAPI provided utilizing JSON Volley network library.
The end points used for Location search, either by query or Geo-coordinates:
https://www.metaweather.com

LOCATION SEARCH QUERIES SAMPLES
https://www.metaweather.com/api/location/search/?query=NEW YORK
https://www.metaweather.com/api/location/search/?lattlong=40.712772,-74.006058
 
The search return unique identifier WOEID (Where On Earth ID) 
used to obtain JSON Objects array corresponding to 6-days forecast.

6-DAYS FORECAST DATA ITEMS
Click on Location list to open another window displaying:
-Location Title
-Date
-Temperature (C or F scale)
-Min Temperature (C or F scale)
-Max Temperature (C or F scale)
-Relative Humidity
-Weather Condition (Rain, Cloud, etc)
-Wind speed (either in m/s or mph)
The toglle button allows switching between Celsius/Fahrenheit scales

SAMLPES
For example, London corresponds to woeid = 44418. 
Corresponding 5-days weather forecast can be
in JSON format obtained using REST request:
https://www.metaweather.com/api/location/44418

Another example obtaing weather forecast for New York City:
https://www.metaweather.com/api/location/2459115

LOCATIONS DATA PERSISTENCE LAYER (DAO)
The list of Locations can be dynamically modified by adding new search items
or removing existing ones (long-click - refer to the screenshot). 
The list is stored locally using custom DAO persistence lib.

BUG REPORT
Strange error discovered regarding the wind speed in the NY City for 08/06/2018: 
site data shows wind speed as -246mph (?!). The same value returned by the app.
Status: TBD

Author's contact info: bell@alexanderbell.us
