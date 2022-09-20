package com.example.androidlearn.dataSave;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearn.R;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * 最近访问的最后输出，那么这就正好满足的LRU缓存算法的思想。
 * 可见LruCache巧妙实现，就是利用了LinkedHashMap的这种数据结构。
 */
public class LruCacheJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_empty);

        linkedHashMapTest();
    }

    /**
     * 打印结果
     * 分析：最近使用过的数据最后打印，利用这个原理
     * 进行LruCache设计，就能做到最近使用过的缓存，最后清理。
     * 2022-09-20 17:43:31.551 18523-18523/com.example.androidlearn I/System.out: 0:0
     * 2022-09-20 17:43:31.551 18523-18523/com.example.androidlearn I/System.out: 3:3
     * 2022-09-20 17:43:31.551 18523-18523/com.example.androidlearn I/System.out: 4:4
     * 2022-09-20 17:43:31.551 18523-18523/com.example.androidlearn I/System.out: 5:5
     * 2022-09-20 17:43:31.551 18523-18523/com.example.androidlearn I/System.out: 6:6
     * 2022-09-20 17:43:31.551 18523-18523/com.example.androidlearn I/System.out: 1:1
     * 2022-09-20 17:43:31.551 18523-18523/com.example.androidlearn I/System.out: 2:2
     */
    private void linkedHashMapTest() {
        LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>(0, 0.75f, true);
        map.put(0, 0);
        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        map.put(4, 4);
        map.put(5, 5);
        map.put(6, 6);
        map.get(1);
        map.get(2);
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

    public static final void main(String[] args) {
        LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>(0, 0.75f, true);
        map.put(0, 0);
        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        map.put(4, 4);
        map.put(5, 5);
        map.put(6, 6);
        map.get(1);
        map.get(2);
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }
}
