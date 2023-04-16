package com.hongri.recyclerview.utils;

import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;

/**
 * @author：zhongyao
 * @date：2023/4/12
 * @description：
 * @reference：http://gityuan.com/2019/01/13/arraymap/
 */
public class ArrayUtil {
    private static final String TAG = "ArrayUtil";

    /**
     * SparseArray的优点：
     * 1、因为它避免了存取元素时的装箱和拆箱。
     * 2、压缩的方式来表示稀疏数组的数据，从而节约内存空间。
     *
     * SparseArray的缺点：
     * 1、执行效率低，特别当数据量大的时候【折半查找】--但对于个数小于1000的情况下，性能基本没有明显差异
     * 2、Key类型只能为Int类型
     *
     * SparseArray比ArrayMap节省1/3的内存，但SparseArray只能用于key为int类型的Map，所以int类型的Map数据推荐使用SparseArray。
     */
    public static void initSparseArray() {
        SparseArray<String> sparseArray = new SparseArray<>();
        sparseArray.append(1, "Android");
        sparseArray.append(3, "Java");

        SparseIntArray sparseIntArray = new SparseIntArray();
        sparseIntArray.append(5, 500);
        sparseIntArray.append(6, 600);

        SparseLongArray sparseLongArray = new SparseLongArray();
        sparseLongArray.append(7, 700L);
        sparseLongArray.append(8, 800L);

        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        sparseBooleanArray.append(9, false);
        sparseBooleanArray.append(10, true);

        Log.d(TAG, "sparseArray:" + sparseArray + " sparseIntArray:" + sparseIntArray + " sparseLongArray:" + sparseLongArray + " sparseBooleanArray:" + sparseBooleanArray);
    }


    /**
     * ArrayMap：
     * ArrayMap是在容量满的时机触发容量扩大至原来的1.5倍，在容量不足1/3时触发内存收缩至原来的0.5倍，更节省的内存扩容机制。
     * 而HashMap是在容量的0.75倍时触发容量扩大至原来的2倍，且没有内存收缩机制。
     */
    public static void initArrayMap() {
        ArrayMap<Integer, String> arrayMap = new ArrayMap<>();
        arrayMap.put(1, "Android");
        arrayMap.put(3, "Java");

        ArrayMap<String, String> arrayMapStr = new ArrayMap<>();
        arrayMapStr.put("name", "Jordon");
        arrayMapStr.put("city", "Chicago");
    }

}
