package com.john.bindlib;

public class BindLib {
    public static void bind(Object activity) {
        String canonicalName = activity.getClass().getCanonicalName();
        Class<?> utilClass = null;
        try {
            utilClass = Class.forName(canonicalName + "_Binder");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (utilClass != null) {
            try {
                Object o = utilClass.newInstance();
                if (o instanceof IBinder) {
                    ((IBinder) o).bind(activity);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }
}
