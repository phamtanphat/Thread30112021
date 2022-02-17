package com.example.thread30112021;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("BBB",Thread.currentThread().getName());
            }
        });

        thread.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("BBB",thread.getState().toString());
            }
        },500);
    }
}