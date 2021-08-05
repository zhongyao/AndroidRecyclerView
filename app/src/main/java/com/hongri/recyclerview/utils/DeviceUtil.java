package com.hongri.recyclerview.utils;

import java.lang.reflect.Method;

/**
 * Create by zhongyao on 2021/8/5
 * Description:
 */
public class DeviceUtil {

    private static final String HARMONY_OS = "harmony";

    /**
     * 判断是否是鸿蒙系统
     * @return
     */
    public static boolean isHarmonyOS() {
        try {
            Class clz = Class.forName("com.huawei.system.BuildEx");
            Method method = clz.getMethod("getOsBrand");

            ClassLoader classLoader = clz.getClassLoader();

            //如果BuildEx为系统提供的，其classloader为BootClassLoader
            //如果BuildEx为伪造的，其classloader一般为PathClassLoader
            System.out.println("classLoader: " + classLoader);

            //BootClassLoader的parent为null
            if (classLoader != null && classLoader.getParent() == null) {
                return HARMONY_OS.equals(method.invoke(clz));
            }
        } catch (ClassNotFoundException e) {
        } catch (NoSuchMethodException e) {
        } catch (Exception e) {
        }
        return false;
    }
}
