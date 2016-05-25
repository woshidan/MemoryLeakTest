package com.example.woshidan.memoryreaktest.often_leak;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.woshidan.memoryreaktest.R;

// ref. http://www.slideshare.net/shaobin0604/memory-management-forandroidapps
public class StaticFieldLeakActivity extends AppCompatActivity {
    static Leak sLeak;

    static class Leak {
        private Context mContext;
        public void setContext(Context context) {
            mContext = context;
        }
        public void log() {
            Log.d("StaticFieldLeakActivity", "Weee!!");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static_field_leak);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (sLeak == null) {
            sLeak = new Leak();
            sLeak.setContext(this);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
