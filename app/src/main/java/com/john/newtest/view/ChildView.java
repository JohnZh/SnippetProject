package com.john.newtest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by JohnZh on 2020/8/26
 *
 * <p></p>
 */
public class ChildView extends View {
    public ChildView(Context context) {
        super(context);
    }

    public ChildView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("Temp", "onMeasure: " + widthMeasureSpec); // todo remove later
        Log.d("Temp", "onMeasure: " + heightMeasureSpec); // todo remove later
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int actionMasked = event.getActionMasked();
        Log.d("Temp", "onTouchEvent: " + actionMasked); // todo remove later
        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN:
                Log.d("Temp", "onTouchEvent: consume " + actionMasked); // todo remove later
                return true;
        }
        return super.onTouchEvent(event);
    }
}
