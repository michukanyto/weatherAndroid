package com.appsmontreal.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.URLDecoder;
import java.net.URLEncoder;

import Controller.DownloadTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String APIKEY = "&APPID=928f54db8907a508f46ec83ac76094df";
    private static final String URLSTRING = "http://api.openweathermap.org/data/2.5/weather?q=";
    Button []buttons = new Button[4];
    int []widgets = {R.id.searchButton,R.id.celsiusButton,R.id.fahrenheitButton,R.id.exitButton};
    EditText cityEditText;
    TextView temperatureTextView;
    TextView minTextView;
    TextView maxTextView;
    TextView humidityTextView;
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
        temperatureTextView = findViewById(R.id.temTextView);
        minTextView = findViewById(R.id.minTextView);
        maxTextView = findViewById(R.id.maxTextView);
        humidityTextView = findViewById(R.id.humidityTextView);
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
//        convertToCelsius();
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
//                convertToCelsius();
                break;

            case R.id.exitButton:
                finish();
                break;
        }
    }


//    ° F = 9/5 (K - 273) + 32
    private void convertToFahrenheit() {
        String tempFahrenheit = String.valueOf(((9/5) * (task.getTemperature() - 273)) + 32);
        String minFahrenheit = String.valueOf(((9/5) * (task.getMinimal() - 273)) + 32);
        String maxFahrenheit = String.valueOf(((9/5) * (task.getMaximal() - 273)) + 32);
        setAndPrintResult(tempFahrenheit,minFahrenheit,maxFahrenheit);
    }

    private void convertToCelsius() {
        String tempCelsius = String.valueOf(task.getTemperature() - 273.15);
        String minCelsius = String.valueOf(task.getMinimal() - 273.15);
        String maxCelsius = String.valueOf(task.getMaximal() - 273.15);
        setAndPrintResult(tempCelsius,minCelsius,maxCelsius);
    }

    private void setAndPrintResult(String temp, String min, String max) {
        temperatureTextView.setText(String.format("%.1f",temp));
        minTextView.setText(String.format("%.1f",min));
        maxTextView.setText(String.format("%.1f",max));
        humidityTextView.setText(String.valueOf(task.getHumidity()));

    }
}
