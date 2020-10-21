package com.john.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.john.common.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * Modified by john on 2018/11/1
 * <p>
 * Description:
 * <p>
 * APIs:
 */
public class Tags extends ViewGroup {

    private static final int TAG_ID = R.layout.view_tag;

    public interface OnTagClickListener {
        void onTagClick(View tagView, int tagIndex, String tag);
    }

    private int mTagInterval;
    private int mLineInterval;
    private OnTagClickListener mOnTagClickListener;

    public Tags(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        processAttrs(attrs);

        init();
    }

    private void init() {
        if (isInEditMode()) {
            for (int i = 0; i < 2; i++) {
                addTag("TEST" + i, R.layout.view_tag, R.id.text);
            }

            for (int i = 0; i < 2; i++) {
                addTag("TEST122: " + i, R.layout.view_tag, R.id.text);
            }

            for (int i = 0; i < 2; i++) {
                addTag("TEST 122 adasda: " + i, R.layout.view_tag, R.id.text);
            }
        }
    }

    private void processAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.Tags);

        processTypedArray(typedArray);

        typedArray.recycle();
    }

    private void processTypedArray(TypedArray typedArray) {
        mTagInterval = typedArray.getDimensionPixelOffset(R.styleable.Tags_tagInterval, 0);
        mLineInterval = typedArray.getDimensionPixelOffset(R.styleable.Tags_lineInterval, 0);
    }

    public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
        mOnTagClickListener = onTagClickListener;
    }

    public void addTag(final String tag, int viewLayoutRes, int textRes) {
        View tagView = LayoutInflater.from(getContext()).inflate(viewLayoutRes, null);
        addTag(tag, tagView, textRes);
    }

    public void removeTag(int tagIndex) {
        if (tagIndex >= 0 && tagIndex < getChildCount()) {
            View childAt = getChildAt(tagIndex);
            removeView(childAt);
        }
    }

    public void addTag(final String tag, View tagView, int textRes) {
        TextView textView = (TextView) tagView.findViewById(textRes);
        textView.setText(tag);
        textView.setTag(TAG_ID, tag);
        tagView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onTagClick(view, tag);
            }
        });
        addView(tagView);
    }

    private void onTagClick(View tagView, String tag) {
        if (mOnTagClickListener != null) {
            int tabIndex = indexOfChild(tagView);
            mOnTagClickListener.onTagClick(tagView, tabIndex, tag);
        }
    }

    public void addTags(String[] tags, int viewLayoutRes, int textRes) {
        addTags(Arrays.asList(tags), viewLayoutRes, textRes);
    }

    public void addTags(List<String> tags, int viewLayoutRes, int textRes) {
        for (String tag : tags) {
            addTag(tag, viewLayoutRes, textRes);
        }
    }

    public int getTagCount() {
        return getChildCount();
    }

    public List<String> getTagList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < getChildCount(); i++) {
            list.add((String) getChildAt(i).getTag(TAG_ID));
        }
        return list;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingRight() - getPaddingLeft();

        int rowWidth = 0;
        int rowHeight = 0;

        int finalWidth = 0;
        int finalHeight = 0;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            if (childWidth > width) {
                throw new RuntimeException("Child view is too big");
            }

            int nextRowWidth = rowWidth + mTagInterval + childWidth;
            if (i == 0) {
                nextRowWidth = rowWidth + childWidth;
            }

            if (nextRowWidth > width) { // 累计行宽大于当前控件宽，换行
                finalWidth = Math.max(finalWidth, rowWidth);
                rowWidth = childWidth;

                finalHeight += rowHeight + mLineInterval;
                rowHeight = childHeight;
            } else { // 同行
                rowWidth = nextRowWidth;

                rowHeight = Math.max(rowHeight, childHeight);
            }
        }

        finalWidth = Math.max(finalHeight, rowWidth);
        finalHeight += rowHeight;

        setMeasuredDimension(getFinalWidth(finalWidth, widthMeasureSpec),
                getFinalHeight(finalHeight, heightMeasureSpec));
    }

    private int getFinalHeight(int finalHeight, int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int result;

        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = finalHeight + getPaddingTop() + getPaddingBottom();
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }

        return result;
    }

    private int getFinalWidth(int finalWidth, int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int result;

        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = finalWidth + getPaddingLeft() + getPaddingRight();
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }

        return result;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int x = getPaddingLeft();
        int y = getPaddingTop();

        int width = right - left - getPaddingLeft() - getPaddingRight();

        int rowWidth = 0;
        int rowHeight = 0;
        int finalY = 0;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            int nextRowWidth = rowWidth + mTagInterval + childWidth;
            if (i == 0) {
                nextRowWidth = rowWidth + childWidth;
            }

            if (nextRowWidth > width) { // 累计行宽大于当前控件宽，换行
                rowWidth = childWidth;

                finalY += rowHeight + mLineInterval;
                rowHeight = childHeight;

                x = getPaddingLeft();
                y = finalY;
            } else { // 同行
                rowWidth = nextRowWidth;

                rowHeight = Math.max(rowHeight, childHeight);
            }

            child.layout(x, y, x + childWidth, y + childHeight);
            x += childWidth + mTagInterval;
        }
    }
}
