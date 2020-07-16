package com.john.webview;

import android.os.Bundle;

import com.john.common.BaseActivity;
import com.john.jrouter.annotation.Route;
import com.john.webview.databinding.ActivityTestBinding;

@Route("/web/test")
public class TestActivity extends BaseActivity {

    private ActivityTestBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //JRouter.get().message("/web/test2").route(getActivity());
    }
}
