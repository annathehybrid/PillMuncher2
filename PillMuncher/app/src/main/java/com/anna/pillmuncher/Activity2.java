package com.anna.pillmuncher;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL ;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;


public class Activity2 extends AppCompatActivity {

    ImageButton imgButton;
    private TextView first_textbox;
    private TextView second_textbox;


    //HttpURLConnection conn = (HttpURLConnection) u.openConnection();

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
        addContentView(textView, layout);



        final String jObject = null;
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String sleepTime = time.getText().toString();
                //runner.execute(sleepTime);
                MyDownloadTask downloadtask = new MyDownloadTask();
                new MyDownloadTask().execute();

                //MyDownloadTask runner = new MyDownloadTask();

                //jObject = new MyDownloadTask().doInBackground();

                //Log.e("HMmmmmmMM",runner);
            }
        });

    }

    public class MyDownloadTask extends AsyncTask<Void, Void, String>
    {

        TextView output = (TextView)findViewById(R.id.textView2);
        //output.setText("Something must have happened here");

        protected void onPreExecute() {
            //display progress dialog.

        }


        @Override
        protected String doInBackground(Void... params) {


            //URL url;
            //HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String forecastJsonStr = null;

            try {
                // Construct the URL
                final String RXNORM_BASE_URL = "http://rxnav.nlm.nih.gov/REST/";
                URL url = new URL("http://rxnav.nlm.nih.gov/REST/rxcui.json?idtype=NDC&id=11523-7020-1");

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                //urlConnection.setAllowUserInteraction(false);
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();


                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }

                forecastJsonStr = buffer.toString();



                Log.e("HELOOOOOOOO httptest",forecastJsonStr);

                urlConnection.disconnect();


            }
            catch (MalformedURLException ex) {
                Log.e("httptest",Log.getStackTraceString(ex));
            }
            catch (IOException ex2) {
                Log.e("httptest", "Error ", ex2);
            }


            runOnUiThread(new Thread() {

                //final String forecastJsonStr = forecastJsonStr;

                public void run() {

                    TextView output = (TextView) findViewById(R.id.textView2);
                    //final String message = forecastJsonStr;
                    output.setText("async thread text! yay");
                }
            });



            return forecastJsonStr;
        }



        protected void onPostExecute(Void result) {
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
