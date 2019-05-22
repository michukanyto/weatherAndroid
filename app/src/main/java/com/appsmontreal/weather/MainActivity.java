package com.appsmontreal.weather;

import android.graphics.Color;
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
        String tempFahrenheit = String.format("%.1f", ((task.getTemperature() - 273) * (9/5)) + 32);
        String minFahrenheit = String.format("%.1f", ((task.getMinimal() - 273) * (9/5)) + 32);
        String maxFahrenheit = String.format("%.1f", ((task.getMaximal() - 273) * (9/5)) + 32);
        setAndPrintResult(tempFahrenheit,minFahrenheit,maxFahrenheit);
        buttons[2].setTextColor(Color.WHITE);
        buttons[1].setTextColor(Color.BLACK);
    }

    private void convertToCelsius() {
        String tempCelsius = String.format("%.1f", task.getTemperature() - 273.15);
        String minCelsius = String.format("%.1f", task.getMinimal() - 273.15);
        String maxCelsius = String.format("%.1f", task.getMaximal() - 273.15);
        setAndPrintResult(tempCelsius,minCelsius,maxCelsius);
        buttons[1].setTextColor(Color.WHITE);
        buttons[2].setTextColor(Color.BLACK);
    }

    private void setAndPrintResult(String temp, String min, String max) {
        temperatureTextView.setText(temp);
        minTextView.setText(min);
        maxTextView.setText(max);
        humidityTextView.setText(String.valueOf(task.getHumidity()));

    }
}
