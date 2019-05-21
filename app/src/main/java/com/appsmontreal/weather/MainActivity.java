package com.appsmontreal.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import Controller.DownloadTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String APIKEY = "&APPID=928f54db8907a508f46ec83ac76094df";
    private static final String URLSTRING = "http://api.openweathermap.org/data/2.5/weather?q=";
    Button searchButton;
    Button exitButton;
    Button []buttons = new Button[4];
    int []widgets = {R.id.searchButton,R.id.celciusButton,R.id.farenheitButton,R.id.exitButton};
    EditText cityEditText;
    DownloadTask task;


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
        task = new DownloadTask();
    }


    public void findCityWeather(){
        Log.i("Step------>","findCityWeather");
        task.execute(URLSTRING + cityEditText.getText().toString() + APIKEY);

    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.celciusButton:

                break;

            case R.id.farenheitButton:
                break;

            case R.id.searchButton:
                findCityWeather();
                break;

            case R.id.exitButton:
                finish();
                break;
        }
    }
}
