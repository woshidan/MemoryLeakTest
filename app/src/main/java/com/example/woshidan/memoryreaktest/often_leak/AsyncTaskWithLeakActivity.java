package com.example.woshidan.memoryreaktest.often_leak;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;

import com.example.woshidan.memoryreaktest.R;


public class AsyncTaskWithLeakActivity extends AppCompatActivity {
    static final String TAG = "AsyncTaskWithLeak";
    private ExampleTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);

        mTask = new ExampleTask(this);
        mTask.execute(100L, 100L, 100L, 100L, 100L, 3000L, 30000L);
    }

    private class ExampleTask extends AsyncTask<Long, Void, String> {
        private Context mContext;
        public ExampleTask(Context context) {
            mContext = context;
        }

        @Override
        protected String doInBackground(Long... params) {
            for(long duration : params) {
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
