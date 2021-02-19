package com.john.newtest.kotlin

import android.util.Log
import com.john.newtest.BuildConfig

/**
 * Created by JohnZh on 11/26/20
 *
 * <p>It is convenient to print info when debug, use with live template</p>
 */
inline fun Any.logDebug(msg: String) {
    if (BuildConfig.DEBUG) Log.d("TEST", msg)
}