package com.john.newtest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by JohnZh on 2020/8/26
 *
 * <p></p>
 */
public class ParentView extends LinearLayout {

    public ParentView(Context context) {
        super(context);
    }

    public ParentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("Temp", "onMeasure: " + widthMeasureSpec); // todo remove later
        Log.d("Temp", "onMeasure: " + heightMeasureSpec); // todo remove later
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int actionMasked = ev.getActionMasked();
        Log.d("Temp", "onInterceptTouchEvent: " + actionMasked); // todo remove later
        switch (actionMasked) {
            case MotionEvent.ACTION_MOVE:
                Log.d("Temp", "onInterceptTouchEvent: consume " + actionMasked); // todo remove later
                return true;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
