package com.john.newtest;

import android.os.Bundle;
import android.view.View;

import com.john.jrouter.annotation.Route;

import androidx.appcompat.app.AppCompatActivity;

@Route("/app/main4")
public class Main4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Main4Activity.this, Main2Activity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
//                startActivity(intent);
            }
        });
    }
}
