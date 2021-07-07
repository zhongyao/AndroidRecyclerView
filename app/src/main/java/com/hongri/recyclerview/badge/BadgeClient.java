package com.hongri.recyclerview.badge;

import android.content.Context;
import android.os.Build;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by zhongyao on 2021/5/27
 * Description: 应用角标适配类
 */
public class BadgeClient {
    private static Map<String, IBadgeStrategy> strategyMap = new HashMap<>();

    public static void setBadgeCount(Context context, Class<?> cls, int count) {
        IBadgeStrategy strategy = createStrategyInternal();
        if (strategy != null) {
            strategy.setBadgeCount(context, cls, count);
        }
    }

    private static IBadgeStrategy createStrategyInternal() {
        String strBrand = Build.BRAND.toLowerCase();
        IBadgeStrategy strategy = strategyMap.get(strBrand);
        if (strategy != null) {
            return strategy;
        }

        if ("xiaomi".equalsIgnoreCase(strBrand) || "redmi".equalsIgnoreCase(strBrand) || "blackshark".equalsIgnoreCase(strBrand)) {
            strategy = new XiaoMiBadgeStrategy();
        } else if ("huawei".equalsIgnoreCase(strBrand) || "honor".equalsIgnoreCase(strBrand)) {
            strategy = new HuaweiBadgeStrategy();
        } else if ("oppo".equalsIgnoreCase(strBrand)) {
            strategy = new OppoBadgeStrategy();
        } else if ("vivo".equalsIgnoreCase(strBrand)) {
            strategy = new VivoBadgeStrategy();
        } else if ("lenovo".equalsIgnoreCase(strBrand)) {
            strategy = new LenovoBadgeStrategy();
        } else if ("htc".equalsIgnoreCase(strBrand)) {
            strategy = new HtcBadgeStrategy();
        } else if ("samsung".equalsIgnoreCase(strBrand)) {
            strategy = new SamsungBadgeStrategy();
        } else if ("sony".equalsIgnoreCase(strBrand)) {
            strategy = new SonyBadgeStrategy();
        } else {
            strategy = new DefaultBadgeStrategy();
        }

        strategyMap.put(strBrand, strategy);

        return strategy;
    }
}
