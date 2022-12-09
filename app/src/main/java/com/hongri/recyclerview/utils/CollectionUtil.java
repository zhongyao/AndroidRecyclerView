package com.hongri.recyclerview.utils;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.hongri.recyclerview.bean.Emp;
import com.hongri.recyclerview.bean.Emp2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

/**
 * @author：hongri
 * @date：12/7/22
 * @description：集合工具类：
 *
 * 1.对于String或Integer这些已经实现Comparable接口的类来说，可以直接使用Collections.sort方法传入list参数来实现默认方式（正序）排序；
 *
 * 2.如果不想使用默认方式（正序）排序，可以通过Collections.sort传入第二个参数类型为Comparator来自定义排序规则；
 *
 * 3.对于自定义类型(如本例子中的Emp)，如果想使用Collections.sort的方式一进行排序，可以通过实现Comparable接口的compareTo方法来进行，如果不实现，则参考第2点；
 *
 * 4.jdk1.8的Comparator接口有很多新增方法，其中有个reversed()方法比较实用，是用来切换正序和逆序的
 */
public class CollectionUtil {
    private static final String TAG = "CollectionUtil";

    /**
     * 排序:
     * isPositiveSequence: true-正序排序；false-逆序排序
     */
    public static void sortHashMap(boolean isPositiveSequence) {
        // 当所有key的hash的最大值<数组的长度-1时HashMap可以将存入的元素按照key的hash从小到大排序
        Log.d(TAG, "getHashMap():" + getHashMap());
        //
        Log.d(TAG, "getHashMap().entrySet():" + getHashMap().entrySet());
        List<Map.Entry<Integer, String>> list = new ArrayList(getHashMap().entrySet());

        Log.d(TAG, "排序前--list:" + list);
        Collections.sort(list, (o1, o2) -> {
            if (isPositiveSequence) {
                return o1.getKey() - o2.getKey();
            } else {
                return o2.getKey() - o1.getKey();
            }
        });

        Log.d(TAG, "排序后--list:" + list);

        HashSet<String> linkedHashSet = new LinkedHashSet<>();
        for (int i = 0; i < list.size(); i++) {
            linkedHashSet.add(list.get(i).getValue());
        }

        Log.d(TAG, "sortHashMapPositiveSequence --- linkedHashSet:" + linkedHashSet);
    }

    /**
     * List排序-方法1-正序:
     */
    public static void sortList() {
        ArrayList<Integer> list = getList();
        Log.d(TAG, "排序前====list:" + list);
        //正序排序
        Collections.sort(list);
        Log.d(TAG, "排序后======list:" + list);
    }

    /**
     * List排序-方法2-正序/逆序:
     * isPositiveSequence: true-正序排序；false-逆序排序
     */
    public static void sortList2(boolean isPositiveSequence) {
        ArrayList<Integer> list = getList();
        Log.d(TAG, "排序前====list:" + list);
        //正序排序
        Collections.sort(list, new Comparator<Integer>() {

            @Override
            public int compare(Integer o1, Integer o2) {
                // 返回值为int类型，大于0表示正序，小于0表示逆序
                Log.d(TAG, "o1:" + o1 + " o2:" + o2);
                if (isPositiveSequence) {
                    return o1 - o2;
                } else {
                    return o2 - o1;
                }
            }
        });
        Log.d(TAG, "排序后======list:" + list);
    }

    /**
     * Bean排序-方法1:
     * isPositiveSequence: true-正序排序；false-逆序排序
     */
    public static void sortBeanList(boolean isPositiveSequence) {
        List<Emp> list = getBeanList();
        Log.d(TAG, "排序前====list:" + list);
        //此处Emp应该实现Comparable即可
        Collections.sort(list);
        Log.d(TAG, "排序后======list:" + list);
    }

    /**
     * Bean排序-方法2:
     * @param isPositiveSequence
     */
    public static void sortBeanList2(boolean isPositiveSequence) {
        List<Emp2> list = getBeanList2();
        Log.d(TAG, "排序前====list:" + list);

        Comparator<Emp2> comparator = new Comparator<Emp2>() {
            @Override
            public int compare(Emp2 o1, Emp2 o2) {
                /*按员工编号正序排序*/
//                return o1.getEmpno() - o2.getEmpno();
                /*按员工编号逆序排序*/
                //return o2.getEmpno()-o1.getEmpno();
                /*按员工姓名正序排序*/
                return o1.getEname().compareTo(o2.getEname());
                /*按员工姓名逆序排序*/
                //return o2.getEname().compareTo(o1.getEname());
            }
        };
        //正序排序
        Collections.sort(list, comparator);
        //@RequiresApi(api = Build.VERSION_CODES.N)
//        Collections.sort(list, comparator.reversed());
        Log.d(TAG, "排序后======list:" + list);
    }

    private static List<Emp> getBeanList() {
        Emp emp1 = new Emp(2, "Guan YunChang");
        Emp emp2 = new Emp(3, "Zhang Fei");
        Emp emp3 = new Emp(1, "Liu Bei");
        return Arrays.asList(emp1, emp2, emp3);
    }

    private static List<Emp2> getBeanList2() {
        Emp2 emp1 = new Emp2(2, "Guan YunChang");
        Emp2 emp2 = new Emp2(3, "Zhang Fei");
        Emp2 emp3 = new Emp2(1, "Liu Bei");
        return Arrays.asList(emp1, emp2, emp3);
    }

    private static ArrayList<Integer> getList() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(1);
        list.add(0);
        return list;
    }

    private static HashMap<Integer, String> getHashMap() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(2, "ddd");
        map.put(3, "aaa");
        map.put(4, "ccc");
        map.put(1, "eee");
        map.put(0, "ggg");
        return map;
    }
}
