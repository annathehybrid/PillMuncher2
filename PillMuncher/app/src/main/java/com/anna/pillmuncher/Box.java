package com.anna.pillmuncher;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Box extends AppCompatActivity {

    ImageButton imgButton;
    public TextView first_textbox;
    public TextView second_textbox;
    public final static String EXTRA_MESSAGE = "This is an extra message!";
    public String add_to_adapter;
    public String see_if_this_works;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box);

        imgButton =(ImageButton)findViewById(R.id.imageButton);
        first_textbox = (TextView) findViewById(R.id.textbox_1);
        second_textbox = (TextView) findViewById(R.id.textbox_2);

        String text = "You are in fragment_box";
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();


        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Client client = Client.create();

                //Intent intent = new Intent(getApplicationContext(), Activity2.class);
                //String message = "you managed to pipe in text";
                //intent.putExtra(EXTRA_MESSAGE, message);
                //startActivity(intent);

                //startActivity(intent);


                MyDownloadTask downloadtask = new MyDownloadTask();
                new MyDownloadTask().execute("input_here");

                see_if_this_works = downloadtask.see_if_this_works; //works
                //add_to_adapter = downloadtask.doInBackground("input_here");

                add_to_adapter = downloadtask.send_to_the_adapter;
                Toast.makeText(getApplicationContext(), add_to_adapter, Toast.LENGTH_SHORT).show();

            }
        });



    }


    public class MyDownloadTask extends AsyncTask<String, Void, String> {



        public String send_to_the_adapter = "whooo"; //gah how do I pull this from the background???
        public String see_if_this_works = "hello I'm in the Async task"; //this works
        TextView output = (TextView) findViewById(R.id.textbox_1);


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
            runOnUiThread(new Thread() {
                

                //final String forecastJsonStr = forecastJsonStr;
                public void run() {


                    TextView output = (TextView) findViewById(R.id.textbox_1);
                    //final String message = forecastJsonStr;
                    output.setText(send_to_the_adapter);
                }
            });

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search)
        {
            Context context = getApplicationContext();
            CharSequence text = "You made a search!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return true;
        }

        else if (id == R.id.action_new)
        {
            Intent intent = new Intent(this, Activity2.class);
            startActivity(intent);

            return true;
        }

        else if (id == R.id.action_settings)
        {
            //Intent intent = new Intent(this, SettingsActivity.class);
            //startActivity(intent);
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);

    }




}