package com.john.newtest.mine;

import android.os.Bundle;

import com.john.jrouter.annotation.Route;

import androidx.appcompat.app.AppCompatActivity;

@Route("/mine/main")
public class MineMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_main_activity);
    }
}
