package com.example.woshidan.memoryreaktest;

import android.app.Application;
import android.os.StrictMode;

import com.squareup.leakcanary.*;

/**
 * Created by woshidan on 2016/05/23.
 */
public class TestApplication extends Application {
    @Override public void onCreate() {
        super.onCreate();
        enabledStrictMode();
        LeakCanary.install(this);
    }

    // https://github.com/square/leakcanary/blob/master/leakcanary-sample/src/main/java/com/example/leakcanary/ExampleApplication.java#L34
    private void enabledStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyDeath()
                .build());
    }
}
