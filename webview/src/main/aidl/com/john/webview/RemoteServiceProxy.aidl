// RemoteServiceProxy.aidl
package com.john.webview;

// Declare any non-default types here with import statements
import com.john.webview.CallbackFromService;

interface RemoteServiceProxy {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */

    void onDispatch(String cmd, String jsonParams, CallbackFromService call);
}
