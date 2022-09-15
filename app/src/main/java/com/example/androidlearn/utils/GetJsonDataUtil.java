package com.example.androidlearn.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GetJsonDataUtil {

    /**
     * 本地JSON文件读取为 JsonString
     * @param context
     * @param fileName
     * @return
     */
    public static String getJson(Context context, String fileName) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            AssetManager assetsManager = context.getAssets();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(assetsManager.open(fileName)));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }

}
