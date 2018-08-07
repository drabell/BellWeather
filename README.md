# BellWeather V.1.0.2, Release date: 08/06/2018

OVERVIEW
Android app BellWeather developed by Dr. Alexander Bell (NY, USA) 
enables Location Search and 6-Days Weather Forecast. 
The app consumes RESTful WebAPI provided utilizing JSON Volley network library.
The end points used for Location search, either by query or Geo-coordinates:
"https://www.metaweather.com/api/location/";
 "search/?query=";
 "search/?lattlong=";
The search return unique identifier WOEID (Where On Earth ID) 
used to obtain JSON Objects array corresponding to 6-days forecast

LOCATIONS DATA PERSISTENCE LAYER (DAO)
The list of Locations can be dynamically modified by adding new search items
or removing existing ones (long-click - refer to the screenshot). 
The list is stored locally using custom DAO persistence lib.

6-DAYS FORECAST
Click on Location list to open another activity screen (window) displaying:
Location Title
Date
Temperature (C or F scale)
Min Temperature (C or F scale)
Max Temperature (C or F scale)
Relative Humidity
Weather Condition (Rain, Cloud, etc)
Wind speed (either in m/s or mph)
The toglle button allows switching between Celsius/Fahrenheit scales

BUG Report
So far a single error happened while reading the wind speed in the NY City.
The actual JSON objects array looks good; the parsing error is
presumably attributed to the Volley Library JSON processing. 
Status: TBD

Author's contact info: bell@alexanderbell.us
