package com.example.android.serj.httpclient;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * This project is an example of a basic get http method network call using a background thread vs
 * AsyncTask.
 *
 */

public class NetworkCallActivity extends Activity {
    private String url = "http://jsonplaceholder.typicode.com";
    private HttpAsyncTask task;
    private boolean isAsync = false;
    private Button button;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // network call
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        button = (Button) findViewById(R.id.button);



        setCheckBox();

        setNetworkButton();

    }

    private void setCheckBox() {
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isAsync = isChecked;
            }
        });
    }

    private void setNetworkButton() {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAsync) {
                    new HttpAsyncTask(progressBar).execute(url);
                }
                else {
                    NetworkManager.getRequestHttpConnection(url);
                }
            }
        });
    }




    public class HttpAsyncTask extends AsyncTask<String, Integer, String> {

        private final ProgressBar progressBar;

        public HttpAsyncTask(ProgressBar progressBar) {
            this.progressBar = progressBar;
        }

        // before (background thread)
        @Override
        protected String doInBackground(String... params) {
            // api from http://jsonplaceholder.typicode.com/
            publishProgress(50);

            String response = NetworkManager.connectAndReturnResponse("http://jsonplaceholder.typicode.com/posts/1");

            publishProgress(100);

            return response;
        }

        // in between - UI thread
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }

        // after (UI thread)
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Toast.makeText(NetworkCallActivity.this, result, Toast.LENGTH_SHORT).show();
        }


    }
}
