package com.example.thread30112021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int a , b , c;
    MyFlag myFlag;
    Handler handler;
    TextView mTvA,mTvB,mTvC;
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvA = findViewById(R.id.textA);
        mTvB = findViewById(R.id.textB);
        mTvC = findViewById(R.id.textC);

        a = b = c = 0;
        myFlag = new MyFlag(0);

        // count
        // 0 => in ra a
        // 1 => in ra b
        // 2 => in ra c
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0 :
                        mTvA.setText("A " + msg.obj);
                        break;
                    case 1 :
                        mTvB.setText("B " + msg.obj);
                        break;
                    case 2 :
                        mTvC.setText("C " + msg.obj);
                        break;
                }
            }
        };

        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
               synchronized (myFlag){
                   for (int i = 0; i < 10 ;) {
                      if (myFlag.count == 0){
                          a = i;
                          try {
                              Thread.sleep(200);
                          } catch (InterruptedException e) {
                              e.printStackTrace();
                          }
                          Message message = new Message();
                          message.what = 0;
                          message.obj = a;
                          handler.sendMessage(message);
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
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Message message = new Message();
                            message.what = 1;
                            message.obj = b;
                            handler.sendMessage(message);
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
                          try {
                              Thread.sleep(200);
                          } catch (InterruptedException e) {
                              e.printStackTrace();
                          }
                          Message message = new Message();
                          message.what = 2;
                          message.obj = c;
                          handler.sendMessage(message);
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