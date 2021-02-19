package com.john.newtest.view.surfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public abstract class BaseSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    public static final int DEFAULT_FRAME_DURATION_MILLISECOND = 50;

    private HandlerThread handlerThread;
    private SurfaceViewHandler handler;
    private int frameDuration = DEFAULT_FRAME_DURATION_MILLISECOND;
    private Canvas canvas;
    private boolean isRepeat;
    private boolean isStarted;

    public BaseSurfaceView(Context context) {
        super(context);
        init();
    }

    public BaseSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init() {
        getHolder().addCallback(this);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
        setZOrderOnTop(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startDrawThread();

        if (isStarted && handler != null) {
            handler.post(new DrawRunnable());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopDrawThread();
    }

    private void stopDrawThread() {
        handler.removeCallbacksAndMessages(null);
        handlerThread.quit();
        handler = null;
    }

    private void startDrawThread() {
        handlerThread = new HandlerThread("SurfaceViewThread");
        handlerThread.start();
        handler = new SurfaceViewHandler(handlerThread.getLooper());
    }

    public void start() {
        isStarted = true;
        if (handler != null) {
            handler.post(new DrawRunnable());
        }
    }

    public void stop() {
        isStarted = false;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    private class SurfaceViewHandler extends Handler {
        public SurfaceViewHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    private class DrawRunnable implements Runnable {

        @Override
        public void run() {
            if (handler == null || !isStarted) return;

            try {
                canvas = getHolder().lockCanvas();
                onFrameDraw(canvas);
                getHolder().unlockCanvasAndPost(canvas);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                onFrameDrawFinish();
            }

            if (handler != null) {
                handler.postDelayed(this, frameDuration);
            }
        }
    }

    /**
     * Set the frame interval
     *
     * @param frameDuration
     */
    public void setFrameDuration(int frameDuration) {
        this.frameDuration = frameDuration;
    }

    /**
     * @param repeat
     */
    public void setRepeat(boolean repeat) {
        isRepeat = repeat;
    }

    /**
     * @return
     */
    public boolean isRepeat() {
        return isRepeat;
    }

    /**
     * it is will be invoked after one frame is drawn
     */
    protected abstract void onFrameDrawFinish();

    /**
     * draw one frame to the surface by canvas
     *
     * @param canvas
     */
    protected abstract void onFrameDraw(Canvas canvas);
}
