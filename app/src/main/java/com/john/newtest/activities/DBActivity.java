package com.john.newtest.activities;

import android.os.Bundle;
import android.widget.Toast;

import com.john.jrouter.annotation.Route;
import com.john.newtest.R;
import com.john.newtest.databinding.ActivityDBBinding;
import com.john.newtest.model.User;
import com.john.newtest.model.UserAssets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

@Route("/app/dbing")
public class DBActivity extends AppCompatActivity {

    private User mUser;
    private UserAssets mUserAssets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDBBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_d_b);

        mUser = new User("john", 30);
        mUser.setHeader("https://pics2.baidu.com/feed/5ab5c9ea15ce36d3269814c6850a3f80eb50b1ad.jpeg?token=206bd81b34051ec8199ce632ae735209");
        mUserAssets = new UserAssets();
        mUserAssets.setCash(-1);
        mUserAssets.setFutures(10);

        binding.setUser(mUser);
        binding.setUserAssets(mUserAssets);

        binding.getRoot().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(DBActivity.this, "change", Toast.LENGTH_SHORT).show();
                mUser.setName("jo");
                mUser.setHeader("https://img2.woyaogexing.com/2020/07/28/e6e1472357e44d6094b893d20d56c520!400x400.jpeg");
                //mUser.setAge(20);
            }
        }, 4000);
    }
}
