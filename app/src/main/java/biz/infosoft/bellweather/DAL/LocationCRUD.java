package biz.infosoft.bellweather.DAL;

import java.util.Date;
import java.util.Map;

// to store location searches
public class LocationCRUD {

    LocationCRUD(Storage PersistOn){ persistOn = PersistOn;}
    private Storage persistOn;

    public static void addLocation(Date TimeStamp, int Woeid){};
    public static void deleteLocation(int Woeid){};
    public Map<Date, Integer> getLocation(int Woeid){
        return null; // TBD
    };

    // persistence layer: either local db (SQLite) or file,, or Firebase
    enum Storage {LocalFile, SQLite, Firebase };
}
