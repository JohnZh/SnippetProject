package com.john.newtest.hotfix;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JohnZh on 2020/6/23
 *
 * <p>热修复工具类</p>
 * <p>生成补丁 dex 文件步骤: </p>
 * <p>把修复后的 .class 打成 .jar 文件：jar cvf patch.jar com/john/newtest/hotfix/Calculator.class， 执行完后 class 目录下回有 patch.jar 文件</p>
 * <p>使用 dx 工具把 .jar 转成 .dex, dx 工具位置：/Users/john/Library/Android/sdk/build-tools/28.0.3/dx</p>
 * <p>./dx --dex --output=patch.dex ~/Desktop/patch.jar 移动 patch.dex 到桌面</p>
 * <p>发送 patch.dex 到 sdcard：adb push patch.dex /sdcard</p>
 */
public class HotFix {
    public static void fix(File dexFile, File optPath, ClassLoader classLoader) {
        Class clazz = classLoader.getClass();
        Field pathListField = null;
        while (clazz != null) {
            try {
                pathListField = clazz.getDeclaredField("pathList");
                pathListField.setAccessible(true);
                break;
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            clazz = clazz.getSuperclass();
        }

        if (pathListField != null) {
            try {
                Object pathList = pathListField.get(classLoader);
                Class<?> pathListClass = pathList.getClass();

                Method makeDexElementsMethod = pathListClass.getDeclaredMethod("makeDexElements",
                        List.class, File.class, List.class, ClassLoader.class);
                makeDexElementsMethod.setAccessible(true);

                // 构造 makeDexElements 的入参
                List<File> patchFile = new ArrayList<>();
                patchFile.add(dexFile);
                List<IOException> exceptionList = new ArrayList<>();

                Object[] patch = (Object[]) makeDexElementsMethod
                        .invoke(patchFile, patchFile, optPath, exceptionList, classLoader);
                Field dexElementsFiled = pathListClass.getDeclaredField("dexElements");
                if (dexElementsFiled != null) {
                    dexElementsFiled.setAccessible(true);
                    Object[] dexElements = (Object[]) dexElementsFiled.get(pathList);

                    // 合并 赋值
                    if (dexElements.length > 0) {
                        Class<?> elementClass = dexElements[0].getClass();
                        Object[] newElements = (Object[]) Array.newInstance(elementClass, patch.length + dexElements.length);

                        System.arraycopy(patch, 0, newElements, 0, patch.length);
                        System.arraycopy(dexElements, 0, newElements, patch.length, dexElements.length);

                        dexElementsFiled.set(pathList, newElements);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }
}
