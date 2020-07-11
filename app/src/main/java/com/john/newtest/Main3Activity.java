package com.john.newtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.john.jrouter.annotation.Route;
import com.john.newtest.utils.Launcher;

import androidx.appcompat.app.AppCompatActivity;

@Route("/app/main3")
public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Launcher.with(getApplication(), Main4Activity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).execute();
            }
        });
    }
}
