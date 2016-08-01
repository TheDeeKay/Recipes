package co.bstorm.aleksa.recipes.config;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

/**
 * Created by aleksa on 8/2/16.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}
