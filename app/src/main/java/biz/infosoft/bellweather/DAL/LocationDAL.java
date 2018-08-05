package biz.infosoft.bellweather.DAL;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import biz.infosoft.bellweather.R;

public class LocationDAL {
    private static final int imgId = R.drawable.airplane;

    // sample Points of Interest (POI) location
    public static final LinkedHashSet<String> arrTitle = new LinkedHashSet<String>()
    {{
        add("New York");
        add("Los Angeles");
        add("Mexico City");
        add("London");
        add("Berlin");
        add("Paris");
        add("Rome");
        add("Moscow");
        add("Amsterdam");
        add("Tokyo");
    }};

    // sample woeid (Where On Earth ID)
    public static final LinkedHashSet<Integer> arrWoeid = new LinkedHashSet<Integer>()
    {{
        add(2459115);
        add(2442047);
        add(116545);
        add(44418);
        add(638242);
        add(615702);
        add(721943);
        add(2122265);
        add(727232);
        add(1118370);
    }};

    // mock POI image
    public static final List<Integer> arrImgLocation = new ArrayList<Integer>()
    {{
        add(imgId); add(imgId); add(imgId); add(imgId);add(imgId);
        add(imgId); add(imgId); add(imgId); add(imgId); add(imgId);
    }};
}
