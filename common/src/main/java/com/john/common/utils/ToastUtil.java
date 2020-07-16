package com.john.common.utils;

import android.widget.Toast;

import com.john.common.BaseApplication;

/**
 * Created by JohnZh on 2020/7/15
 *
 * <p></p>
 */
public class ToastUtil {

    private static Toast sToast;
    private static long sFirstTime;
    private static long sSecondTime;
    private static String sMessage;

    public static void show(int messageId) {
        show(BaseApplication.getContext().getString(messageId));
    }

    public static void show(String message) {
        if (sToast == null) {
            sToast = Toast.makeText(BaseApplication.getContext(), message, Toast.LENGTH_SHORT);
            sToast.show();
            sFirstTime = System.currentTimeMillis();
        } else {
            sSecondTime = System.currentTimeMillis();
            if (message.equals(sMessage)) {
                if (sSecondTime - sFirstTime > Toast.LENGTH_SHORT) {
                    sToast.show();
                }
            } else {
                sMessage = message;
                sToast.setText(message);
                sToast.show();
            }
        }
        sFirstTime = sSecondTime;
    }
}
