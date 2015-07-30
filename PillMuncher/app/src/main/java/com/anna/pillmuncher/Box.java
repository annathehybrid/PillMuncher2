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
import android.widget.ArrayAdapter;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Box extends AppCompatActivity {

    //ImageButton imgButton;
    //public TextView first_textbox;
    //public TextView second_textbox;
    public final static String EXTRA_MESSAGE = "This is an extra message!";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_box);

        //imgButton =(ImageButton)findViewById(R.id.imageButton);
        //first_textbox = (TextView) findViewById(R.id.textbox_1);
        //second_textbox = (TextView) findViewById(R.id.textbox_2);

        String text = "You are in fragment_box";
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }





}