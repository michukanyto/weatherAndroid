package com.appsmontreal.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.net.URLDecoder;
import java.net.URLEncoder;

import Controller.DownloadTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String APIKEY = "&APPID=928f54db8907a508f46ec83ac76094df";
    private static final String URLSTRING = "http://api.openweathermap.org/data/2.5/weather?q=";
    Button []buttons = new Button[4];
    int []widgets = {R.id.searchButton,R.id.celsiusButton,R.id.fahrenheitButton,R.id.exitButton};
    EditText cityEditText;
    DownloadTask task;
    String encodeCityName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        for(int b = 0 ; b < buttons.length; b++){
            buttons[b] = findViewById(widgets[b]);
            buttons[b].setOnClickListener(this);
        }
        cityEditText = findViewById(R.id.cityEditText);
    }


    public void findCityWeather(){
        try {
            task = new DownloadTask();
            encodeCityName = URLEncoder.encode(cityEditText.getText().toString(), "UTF-8");
            Log.i("Step------>", "findCityWeather");
            task.execute(URLSTRING + encodeCityName + APIKEY);
            InputMethodManager mgr = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(cityEditText.getWindowToken(), 0);//hide automatically keyboard to see all content
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.celsiusButton:
                convertToCelsius();
                break;

            case R.id.fahrenheitButton:
                convertToFahrenheit();
                break;

            case R.id.searchButton:
                findCityWeather();
                break;

            case R.id.exitButton:
                finish();
                break;
        }
    }

    private void convertToFahrenheit() {
    }

    private void convertToCelsius() {
    }
}
