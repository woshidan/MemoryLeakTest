package com.example.woshidan.memoryreaktest.rarely_leak;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.woshidan.memoryreaktest.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by woshidan on 2016/05/22.
 */
public class RecyclerViewActivity extends AppCompatActivity {
    private int count = 0;
    static final String KEY_COUNT = "ToolbarActivity.KEY_COUNT";
    private FloatingActionButton fab;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // テスト用に大量にActivityなどを作成/削除した時に誤ってアプリを終了しないようにカウントを出す
        count = getIntent().getIntExtra(KEY_COUNT, 0);
        getSupportActionBar().setTitle("open count :" + count);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRecyclerView(view);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        // わざとActivityContextを渡しているが、ごく稀にしかリークしなかった
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//            }
//        });
        recyclerView.setAdapter(new CustomAdapter(this, new ArrayList<Integer>(Arrays.asList(1,2,3,4, 5, 6, 7, 8, 9, 10, 11, 12,13,14,15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25))));
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
//        recyclerView.setAdapter(null);
//        recyclerView = null;
        super.onDestroy();
    }

    private void openRecyclerView(View view) {
        Intent intent = new Intent(this, RecyclerViewActivity.class);
        intent.putExtra(KEY_COUNT, count + 1);
        startActivity(intent);
    }

    private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
        Context mContext;
        ArrayList<Integer> mData;
        public CustomAdapter(Context context, ArrayList<Integer> data) {
            mContext = context;
            mData = data;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_layout, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.button.setOnClickListener(new CustomClickListener());
        }

        @Override
        public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
            // mContext = null;
            super.onDetachedFromRecyclerView(recyclerView);
        }

        @Override
        public void onViewDetachedFromWindow(ViewHolder holder) {
            // ViewHolderのクリックリスナや各種リソースの解放など
            // holder.button.setOnClickListener(null);
            super.onViewDetachedFromWindow(holder);
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            public AppCompatButton button;
            public ViewHolder(View itemView) {
                super(itemView);
                button = (AppCompatButton) itemView.findViewById(R.id.childButton);
            }
        }

        class CustomClickListener implements View.OnClickListener {
            @Override
            public void onClick(View v) {
                Log.d("CustomClickListener", "tapped!");
            }
        }
    }
}
