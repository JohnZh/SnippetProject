package com.john.common;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by JohnZh on 2020/7/11
 *
 * <p></p>
 */
public class BaseActivity extends AppCompatActivity {

    protected Activity getActivity() {
        return this;
    }

}
