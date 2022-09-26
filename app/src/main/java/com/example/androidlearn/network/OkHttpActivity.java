package com.example.androidlearn.network;

import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearn.R;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
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
        okHttpGet2();
//        okHttpPost();
        okHttpFileUpload();
        try {
            okHttpFileUpload2();
        } catch (IOException e) {
            e.printStackTrace();
        }
        okHttpFileDownload();
        okHttpDisplayImage();
    }

    private void okHttpFileUpload2() throws IOException {

        File file = new File(Environment.getExternalStorageDirectory(), "balabala.mp4");
        OkHttpClientManager.postAsyn("http://192.168.1.103:8080/okHttpServer/fileUpload",//
                new OkHttpClientManager.ResultCallback<String>() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String result) {

                    }
                },//
                file,//
                "mFile",//
                new OkHttpClientManager.Param[]{
                        new OkHttpClientManager.Param("username", "zhy"),
                        new OkHttpClientManager.Param("password", "123")}
        );

    }

    private void okHttpDisplayImage() {
        OkHttpClientManager.displayImage(new ImageView(this),
                "http://images.csdn.net/20150817/1.jpg");
    }

    private void okHttpFileDownload() {
        OkHttpClientManager.downloadAsyn(
                "http://192.168.1.103:8080/okHttpServer/files/messenger_01.png",
                Environment.getExternalStorageDirectory().getAbsolutePath(),
                new OkHttpClientManager.ResultCallback<String>() {

                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        //文件下载成功，这里回调的reponse为文件的absolutePath
                    }
                });
    }

    private void okHttpGet2() {
        OkHttpClientManager.getAsyn("",
                new OkHttpClientManager.ResultCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(Object response) {

                    }
                }
        );
    }

    /**
     * OkHttp file upload
     * 文件上传
     */
    private void okHttpFileUpload() {
        File file = new File(
                Environment.getExternalStorageDirectory(),
                "balabala.mp4");
        RequestBody fileBody = RequestBody.create(
                MediaType.parse("application/octet-stream"),
                file);

        RequestBody requestBody = new MultipartBuilder()
                .type(MultipartBuilder.FORM)
                .addPart(Headers.of(
                                "Content-Disposition",
                                "form-data; name=\"username\""),
                        RequestBody.create(null, "贾元发"))
                .addPart(Headers.of(
                                "Content-Disposition",
                                "form-data; name=\"mFile\"; filename=\"wjd.mp4\""),
                        fileBody)
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.1.103:8080/okHttpServer/fileUpload")
                .post(requestBody)
                .build();

        OkHttpClient mOkHttpClient = new OkHttpClient();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

            }
        });
    }

    /**
     * OkHttp POST 请求
     */
    private void okHttpPost() {
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
//        Request request = buildMultipartFormRequest(
//                url, new File[]{file}, new String[]{fileKey}, null);
        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add("username", "张鸿洋");

        Request request = new Request.Builder()
                .url("https://github.com/hongyangAndroid")
                .post(builder.build())
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

            }
        });
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
                String htmlStr = response.body().string();
                System.out.println("okHttpGet onResponse :" + htmlStr.substring(0, 10));
                runOnUiThread(() -> {
                    mTv.setText(htmlStr.substring(0, 100));
                });
            }
        });
    }
}
