package com.john.newtest.activities;

import android.os.Bundle;
import android.util.Log;

import com.john.jrouter.JRouter;
import com.john.jrouter.RouteCallback;
import com.john.jrouter.RoutePath;
import com.john.jrouter.annotation.Route;
import com.john.newtest.R;

import androidx.appcompat.app.AppCompatActivity;

@Route("/app/testweb")
public class TestWebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_web);

        JRouter.get().path("/web/webview")
                //.putString("url", "https://www.baidu.com")
                .route(new RouteCallback() {
                    @Override
                    public void onLost(RoutePath path) {
                        Log.d("Temp", "onLost: "); // todo remove later
                    }

                    @Override
                    public void onFound(RoutePath path) {
                    }

                    @Override
                    public void onArrived(RoutePath path) {

                    }
                });
    }
}
