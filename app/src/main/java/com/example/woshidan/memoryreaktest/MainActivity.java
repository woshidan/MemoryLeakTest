package com.example.woshidan.memoryreaktest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.woshidan.memoryreaktest.often_leak.StaticFieldLeakActivity;
import com.example.woshidan.memoryreaktest.often_leak.AsyncTaskWithLeakActivity;
import com.example.woshidan.memoryreaktest.rarely_leak.AsyncTaskWithoutLeakActivity;
import com.example.woshidan.memoryreaktest.rarely_leak.BottomSheetActivity;
import com.example.woshidan.memoryreaktest.rarely_leak.DialogActivity;
import com.example.woshidan.memoryreaktest.rarely_leak.NavigationViewActivity;
import com.example.woshidan.memoryreaktest.rarely_leak.RecyclerViewActivity;
import com.example.woshidan.memoryreaktest.rarely_leak.RxJavaActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        findViewById(R.id.bottomSheet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBottomSheet(v);
            }
        });

        findViewById(R.id.navigationView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNavigationView(v);
            }
        });

        findViewById(R.id.recyclerView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRecyclerView(v);
            }
        });

        findViewById(R.id.dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(v);
            }
        });

        findViewById(R.id.staticLeak).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStaticLeak(v);
            }
        });

        findViewById(R.id.asyncTaskWithLeak).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAsyncTaskWithLeak(v);
            }
        });

        findViewById(R.id.rxJava).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRxJava(v);
            }
        });

        findViewById(R.id.asyncTaskWithoutLeak).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAsyncTaskWithoutLeak(v);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(null);
        findViewById(R.id.bottomSheet).setOnClickListener(null);
        findViewById(R.id.navigationView).setOnClickListener(null);
        findViewById(R.id.recyclerView).setOnClickListener(null);
        findViewById(R.id.dialog).setOnClickListener(null);
        findViewById(R.id.staticLeak).setOnClickListener(null);
        findViewById(R.id.asyncTaskWithLeak).setOnClickListener(null);
        findViewById(R.id.rxJava).setOnClickListener(null);
        findViewById(R.id.asyncTaskWithoutLeak).setOnClickListener(null);

        super.onDestroy();
    }

    private void openBottomSheet(View view) {
        Intent intent = new Intent(this, BottomSheetActivity.class);
        startActivity(intent);
    }

    private void openNavigationView(View view) {
        Intent intent = new Intent(this, NavigationViewActivity.class);
        startActivity(intent);
    }

    private void openRecyclerView(View view) {
        Intent intent = new Intent(this, RecyclerViewActivity.class);
        startActivity(intent);
    }

    private void openDialog(View view) {
        Intent intent = new Intent(this, DialogActivity.class);
        startActivity(intent);
    }

    private void openStaticLeak(View view) {
        Intent intent = new Intent(this, StaticFieldLeakActivity.class);
        startActivity(intent);
    }

    private void openAsyncTaskWithLeak(View view) {
        Intent intent = new Intent(this, AsyncTaskWithLeakActivity.class);
        startActivity(intent);
    }

    private void openRxJava(View view) {
        Intent intent = new Intent(this, RxJavaActivity.class);
        startActivity(intent);
    }

    private void openAsyncTaskWithoutLeak(View view) {
        Intent intent = new Intent(this, AsyncTaskWithoutLeakActivity.class);
        startActivity(intent);
    }
}
