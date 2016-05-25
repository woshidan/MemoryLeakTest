package com.example.woshidan.memoryreaktest.rarely_leak;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.woshidan.memoryreaktest.MainActivity;
import com.example.woshidan.memoryreaktest.R;

/**
 * Created by woshidan on 2016/05/22.
 */
public class NavigationViewActivity extends AppCompatActivity {
    private int count = 0;
    static final String KEY_COUNT = "ToolbarActivity.KEY_COUNT";
    private static final String TAG = MainActivity.class.getSimpleName();
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView mNavigationView;
    private FloatingActionButton fab;
    private ImageView mUserIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // テスト用に大量にActivityなどを作成/削除した時に誤ってアプリを終了しないようにカウントを出す
        count = getIntent().getIntExtra(KEY_COUNT, 0);
        getSupportActionBar().setTitle("open count :" + count);

        mNavigationView = (NavigationView) findViewById(R.id.navigationHeader);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.menu_item_1:
                        Log.d(TAG, "menu 1がタップされました");
                        break;

                    case R.id.menu_item_2:
                        Log.d(TAG, "menu 2がタップされました");
                        break;


                    default:
                        break;
                }

                return false;
            }
        });

        mUserIcon = (ImageView) mNavigationView.getHeaderView(0).findViewById(R.id.user_icon);

        mUserIcon.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(NavigationViewActivity.this, TAG + " id:  clicked", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close
            );

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerLayout.closeDrawer(GravityCompat.START);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNavigationView(view);
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
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public void onDestroy() {
        // fab.setOnClickListener(null);
        //mDrawerLayout.removeDrawerListener(mDrawerToggle);
        //mNavigationView.setNavigationItemSelectedListener(null);
        //mUserIcon.setOnClickListener(null);
        super.onDestroy();
    }

    private void openNavigationView(View view) {
        Intent intent = new Intent(this, NavigationViewActivity.class);
        intent.putExtra(KEY_COUNT, count + 1);
        startActivity(intent);
    }
}
