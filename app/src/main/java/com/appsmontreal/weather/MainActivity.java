package com.appsmontreal.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import Controller.DownloadTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button searchButton;
    Button exitButton;
    Button celciusButton;
    Button farenheitButton;
    EditText cityEditText;

    DownloadTask task;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        searchButton = findViewById(R.id.searchButton);
        exitButton = findViewById(R.id.exitButton);
        cityEditText = findViewById(R.id.editTextCity);
        task = new DownloadTask();
    }

    public void endApplication(){
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void findCityWeather(){
        task.execute("http://openweathermap.org/data/2.5/weather?q=" + cityEditText.getText().toString() +"&appid=b1b15e88fa797225412429c1c50c122a1");
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
