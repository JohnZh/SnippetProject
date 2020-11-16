package com.john.newtest.trade;

import android.os.Bundle;

import com.john.jrouter.annotation.Route;

import androidx.appcompat.app.AppCompatActivity;

@Route("/trade/main")
public class TradeMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_main_activity);
    }
}
