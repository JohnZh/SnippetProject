package com.john.newtest.view.surfaceview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * a SurfaceView which draws bitmaps one after another like frame animation
 */
public class FrameSurfaceView extends BaseSurfaceView {
    public static final int INVALID_BITMAP_INDEX = Integer.MAX_VALUE;

    private List<Integer> bitmaps = new ArrayList<>();
    private Bitmap frameBitmap;
    private int bitmapIndex = INVALID_BITMAP_INDEX;
    private Paint paint = new Paint();
    private BitmapFactory.Options options;
    private Rect srcRect;
    private Rect dstRect = new Rect();

    private int defaultWidth;
    private int defaultHeight;

    private int drawWidth;
    private int drawHeight;

    private boolean running;

    public FrameSurfaceView(Context context) {
        super(context);
    }

    public FrameSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FrameSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init() {
        super.init();
        options = new BitmapFactory.Options();
        options.inMutable = true;
    }

    public void setBitmaps(List<Integer> bitmaps) {
        if (bitmaps == null || bitmaps.size() == 0) {
            return;
        }
        this.bitmaps = bitmaps;

        //by default, take the first bitmap's dimension into consideration
        getBitmapDimension(bitmaps.get(0));
    }

    public void setDrawWidth(int drawWidth) {
        this.drawWidth = drawWidth;
    }

    public void setDrawHeight(int drawHeight) {
        this.drawHeight = drawHeight;
    }

    public boolean isInitBitmaps() {
        return !bitmaps.isEmpty();
    }

    private void getBitmapDimension(Integer integer) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), integer, options);
        defaultWidth = options.outWidth;
        defaultHeight = options.outHeight;
        srcRect = new Rect(0, 0, defaultWidth, defaultHeight);
        // Log.v(TAG, "FrameSurfaceView.getBitmapDimension()" + "  defaultWidth=" + defaultWidth + " defaultHeight=" + defaultHeight);
        // we have to re-measure to make defaultWidth in use in onMeasure()
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = measureDimension(widthMeasureSpec, drawWidth != 0 ? drawWidth : defaultWidth);
        int height = measureDimension(heightMeasureSpec, drawHeight != 0 ? drawHeight : defaultHeight);
        dstRect.set(0, 0, width, height);
        setMeasuredDimension(width, height);
    }

    private int measureDimension(int measureSpec, int defaultSize) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.AT_MOST:
                return defaultSize;
            case MeasureSpec.EXACTLY:
                return size < defaultSize ? size : defaultSize;
        }
        return defaultSize;
    }

    @Override
    protected void onFrameDrawFinish() {
//        recycle();
    }

    /**
     * recycle the bitmap used by frame animation.
     * Usually it should be invoked when the ui of frame animation is no longer visible
     */
    public void recycle() {
        if (null != frameBitmap) {
            frameBitmap.recycle();
            frameBitmap = null;
        }
    }

    @Override
    protected void onFrameDraw(Canvas canvas) {
        clearCanvas(canvas);
        if (!isRunning()) {
            resetFrame(canvas);
            return;
        }
        if (!isFinished()) {
            drawOneFrame(canvas);
        } else {
            onFrameAnimationEnd();
            resetFrame(canvas);
        }
    }

    /**
     * draw a single frame which is a bitmap
     *
     * @param canvas
     */
    private void drawOneFrame(Canvas canvas) {
//        Log.v(TAG, "ProgressRingSurfaceView.onFrameDraw()" + "  bitmapIndex=" + bitmapIndex + " measureWidth=" + getMeasuredWidth());
        if (isRepeat() && bitmapIndex >= bitmaps.size()) {
            bitmapIndex = 0;
        }
        if (bitmapIndex < bitmaps.size()) {
            drawBitmap(canvas, bitmapIndex);
        }
        bitmapIndex++;
    }

    /**
     * invoked when frame animation is done
     */
    private void onFrameAnimationEnd() {
        stop();
    }

    /**
     * whether frame animation is finished
     *
     * @return true: animation is finished, false: animation is doing
     */
    private boolean isFinished() {
        if (isRepeat()) {
            return !isRunning();
        } else {
            return (!isRunning()) || (bitmapIndex >= bitmaps.size());
        }
    }

    public boolean isRunning() {
        return running;
    }

    /**
     * start frame animation which means draw list of bitmaps from 0 index
     */
    @Override
    public void start() {
        setVisibility(VISIBLE);
        if (null == bitmaps || bitmaps.isEmpty()) {
            throw new RuntimeException("FrameSurfaceView must setBitmaps first");
        }
        if (!isRunning()) {
            running = true;
            bitmapIndex = 0;
        }
        super.start();
    }

    @Override
    public void stop() {
        setVisibility(GONE);
        if (isRunning()) {
            running = false;
            bitmapIndex = INVALID_BITMAP_INDEX;
        }
        super.stop();
    }

    /**
     * Reset to default frame
     *
     * @param canvas
     */
    private void resetFrame(Canvas canvas) {
        if (!bitmaps.isEmpty()) {
            drawBitmap(canvas, 0);
        }
    }

    private void drawBitmap(Canvas canvas, int index) {
        frameBitmap = decodeOriginBitmap(getResources(), bitmaps.get(index), options);
        options.inBitmap = frameBitmap;
        canvas.drawBitmap(frameBitmap, srcRect, dstRect, paint);
    }

    /**
     * clear out the drawing on canvas,preparing for the next frame
     * * @param canvas
     */
    private void clearCanvas(Canvas canvas) {
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawPaint(paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
    }

    private Bitmap decodeOriginBitmap(Resources res, int resId, BitmapFactory.Options options) {
        options.inScaled = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
}
