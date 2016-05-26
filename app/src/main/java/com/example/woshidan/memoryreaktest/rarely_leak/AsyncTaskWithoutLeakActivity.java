package com.example.woshidan.memoryreaktest.rarely_leak;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;

import com.example.woshidan.memoryreaktest.R;

public class AsyncTaskWithoutLeakActivity extends AppCompatActivity {
    static final String TAG = "AsyncTaskWithoutLeak";
    private ExampleTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_leak_async_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTask = new ExampleTask(this);
        mTask.execute(100L, 100L, 100L, 100L, 100L, 3000L, 30000L);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTask.setContext(null);
        mTask.cancel(true);
    }

    private static class ExampleTask extends AsyncTask<Long, Void, String> {
        private Context mContext;
        public ExampleTask(Context context) {
            mContext = context;
        }

        public void setContext(Context context) {
            mContext = context;
        }

        @Override
        protected String doInBackground(Long... params) {
            for(long duration : params) {
                if (isCancelled()) {
                    return null;
                }
                Log.d(TAG, "duration: " + duration);
                try {
                    Thread.sleep(duration);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return (String) DateFormat.format("yyyy/MM/dd", System.currentTimeMillis());
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d(TAG, result);
        }
    }

}
