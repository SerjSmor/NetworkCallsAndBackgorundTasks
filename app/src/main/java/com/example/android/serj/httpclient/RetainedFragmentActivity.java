package com.example.android.serj.httpclient;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class RetainedFragmentActivity extends Activity implements TaskFragment.TaskCallbacks {


    private static final String KEY_CURRENT_PROGRESS = "current_progress";
    private static final String TAG_TASK_FRAGMENT = "TAG_TASK_FRAGMENT";

    private static final String TAG = RetainedFragmentActivity.class.getSimpleName();


    private TaskFragment taskFragment;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (taskFragment.isRunning()) {
                    taskFragment.cancel();
                }
                else {
                    taskFragment.start();
                }
            }
        });

        if (savedInstanceState != null) {
            progressBar.setProgress(savedInstanceState.getInt(KEY_CURRENT_PROGRESS));
        }


        FragmentManager fm = getFragmentManager();
        taskFragment = (TaskFragment) fm.findFragmentByTag(TAG_TASK_FRAGMENT);
        // If the Fragment is non-null, then it is currently being
        // retained across a configuration change.
        if (taskFragment == null) {
            taskFragment = new TaskFragment();
            fm.beginTransaction().add(taskFragment, TAG_TASK_FRAGMENT).commit();
        }

    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onProgressUpdate(int percent) {

        int update = percent * progressBar.getMax() / 100;
        progressBar.setProgress(update);
//        Toast.makeText(this, "iteration:" + update, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancelled() {
        progressBar.setProgress(0);
        Toast.makeText(this, "Task cancelled!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPostExecute() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i(TAG, "onSaveInstanceState(Bundle)");
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_PROGRESS, progressBar.getProgress());
    }
}
