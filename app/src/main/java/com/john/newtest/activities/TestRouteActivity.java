package com.john.newtest.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.john.common.BaseActivity;
import com.john.home_api.HomeDataService;
import com.john.jrouter.JRouter;
import com.john.jrouter.RouteCallback;
import com.john.jrouter.RouteCallbackWithInstance;
import com.john.jrouter.RouteMsg;
import com.john.jrouter.annotation.Route;
import com.john.newtest.R;
import com.john.newtest.databinding.ActivityTestRouteBinding;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

@Route("/app/testRoute")
public class TestRouteActivity extends BaseActivity {

    ActivityTestRouteBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityTestRouteBinding.inflate(LayoutInflater.from(this));
        setContentView(mBinding.getRoot());

        int intKey = getIntent().getIntExtra("intKey", -1);

        mBinding.openHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JRouter.get().message("/home/main").route(getActivity(), 100, new RouteCallback() {
                    @Override
                    public void onLost(RouteMsg msg) {
                        Toast.makeText(getActivity(), "lost", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFound(RouteMsg msg) {
                        Toast.makeText(getActivity(), "found", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onArrived(RouteMsg msg) {
                        Toast.makeText(getActivity(), "arrived", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        mBinding.loadFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JRouter.get().message("/home/blankFragment").route(new RouteCallbackWithInstance<Fragment>() {
                    @Override
                    public void onInstance(Fragment instance) {
                        addFragment2Activity(instance);
                    }
                });
            }
        });

        mBinding.showHomeData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JRouter.get().message("/home/homeData").route(new RouteCallbackWithInstance<HomeDataService>() {
                    @Override
                    public void onInstance(HomeDataService instance) {
                        Toast.makeText(getActivity(), instance.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Temp", "onDestroy: "); // todo remove later
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            Toast.makeText(this, "result from home", Toast.LENGTH_SHORT).show();
        }
    }

    private void addFragment2Activity(Fragment instance) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, instance, instance.getClass().getCanonicalName())
                .commit();
    }
}
