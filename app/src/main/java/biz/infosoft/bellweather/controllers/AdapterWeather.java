package biz.infosoft.bellweather.controllers;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import biz.infosoft.bellweather.R;

// custom adapter extends the functionality of ArrayAdapter
public class AdapterWeather extends ArrayAdapter<String> {
    private final Activity context;

    //data items
    private final String[] arrDate; // date
    private final String[] arrCondition; // weather condition
    private final Integer[] arrTemp; // temperature

    // max items to display (5 per assignment spec)
    public static final Integer ITEMS_LIMIT =5;

    // custom adapter constructor
    public AdapterWeather(Activity context, String[] Dates, String[] Conditions, Integer[] Temps) {
        super(context, R.layout.list_item_location, Conditions);
        this.context = context;

        this.arrDate = Dates;
        this.arrCondition = Conditions;
        this.arrTemp = Temps;
    }

    @Override
    @NonNull
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View itemView = inflater.inflate(R.layout.list_item_weather, null, true);

        // set date
        ((TextView) itemView.findViewById(R.id.wDates)).setText(arrDate[position] );

        // set weather condition
        ((TextView) itemView.findViewById(R.id.wConditions)).setText(arrCondition[position]);

        // set temperature in Celsius and Fahrenheit
        // F = 1.8C + 32
        int C = arrTemp[position];
        int F = (int)Math.round(1.8*C+32);
        String temp = Integer.toString(C) + (char) 0x00B0 +"C" +
                " (" + Integer.toString(F) + (char) 0x00B0 +"F)";
        ((TextView) itemView.findViewById(R.id.wTemp)).setText(temp);

        // single item View
        return itemView;
    }
}

