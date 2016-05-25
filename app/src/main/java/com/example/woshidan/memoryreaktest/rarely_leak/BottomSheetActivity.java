package com.example.woshidan.memoryreaktest.rarely_leak;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.woshidan.memoryreaktest.R;

/**
 * Created by woshidan on 2016/05/22.
 */
public class BottomSheetActivity extends AppCompatActivity {
    private int count = 0;
    static final String KEY_COUNT = "BottomSheetActivity.KEY_COUNT";
    private BottomSheetBehavior mBottomSheetBehavior;
    private AppCompatButton mToggleButton;
    private AppCompatButton mShowToast;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // テスト用に大量にActivityなどを作成/削除した時に誤ってアプリを終了しないようにカウントを出す
        count = getIntent().getIntExtra(KEY_COUNT, 0);
        getSupportActionBar().setTitle("open count :" + count);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        mToggleButton = (AppCompatButton) findViewById(R.id.toggleBottomSheet);
        mShowToast = (AppCompatButton) findViewById(R.id.showToast);

        View bottomSheet = findViewById(R.id.bottomSheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBottomSheet(view);
            }
        });

        mToggleButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                }
        );

        mShowToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BottomSheetActivity.this, "Click Button On The Seat", Toast.LENGTH_SHORT).show();
            }
        });

        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

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
//        fab.setOnClickListener(null);
//        mToggleButton.setOnClickListener(null);
//        mShowToast.setOnClickListener(null);
//        mBottomSheetBehavior.setBottomSheetCallback(null);
        super.onDestroy();
    }

    private void openBottomSheet(View view) {
        Intent intent = new Intent(this, BottomSheetActivity.class);
        intent.putExtra(KEY_COUNT, count + 1);
        startActivity(intent);
    }
}
