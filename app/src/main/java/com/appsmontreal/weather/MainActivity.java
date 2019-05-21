package com.appsmontreal.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import Controller.DownloadTask;

public class MainActivity extends AppCompatActivity {
    Button searchButton;
    Button exitButton;
    DownloadTask task;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        searchButton = findViewById(R.id.buttonSearch);
        exitButton = findViewById(R.id.buttonExit);
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

    public void searchCityMeteo(){
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.execute("http://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b1b15e88fa797225412429c1c50c122a1");

            }
        });
    }
}
