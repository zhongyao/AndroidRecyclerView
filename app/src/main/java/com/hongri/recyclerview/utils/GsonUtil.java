package com.hongri.recyclerview.utils;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @author：hongri
 * @date：11/25/22
 * @description：
 */
public class GsonUtil {
    private static final String TAG = "GsonUtil";

    public static void hashSetToJson(HashSet<String> hashSet) {
        Gson gson = new Gson();
        String jsonObject = gson.toJson(hashSet);

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "jsonObject:" + jsonObject + " jsonArray:" + jsonArray);
    }
}
