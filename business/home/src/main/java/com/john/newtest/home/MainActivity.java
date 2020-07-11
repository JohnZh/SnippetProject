package com.john.newtest.home;

import android.os.Bundle;

import com.john.jrouter.annotation.Route;

import androidx.appcompat.app.AppCompatActivity;

@Route("/home/main")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);
    }
}