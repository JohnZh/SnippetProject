package com.john.newtest;

import android.app.Application;
import android.util.Log;

import com.john.newtest.hotfix.HotFix;

import java.io.File;

import androidx.core.content.ContextCompat;

/**
 * Created by JohnZh on 2020/6/23
 *
 * <p></p>
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        File patchFile = new File("/sdcard/patch.dex");
        if (patchFile.exists()) {
            Log.d("Temp", "onCreate: " + "fix bug"); // todo remove later
            HotFix.fix(patchFile, ContextCompat.getCodeCacheDir(getApplicationContext()), getClassLoader());
        } else {
            Log.d("Temp", "onCreate: " + "no bug to fix"); // todo remove later
        }
    }
}
