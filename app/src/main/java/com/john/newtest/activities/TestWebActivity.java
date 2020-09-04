package com.john.newtest.activities;

import android.os.Bundle;
import android.util.Log;

import com.john.jrouter.JRouter;
import com.john.jrouter.RouteCallback;
import com.john.jrouter.RouteMsg;
import com.john.jrouter.annotation.Route;
import com.john.newtest.R;

import androidx.appcompat.app.AppCompatActivity;

@Route("/app/testweb")
public class TestWebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_web);

        JRouter.get().message("/web/webview")
                //.putString("url", "https://www.baidu.com")
                .route(new RouteCallback() {
                    @Override
                    public void onLost(RouteMsg msg) {
                        Log.d("Temp", "onLost: "); // todo remove later
                    }

                    @Override
                    public void onFound(RouteMsg msg) {
                    }

                    @Override
                    public void onArrived(RouteMsg msg) {

                    }
                });
    }
}
