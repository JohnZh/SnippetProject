package com.john.jrouter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dalvik.system.DexFile;

/**
 * Created by JohnZh on 2020/7/8
 *
 * <p>反射工具集合</p>
 */
public class ClassUtils {

    public static Set<String> getClassNameByPackage(Context context, final String packageName)
            throws PackageManager.NameNotFoundException {
        Set<String> classNames = new HashSet<>();
        List<String> paths = getSourcePaths(context);
        for (String path : paths) {
            DexFile dexfile = null;
            try {
                //加载 apk中的dex 并遍历 获得所有 packageName 的类
                dexfile = new DexFile(path);
                Enumeration<String> dexEntries = dexfile.entries();
                while (dexEntries.hasMoreElements()) {
                    String className = dexEntries.nextElement();
                    if (className.startsWith(packageName)) {
                        classNames.add(className);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (null != dexfile) {
                    try {
                        dexfile.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return classNames;
    }

    private static List<String> getSourcePaths(Context context) throws PackageManager.NameNotFoundException {
        ApplicationInfo applicationInfo = context.getPackageManager()
                .getApplicationInfo(context.getPackageName(), 0);

        List<String> sourcePaths = new ArrayList<>();
        sourcePaths.add(applicationInfo.sourceDir);

        //instant run
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (null != applicationInfo.splitSourceDirs) {
                sourcePaths.addAll(Arrays.asList(applicationInfo.splitSourceDirs));
            }
        }

        return sourcePaths;
    }
}
