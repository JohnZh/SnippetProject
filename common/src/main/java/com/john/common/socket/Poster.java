package com.john.common.socket;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by JohnZh on 2020/10/14
 *
 * <p></p>
 */
public class Poster extends Handler {
    public Poster(Handler.Callback callback) {
        super(Looper.getMainLooper(), callback);
    }
}
