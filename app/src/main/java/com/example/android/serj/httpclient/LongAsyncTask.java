package com.example.android.serj.httpclient;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Created by sergey on 7/3/16.
 */
public class LongAsyncTask extends AsyncTask<String, Integer, String> {

    private final ProgressBar progressBar;
    private final Activity activity;

    public LongAsyncTask(ProgressBar progressBar, Activity activity) {
        this.progressBar = progressBar;
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... params) {
        for (int i = 0; !isCancelled() && i < 100; i++) {
            SystemClock.sleep(100);
            publishProgress(i);
        }

        return "Done!";
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        this.progressBar.setProgress(values[0]);
        Toast.makeText(activity, "iteration:" + values[0], Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}