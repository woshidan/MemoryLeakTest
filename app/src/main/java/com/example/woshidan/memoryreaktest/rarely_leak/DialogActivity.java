package com.example.woshidan.memoryreaktest.rarely_leak;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.woshidan.memoryreaktest.R;

/**
 * Created by woshidan on 2016/05/22.
 */
public class DialogActivity extends AppCompatActivity {
    private int count = 0;
    static final String KEY_COUNT = "DialogActivity.KEY_COUNT";
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // テスト用に大量にActivityなどを作成/削除した時に誤ってアプリを終了しないようにカウントを出す
        count = getIntent().getIntExtra(KEY_COUNT, 0);
        getSupportActionBar().setTitle("open count :" + count);

    // これは回転時などにwindowがリークする...とLogcatには出るがMemoryMonitorなど反応せず...
//
//        AlertDialog alertDialog = new AlertDialog.Builder(
//                this
//        ).setCancelable(true).create();
//
//        alertDialog.setTitle("Test");
//        alertDialog.show();

        // Activityを回転させない, onPauseなどでdismissするなどの方法も
        FragmentManager fm = getSupportFragmentManager();
        AlertFragment af = new AlertFragment();
        af.show(fm, "alert_dialog");

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(view);
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
        fab.setOnClickListener(null);
        super.onDestroy();
    }

    private void openDialog(View view) {
        Intent intent = new Intent(this, DialogActivity.class);
        intent.putExtra(KEY_COUNT, count + 1);
        startActivity(intent);
    }
}

