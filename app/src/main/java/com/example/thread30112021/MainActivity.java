package com.example.thread30112021;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    int a , b , c;
    MyFlag myFlag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        a = b = c = 0;
        myFlag = new MyFlag(0);

        // count
        // 0 => in ra a
        // 1 => in ra b
        // 2 => in ra c

        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
               synchronized (myFlag){
                   for (int i = 0; i < 10 ;) {
                      if (myFlag.count == 0){
                          a = i;
                          Log.d("BBB","A = " + a);
                          myFlag.count = 1;
                          myFlag.notifyAll();
                          i++;
                      }else {
                          try {
                              myFlag.wait();
                          } catch (InterruptedException e) {
                              e.printStackTrace();
                          }
                      }
                   }
               }
            }
        });

        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (myFlag){
                    for (int i = 0; i < 10 ; ) {
                        if (myFlag.count == 1){
                            b = i;
                            Log.d("BBB","B = " + b);
                            myFlag.count = 2;
                            myFlag.notifyAll();
                            i++;
                        }else {
                            try {
                                myFlag.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });

        Thread threadC = new Thread(new Runnable() {
            @Override
            public void run() {
               synchronized (myFlag){
                   for (int i = 0; i < 10 ;) {
                      if (myFlag.count == 2){
                          c = a + b;
                          Log.d("BBB","C = " + c);
                          myFlag.count = 0;
                          myFlag.notifyAll();
                          i++;
                      }else {
                          try {
                              myFlag.wait();
                          } catch (InterruptedException e) {
                              e.printStackTrace();
                          }
                      }
                   }
               }
            }
        });

        threadA.start();
        threadB.start();
        threadC.start();

    }
}