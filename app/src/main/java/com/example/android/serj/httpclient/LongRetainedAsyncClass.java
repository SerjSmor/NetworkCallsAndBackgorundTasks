package com.example.android.serj.httpclient;

import android.os.AsyncTask;
import android.os.SystemClock;

/**
 * Created by sergey on 7/3/16.
 */
public class LongRetainedAsyncClass extends AsyncTask<String, Integer, String> {

    private TaskFragment.TaskCallbacks callbacks;

    public LongRetainedAsyncClass(TaskFragment.TaskCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    protected String doInBackground(String... params) {
        for (int i = 0; !isCancelled() && i < 1000; i++) {
            SystemClock.sleep(100);
            publishProgress(i);
        }

        return "Done!";
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        callbacks.onProgressUpdate(values[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        callbacks.onCancelled();
    }

    public void setCallback(TaskFragment.TaskCallbacks callback) {
        this.callbacks = callback;
    }
}
