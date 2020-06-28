package com.john.newtest.activities;

import android.Manifest;
import android.os.Bundle;

import com.john.newtest.BaseActivity;
import com.john.newtest.databinding.ActivityHotFixBinding;
import com.john.newtest.hotfix.Calculator;

import androidx.core.app.ActivityCompat;

/**
 * 热修复的示例代码
 */
public class HotFixActivity extends BaseActivity {

    ActivityHotFixBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityHotFixBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.showBug.setOnClickListener(v -> {
            float divide = Calculator.divide(1, 0);
            mBinding.output.setText(String.valueOf(divide));
        });

        mBinding.fixBug.setOnClickListener(v -> {
//            File patchFile = new File("/sdcard/patch.dex");
//            HotFix.fix(patchFile, getCacheDir(), getClassLoader());
        });

        ActivityCompat.requestPermissions(getActivity(),
                new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }


}
