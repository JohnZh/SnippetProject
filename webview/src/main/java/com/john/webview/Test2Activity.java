package com.john.webview;

import android.os.Bundle;

import com.john.jrouter.annotation.Route;
import com.john.webview.databinding.ActivityTest2Binding;

import androidx.appcompat.app.AppCompatActivity;

@Route("/web/test2")
public class Test2Activity extends AppCompatActivity {

    ActivityTest2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTest2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
