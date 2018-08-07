# BellWeather V.1.0.2
Release date: 08/06/2018

OVERVIEW
Android app BellWeather developed by Dr. Alexander Bell (NY, USA) 
enables Location Search and 6-Days Weather Forecast. 
The app consumes RESTful WebAPI aggregated data provided by https://www.metaweather.com
utilizing Android Volley JSON network library.

LOCATION SEARCH QUERIES SAMPLES
The end points used for Location search are shown in the follwoing sample web query. 
By Location name, e.g. 'New York'

https://www.metaweather.com/api/location/search/?query=NEW+YORK
Corresponding JSON object array:

[{"title":"New York","location_type":"City","woeid":2459115,"latt_long":"40.71455,-74.007118"}]

By Location coordinates (sample Latitude/Longitude corresponding to the New York City):

https://www.metaweather.com/api/location/search/?lattlong=40.712772,-74.006058

The first object in JSON array shows New York as the closest one:

[{"distance":216,"title":"New York","location_type":"City","woeid":2459115,"latt_long":"40.71455,-74.007118"}]

The Location search result retursn unique identifier WOEID (Where On Earth ID), which is
used to obtain JSON Objects array corresponding to 6-days forecast.

6-DAYS FORECAST DATA ITEMS

-Location Title

-Date

-Weekday

-Temperature (C or F scale)

-Min Temperature (C or F scale)

-Max Temperature (C or F scale)

-Relative Humidity

-Weather Condition (Rain, Cloud, etc)

-Wind speed (either in m/s or mph)

SAMLPES
For example, London corresponds to woeid = 44418. 
Corresponding 5-days weather forecast can be
in JSON format obtained using REST request:
https://www.metaweather.com/api/location/44418

Another example obtaing weather forecast for New York City:
https://www.metaweather.com/api/location/2459115

USAGE
Click on Location list to open another window displaying 6-days weather forecast
(note: originally it was limited to 5-days per special project requirements)
The button in the upper-right corner toggles the reading between Celsius/Fahrenheit scales.
Temperature reading collor code: red for t>=30C and blue for t<=30.

To add location click on the floating action button at the bottom ob the main screen
to open the search box. Enter search query and click on search button. 
Click on the item in results list to add it to permanent storage.
Use 'long-click" to remove existing list item 
Refer to the sample screenshot highlighting aforementioned use cases.

LOCATIONS DATA PERSISTENCE LAYER (DAO)
The list of Locations can be dynamically modified by adding new search items
or  removing the existing ones. The list is stored locally using custom DAO persistence objects.

BUG REPORT
Error discovered regarding the wind speed readings in the New York City on 08/06/2018: 
the site data shows wind speed as -246mph (?!). 
The same type of error observed in this app: wind speed shown as -553 mph 
(see the screenshot WindSpeedError).
Status: TBD

Author's contact info: bell@alexanderbell.us
