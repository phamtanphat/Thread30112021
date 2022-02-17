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

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("CCC",threadA.getState().toString());
                Log.d("CCC",threadB.getState().toString());
            }
        },2000);
    }
    private synchronized void printLog(String text){
        for (int i = 0; i < 100 ; i++) {
            Log.d("BBB",text + " : " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}