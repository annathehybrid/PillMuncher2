package com.anna.pillmuncher;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoxFragment extends Fragment {

    public BoxFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            updateWeatherData(new CityPreference(getActivity()).getCity());

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void updateWeatherData(final String city) {
        new Thread() {
            public void run() {
                final JSONObject json = RemoteFetch.getJSON(getActivity(), city);
            }
        }.start();
    }


    private ArrayAdapter<String> mForecastAdapter;

    TextView cityField;
    TextView updatedField;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_box, container, false);
        //}

        View view = inflater.inflate(R.layout.fragment_main_box, container, false);

        cityField = (TextView) view.findViewById(R.id.city_field);
        updatedField = (TextView) view.findViewById(R.id.updated_field);


        // Create some dummy data for the ListView.  Here's a sample pillbox
        // Array of strings storing country names
        String[] pill_data = new String[]{
                "Red Pill",
                "Orange Pill",
                "Yellow Pill",
                "Green Pill",
                "Blue Pill"
        };


        List<String> weekPills = new ArrayList<String>(Arrays.asList(pill_data));


        mForecastAdapter = new ArrayAdapter<String>(
                getActivity(), //global information about the app enviroment, resources
                R.layout.list_item_pills, //layout for each element
                R.id.list_item_pills_textview, //id of the textview
                weekPills);
        //new ArrayList<String>());

        ListView listView = (ListView) view.findViewById(R.id.listview_pillbox);
        listView.setAdapter(mForecastAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String forecast = mForecastAdapter.getItem(position);
                //Toast.makeText(getActivity(), forecast, Toast.LENGTH_SHORT).show();


                //Intent intent = new Intent(getActivity(), DetailActivity.class)
                //        .putExtra(Intent.EXTRA_TEXT, forecast);

                String message = "http://rxnav.nlm.nih.gov/REST/rxcui.json?idtype=NDC&id=11523-7020-1";
                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, message);
                startActivity(intent);
            }
        });


        return view;
    }


}




