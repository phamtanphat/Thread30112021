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

        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                printLog("A");
            }
        });

        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                printLog("B");
            }
        });
        threadA.start();
        threadB.start();
    }
    private void printLog(String text){
        for (int i = 0; i < 100 ; i++) {
            Log.d("BBB",text + " : " + i);
        }
    }
}