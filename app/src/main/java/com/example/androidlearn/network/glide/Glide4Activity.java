package com.example.androidlearn.network.glide;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.androidlearn.R;

/**
 * Glide4.0用法
 */
public class Glide4Activity extends AppCompatActivity {

    String url = "http://guolin.tech/book.png";
    String gifUrl = "http://guolin.tech/test.gif";
    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_glide);

        imageView = findViewById(R.id.mIv);

        findViewById(R.id.button6).setOnClickListener(view -> {

            /**
             * diskCacheStrategy(DiskCacheStrategy.NONE) 禁用掉硬盘缓存
             * skipMemoryCache()方法并传入true 禁用掉内存缓存的功能
             */
            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.compose_bg)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .override(200, 100);
            glideLoadUrlImageWithOptions(options);
        });

    }

    /**
     * Glide4.0
     * 通过options对建造者模式所需要的参数进行封装
     * 简化使用
     * @param options
     */
    private void glideLoadUrlImageWithOptions(RequestOptions options) {
        Glide.with(this)
                .load(gifUrl)
                .apply(options)
                .into(imageView);
    }

    /**
     * 回调与监听
     * @param options
     */
    private void glideSimpleTarget(RequestOptions options) {

        Glide.with(this)
                .load(gifUrl)
                .apply(options)
                .into(imageView);
    }
}
