package com.example.android.serj.httpclient;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class ThreadActivity extends Activity {

    private String url = "http://jsonplaceholder.typicode.com";
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startThread();
            }
        });




    }

    private void startThread() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // background
                NetworkManager.connectAndReturnResponse(url);

                // ui
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setProgress(100);
                    }
                });
            }
        };


        Thread myThread = new Thread(runnable);
        myThread.start();
    }
}
