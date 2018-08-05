package biz.infosoft.bellweather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import biz.infosoft.bellweather.controllers.AdapterWeather;
import biz.infosoft.bellweather.controllers.SingletonNetworkLoader;
import biz.infosoft.bellweather.models.Location;
import biz.infosoft.bellweather.util.SessionSettings;

public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        try {
            Intent intent = getIntent();
            String woeid = intent.getExtras().get("woeid").toString();
            final ListView lv = (ListView) findViewById(R.id.weatherListView);

            final String url = Location.BASE_URL + woeid;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray weatherJSON = response.getJSONArray("consolidated_weather");
                                final String title = response.getString("title");
                                ((TextView) findViewById(R.id.poi)).setText(title);

                                // validate response object
                                if (weatherJSON == null && weatherJSON.length() == 0) return;

                                // limit list items as per spec
                                int count = weatherJSON.length();
                                count = Math.min(count, AdapterWeather.ITEMS_LIMIT);

                                // populate data fields
                                String[] arrDate = new String[count];
                                String[] arrCond = new String[count];
                                Integer[] arrTemp = new Integer[count];
                                for (int i = 0; i < count; i++) {
                                    arrDate[i] = weatherJSON.getJSONObject(i).getString("applicable_date");
                                    arrCond[i] = weatherJSON.getJSONObject(i).getString("weather_state_name");
                                    Integer temp = Math.round(weatherJSON.getJSONObject(i).getInt("the_temp"));
                                    arrTemp[i] = temp;
                                }
                                AdapterWeather adapter = new AdapterWeather(WeatherActivity.this, arrDate, arrCond, arrTemp);
                                lv.setAdapter(adapter);

                                // updated notice
                                Toast.makeText(WeatherActivity.this, R.string.action_upd, Toast.LENGTH_SHORT).show();
                            } catch (JSONException jex) {
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(WeatherActivity.this, R.string.err, Toast.LENGTH_SHORT).show();
                        }
                    });
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    SessionSettings.TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            // Access the RequestQueue through singleton class
            SingletonNetworkLoader.getInstance(WeatherActivity.this).addToRequestQueue(jsonObjectRequest);
        }
        catch(NullPointerException nex)
        {
            // error notification
            Toast.makeText(WeatherActivity.this, R.string.err, Toast.LENGTH_SHORT).show();
        }
    }
}
