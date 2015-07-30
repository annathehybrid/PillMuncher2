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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.anna.pillmuncher.Box;
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

    public ArrayAdapter<String> mForecastAdapter;
    ImageButton imgButton;
    TextView TextBox1;
    TextView TextBox2;

    public BoxFragment() {
    }

    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);



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



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_box, container, false);
        //}

        View view = inflater.inflate(R.layout.fragment_main_box, container, false);

        //configureImageButton();
        ImageButton imgButton = (ImageButton) view.findViewById(R.id.imageButton);




        TextBox1 = (TextView) view.findViewById(R.id.textbox_1);
        TextBox2 = (TextView) view.findViewById(R.id.textbox_2);


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



        //Box.MyDownloadTask frame = ;


        mForecastAdapter.add("hi hi");
        mForecastAdapter.add(Box.EXTRA_MESSAGE);
        listView.setAdapter(mForecastAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //String forecast = mForecastAdapter.getItem(position);
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

    private void configureImageButton() {

        ImageButton imgButton = (ImageButton) view.findViewById(R.id.imageButton);

        imgButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "You Clicked the button!", Toast.LENGTH_LONG).show();
                new MyDownloadTask().execute("input_here");
            }
        });


    }


    public class MyDownloadTask extends AsyncTask<String, Void, String> {



        public String send_to_the_adapter = "whooo"; //gah how do I pull this from the background???
        public String see_if_this_works = "hello I'm in the Async task"; //this works

        String parameter = "rxcui.json";
        String units = "NDC";
        String units2 = "11523-7020-1";


        @Override
        public String doInBackground(String... args) {

            //URL url;
            //see_if_this_works = "hello I'm in the Async task";
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String forecastJsonStr = null;

            String args_print = args[0];
            Log.e("This is the input: ", args_print);


            try {
                // Construct the URL
                final String RXNORM_BASE_URL = "http://rxnav.nlm.nih.gov/REST/";
                //URL url = new URL("http://rxnav.nlm.nih.gov/REST/rxcui.json?idtype=NDC&id=11523-7020-1");

                //final String QUERY_PARAM = "mouse";
                final String UNITS_PARAM = "idtype";
                final String UNITS_PARAM2 = "id";

                Uri builtUri = Uri.parse(RXNORM_BASE_URL).buildUpon()
                        .appendPath(parameter)
                        .appendQueryParameter(UNITS_PARAM, units) //adds a ?PARAM=units and fragment adds a #
                        .appendQueryParameter(UNITS_PARAM2, units2)
                        .build();

                URL url = new URL(builtUri.toString());

                //HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }


                forecastJsonStr = buffer.toString();
                send_to_the_adapter = forecastJsonStr;

                //String returnthis = url.toString();
                Log.e("background: ", send_to_the_adapter);

                urlConnection.disconnect();


            } catch (MalformedURLException ex) {
                Log.e("httptest", Log.getStackTraceString(ex));
            } catch (IOException ex2) {
                Log.e("httptest", "Error ", ex2);
            }

            //put the Json text into a return string that was initialized earlier
            send_to_the_adapter = forecastJsonStr;


            return forecastJsonStr;
        }


        public void onPostExecute(String result) {
            //result = returnstring;

            if (result != null) {
                Log.e("this is the result", send_to_the_adapter);
            }

            //send_to_the_adapter = result;

            //Log.e("from post execute", send_to_the_adapter);

        }



    }

}
