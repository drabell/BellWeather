/*
Project     : BellWeather 5-days Forecast (Demo Project)
Author      : Alexander Bell (NY, USA)
Description : Android app allows Geo-Location Search and generating 5 days weather forecast
minSDK = 21 (Lollipop)
targetSDK = 27 (Oreo)
--------------------------------------------------------------------------------------------
TODO for next release V1.02
1. Add Data Persistence Layer (tentatively SQLite)
2. Replace ListView with Recycler View and add corresponding adapter
3. Optimize State Management (onPause/onResume)
4. Optimize Landscape view (or restrict to portrait only)
5. Add search by Coordinates manually entered (note:for privacy/security reason GPS
   device tracking is not implemented
6. Add Test Cases
*/

package biz.infosoft.bellweather;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

// this app utilizes VOLLEY library for async HTTP request/response
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

// app specific library
import biz.infosoft.bellweather.DAL.LocationDAL;
import biz.infosoft.bellweather.controllers.AdapterLocation;
import biz.infosoft.bellweather.controllers.SingletonNetworkLoader;
import biz.infosoft.bellweather.models.Location;
import biz.infosoft.bellweather.util.SessionSettings;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            final Button btn = findViewById(R.id.btn);
            final EditText txtSearch = findViewById(R.id.txtSearch);

            final ListView lv = (ListView) findViewById(R.id.itemListView);
            AdapterLocation adapter = new AdapterLocation(MainActivity.this,
                    LocationDAL.arrTitle.toArray(new String[LocationDAL.arrTitle.size()]),
                    LocationDAL.arrImgLocation.toArray(new Integer[LocationDAL.arrImgLocation.size()]),
                    LocationDAL.arrWoeid.toArray(new Integer[LocationDAL.arrWoeid.size()]));
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Title
                    String title = (String) (lv.getItemAtPosition(position));
                    // woeid
                    Integer woeId = Integer.parseInt(((TextView) view.findViewById(R.id.txt2)).getText().toString());
                    if (LocationDAL.arrWoeid.contains(woeId)) {
                        Intent myIntent = new Intent(MainActivity.this, WeatherActivity.class);
                        myIntent.putExtra("woeid", woeId);
                        MainActivity.this.startActivity(myIntent);
                    } else {
                        LocationDAL.arrWoeid.add(woeId);
                        LocationDAL.arrTitle.add(title);
                        LocationDAL.arrImgLocation.add(R.drawable.airplane);
                        refreshLocation();
                        // check if exist and if not add to Location mock data
                        Toast.makeText(MainActivity.this, title + " is added to list",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String query = txtSearch.getText().toString();
                    if (query.isEmpty()) return;

                    final String url = Location.BASE_URL + Location.SEARCH_POI + txtSearch.getText().toString();
                    final ListView lv = findViewById(R.id.itemListView);

                    JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                int poiCount = response.length();
                                if (poiCount <= 0) {
                                    Toast.makeText(MainActivity.this, R.string.search_empty,
                                            Toast.LENGTH_SHORT).show();
                                    lv.setAdapter(null);
                                    return;
                                }
                                if (poiCount > AdapterLocation.ITEMS_LIMIT) {
                                    Toast.makeText(MainActivity.this, R.string.search_too_many,
                                            Toast.LENGTH_SHORT).show();
                                    poiCount = Math.min(AdapterLocation.ITEMS_LIMIT, poiCount);
                                }
                                String[] arrTitle = new String[poiCount];
                                Integer[] arrWoeid = new Integer[poiCount];
                                Integer[] arrImgLocation = new Integer[poiCount];
                                String poi = "";
                                for (int i = 0; i < poiCount; i++) {
                                    arrTitle[i] = response.getJSONObject(i).getString("title");
                                    arrWoeid[i] = response.getJSONObject(i).getInt("woeid");
                                    arrImgLocation[i] = R.drawable.airplane;
                                }
                                AdapterLocation adapter = new AdapterLocation(MainActivity.this,
                                        arrTitle, arrImgLocation, arrWoeid);
                                lv.setAdapter(adapter);
                            } catch (JSONException jex) {
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this, R.string.err, Toast.LENGTH_SHORT).show();
                        }
                    });
                    // set timeout
                    jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                            SessionSettings.TIMEOUT_MS,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                    // Access the RequestQueue through Singleton network loader class
                    SingletonNetworkLoader.getInstance(MainActivity.this).addToRequestQueue(jsonObjectRequest);
                }
            });

            // floating action button to turn search functionality ON/OFF
            final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // toggle search ON/OFF
                    SessionSettings.allowSearch = !SessionSettings.allowSearch;
                    setMode(SessionSettings.allowSearch);
                    // display search mode notice
                    final int notice = SessionSettings.allowSearch ?
                            R.string.app_search_allow : R.string.app_search_close;
                    Snackbar.make(view, notice, Snackbar.LENGTH_LONG)
                            .setAction(R.string.app_mode, null).show();
                }
            });

            btn.setFocusableInTouchMode(SessionSettings.allowSearch);
            fab.requestFocus();
        }
        catch( Exception ex)
        {
            // error notification
            Toast.makeText(MainActivity.this, R.string.err, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // selected menu item
        switch (item.getItemId()) {
            case R.id.mnuHelp:
                // create TextView and add hyperlink to URL
                final TextView tvURL = new TextView(this);
                tvURL.setText(getResources().getString(R.string.author_url));
                tvURL.setAutoLinkMask(Linkify.WEB_URLS);
                tvURL.setMovementMethod(LinkMovementMethod.getInstance());
                tvURL.setGravity(Gravity.CENTER_HORIZONTAL);
                tvURL.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                tvURL.setLinkTextColor(getResources().getColor(R.color.colorUrlLight));
                // create and show AlertDialog
                (new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_NoActionBar_MinWidth))
                    .setCancelable(true)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setTitle(getResources().getText(R.string.info_help_title))
                    .setMessage(getResources().getText(R.string.info_help))
                    .setView(tvURL)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
                return true;
            case R.id.mnuAbout:
                // create TextView and add hyperlink to URL
                final TextView tvAbout = new TextView(this);
                tvAbout.setText(getResources().getString(R.string.company_url));
                tvAbout.setAutoLinkMask(Linkify.WEB_URLS);
                tvAbout.setMovementMethod(LinkMovementMethod.getInstance());
                tvAbout.setGravity(Gravity.CENTER_HORIZONTAL);
                tvAbout.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                tvAbout.setLinkTextColor(getResources().getColor(R.color.colorUrlLight));
                // create and show AlertDialog
                (new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_NoActionBar_MinWidth))
                    .setCancelable(true)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setTitle(getResources().getText(R.string.info_about_title))
                    .setMessage(getResources().getText(R.string.info_about))
                    .setView(tvAbout)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    // set floating action button icon corresponding to search ON/OFF
    private void setMode(boolean state) {
        int fabId = state ? R.drawable.ic_menu_slideshow : R.drawable.airplane;
        ((FloatingActionButton) findViewById(R.id.fab)).setImageResource(fabId);
        if(state) ((LinearLayout) findViewById(R.id.layout_search)).setVisibility((View.VISIBLE));
        else ((LinearLayout) findViewById(R.id.layout_search)).setVisibility((View.GONE));
    }

    // force location list to refresh
    private void refreshLocation()
    {
        final ListView lv=(ListView)findViewById(R.id.itemListView);
        AdapterLocation adapter = new AdapterLocation(MainActivity.this,
                LocationDAL.arrTitle.toArray(new String[LocationDAL.arrTitle.size()]),
                LocationDAL.arrImgLocation.toArray(new Integer[LocationDAL.arrImgLocation.size()]),
                LocationDAL.arrWoeid.toArray(new Integer[LocationDAL.arrWoeid.size()]));
                lv.setAdapter(adapter);
    }

    ///////////////////////////////////////////////////////////////////////////////
    @Override
    public void onPause(){
        super.onPause();
        // TBD in next release...
    }
    @Override
    public void onResume(){
        super.onResume();
        // TBD in next release...
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        // TBD in next release...
    }
}
