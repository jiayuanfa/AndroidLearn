package com.example.androidlearn.network;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearn.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**(
 * 基本网络请求以及JSON数据解析
 * HttpURLConnection的使用
 * JSONObject的使用
 */
public class NetworkJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_http_net);

        TextView weatherTv = findViewById(R.id.textView12);
        findViewById(R.id.button5).setOnClickListener(view -> {
            // 网络请求
            String url = "http://www.weather.com.cn/data/sk/101010100.html";
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url1 = new URL(url);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection();
                        httpURLConnection.setRequestMethod("GET");

                        // 拿到输入流
                        InputStream inputStream = httpURLConnection.getInputStream();

                        // 一行行读取出来 拼接结果
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder responseStringBuilder = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            responseStringBuilder.append(line);
                        }

                        // 解析JSON
                        try {
                            JSONObject jsonObject = new JSONObject(responseStringBuilder.toString());
                            JSONObject weatherObject = jsonObject.getJSONObject("weatherinfo");
                            String city = weatherObject.getString("city");
                            String temp = weatherObject.getString("temp");
                            String WD = weatherObject.getString("WD");
                            String text = city + temp + WD;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    weatherTv.setText(text);
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        });
    }
}
