package com.john.newtest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.john.bindlib.BindLib;
import com.john.bindlib.annotation.BindView;

import androidx.appcompat.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity {

    @BindView(R.id.textView)
    Button mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        BindLib.bind(this);

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
