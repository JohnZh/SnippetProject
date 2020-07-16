package com.john.newtest.mine;

import android.os.Bundle;
import android.view.View;

import com.john.jrouter.annotation.Route;
import com.john.newtest.mine.databinding.ActivityLoginBinding;

import androidx.appcompat.app.AppCompatActivity;

@Route("/mine/login")
public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
