package com.example.androidlearn.network;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearn.R;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * OkHttp
 */
public class OkHttpActivity extends AppCompatActivity {

    private TextView mTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_empty);

        mTv = findViewById(R.id.mTv);
        okHttpGet();
    }

    /**
     * OkHttp GET 请求
     */
    private void okHttpGet() {
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建一个Request
        final Request request = new Request.Builder()
                .url("https://github.com/hongyangAndroid")
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                System.out.println("okHttpGet onFailure :" + e.toString());
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                System.out.println("okHttpGet onResponse thread:" + Thread.currentThread());
                String htmlStr =  response.body().string();
                System.out.println("okHttpGet onResponse :" + htmlStr.substring(0, 10));
                runOnUiThread(() -> {
                    mTv.setText(htmlStr.substring(0, 100));
                });
            }
        });
    }
}
