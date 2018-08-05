package biz.infosoft.bellweather.models;

public class Location {
    // Web API end points/query
    public static final String BASE_URL = "https://www.metaweather.com/api/location/";
    public static final String SEARCH_POI= "search/?query=";
    public static final String SEARCH_LATLON = "search/?lattlong=";

    String index;
    String title;
    String woeid;

    // optional
    String description;
    Double Latitude;
    Double Longitude;

    public Location() {}

    public Location(String Title, String Woeid) {
        this.title = Title;
        this.woeid = Woeid;
    }

    public String getTitle() {
        return title;
    }
    public String getWoeid() {
        return woeid;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setWoeid(String woeid) {
        this.woeid = woeid;
    }
}
