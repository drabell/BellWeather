package biz.infosoft.bellweather.controllers;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import biz.infosoft.bellweather.R;

// custom adapter extends the functionality of ArrayAdapter
public class AdapterLocation extends ArrayAdapter<String> {
    private final Activity context;

    // data items
    private final String[] arrTitle;
    private final Integer[] arrImgId;
    private final Integer[] arrWoeid;

    // max items to display
    public static final Integer ITEMS_LIMIT =10;

    // custom adapter constructor
    public AdapterLocation(Activity context, String[] Titles, Integer[] Images, Integer[] Woeids) {
        super(context, R.layout.list_item_location, Titles);
        this.context = context;

        this.arrTitle = Titles;
        this.arrImgId = Images;
        this.arrWoeid = Woeids;
    }
    @Override
    @NonNull
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View itemView= inflater.inflate(R.layout.list_item_location, null, true);

        // set item text
        ((TextView) itemView.findViewById(R.id.txt)).setText(arrTitle[position]);

        // set item text
        ((TextView) itemView.findViewById(R.id.txt2)).setText(arrWoeid[position].toString());

        // set item image
        ((ImageView) itemView.findViewById(R.id.img)).setImageResource(arrImgId[position]);

        // single item View
        return itemView;
    }
}


