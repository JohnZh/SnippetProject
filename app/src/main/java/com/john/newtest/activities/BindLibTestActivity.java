package com.john.newtest.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.john.bindlib.BindLib;
import com.john.bindlib.annotation.BindView;
import com.john.newtest.R;

import androidx.appcompat.app.AppCompatActivity;

public class BindLibTestActivity extends AppCompatActivity {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.button)
    Button mButton;
    @BindView(R.id.output)
    TextView mOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_lib_test);
        BindLib.bind(this);

        mTitle.setText("BindView Test");
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOutput.setText("app/generated/ap_generated_source/debug/out");
            }
        });
    }
}
