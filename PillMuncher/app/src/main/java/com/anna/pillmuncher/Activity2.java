package com.anna.pillmuncher;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL ;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Activity2 extends AppCompatActivity {


    ImageButton imgButton;
    public TextView first_textbox;
    public TextView second_textbox;
    public ArrayAdapter<String> Pill_Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity2);

        //time = (EditText) findViewById(R.id.et_time);
        imgButton =(ImageButton)findViewById(R.id.imageButton1);
        first_textbox = (TextView) findViewById(R.id.textbox);
        second_textbox = (TextView) findViewById(R.id.textView2);



        // Get the message from the intent
        Intent intent = getIntent();
        Bundle extras = getIntent().getExtras();

        String message = intent.getStringExtra(Box.EXTRA_MESSAGE);
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

        Log.i("debug", "made it to new class");
        // Create the text view

        int padding = 50;
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setPadding(padding, padding, padding, padding);
        textView.setText(message);

        // Set the text view as the activity layout
        GridLayout.LayoutParams layout = new GridLayout.LayoutParams();
        //addContentView(textView, layout);
        addContentView(textView, layout);


        final String jObject = null;
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyDownloadTask downloadtask = new MyDownloadTask();
                new MyDownloadTask().execute("input_here");

            }
        });
    }



    public class MyDownloadTask extends AsyncTask<String, Void, String> {
        public String returnstring = null;
        TextView output = (TextView) findViewById(R.id.textView2);
        //output.setText("Something must have happened here");

        protected void onPreExecute() {
            //display progress dialog.

        }

        String parameter = "rxcui.json";
        String units = "NDC";
        String units2 = "11523-7020-1";

        @Override
        protected String doInBackground(String... args) {


            //URL url;
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
                returnstring = forecastJsonStr;

                String returnthis = url.toString();
                Log.e("ANNA, LOOK AT THIS URL", forecastJsonStr);

                urlConnection.disconnect();


            } catch (MalformedURLException ex) {
                Log.e("httptest", Log.getStackTraceString(ex));
            } catch (IOException ex2) {
                Log.e("httptest", "Error ", ex2);
            }


            runOnUiThread(new Thread() {

                //final String forecastJsonStr = forecastJsonStr;
                public void run() {


                    TextView output = (TextView) findViewById(R.id.textView2);
                    //final String message = forecastJsonStr;
                    output.setText(returnstring);
                }
            });

            //return getWeatherDataFromJson(forecastJsonStr);
            return forecastJsonStr;
        }


        protected void onPostExecute(String result) {
            result = returnstring;

            if (result != null) {
                Log.e("this is the result", result);

                }
            // dismiss progress dialog and update ui
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.action_search) {
            return true;

        }
        else if (id == R.id.action_new) {
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

}
