package com.john.jrouter;

import android.app.Activity;
import android.content.Context;

import com.john.jrouter.annotation.Constants;

import java.util.Set;

import androidx.fragment.app.Fragment;

/**
 * Created by JohnZh on 2020/7/8
 *
 * <p>登记中心</p>
 */
public class Register {

    public static void register(String path, Class clazz) {
        RouteType routeType = getRouteType(clazz);
        RouteRecord record = new RouteRecord(routeType, clazz, path);
        RouteTables.sMap.put(path, record);
    }

    private static RouteType getRouteType(Class clazz) {
        if (Activity.class.isAssignableFrom(clazz)) {
            return RouteType.ACTIVITY;
        } else if (clazz.getCanonicalName().contains(Fragment.class.getSimpleName())) {
            return RouteType.FRAGMENT;
        }
        return RouteType.CLASS;
    }

    public static RouteRecord findRecord(RoutePath msg) {
        return RouteTables.sMap.get(msg.path);
    }

    public static void init(Context appContext) {
        try {
            Set<String> classNameSet = ClassUtils.getClassNameByPackage(appContext, Constants.packageName);
            for (String className : classNameSet) {
                Class clazz = Class.forName(className);
                if (IRegister.class.isAssignableFrom(clazz)) {
                    IRegister register = (IRegister) clazz.getConstructor().newInstance();
                    register.onRegister();
                }
            }
        } catch (Exception e) {
            // not such package, util class not exist
            e.printStackTrace();
        }
    }
}
