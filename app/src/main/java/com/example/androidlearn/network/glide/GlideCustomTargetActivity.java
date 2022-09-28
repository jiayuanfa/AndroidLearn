package com.example.androidlearn.network.glide;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapThumbnailImageViewTarget;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.androidlearn.R;

import java.io.File;

/**
 * Glide 用法解析
 *
 * SimpleTarget
 */
public class GlideCustomTargetActivity extends AppCompatActivity {

    private ImageView mImageView;

    CustomTarget<Bitmap> customTarget = new CustomTarget<Bitmap>() {
        @Override
        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
            System.out.println("onResourceReady:" + resource.getWidth() + "/" + resource.getHeight());
            mImageView.setImageBitmap(resource);
        }

        @Override
        public void onLoadCleared(@Nullable Drawable placeholder) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_glide);

        mImageView = findViewById(R.id.mIv);

        glideLoadUrl();
    }

    /**
     * 加载Uri图片
     */
    private void glideLoadImageUri() {
        Uri imageUri = getImageUri();
        Glide.with(this).load(imageUri).into(mImageView);
    }

    private Uri getImageUri() {
        return null;
    }

    /**
     * 加载二进制流图片
     */
    private void glideLoadImageBytes() {
        byte[] imageBytes = getImageBytes();
        Glide.with(this).load(imageBytes).into(mImageView);
    }

    private byte[] getImageBytes() {
        return new byte[10];
    }

    /**
     * 加载应用资源
     */
    private void glideLoadResImage() {
        // 加载应用资源
        int resource = R.drawable.play;
        Glide.with(this).load(resource).into(mImageView);
    }

    /**
     * Glide加载本地图片
     * // getExternalCacheDir 获取的是缓存目录 /storage/emulated/0/Android/data/com.example.androidlearn/cache
     * /storage/emulated/0/Android/data/com.example.androidlearn/cache/pictures/锁屏壁纸/girl.jpeg
     */
    private void glideLoadLocalImage() {
        File file = new File(getExternalCacheDir() + "/pictures/锁屏壁纸/girl.jpeg");
        Glide.with(this).load(file).into(mImageView);
    }

    /**
     * Glide流程
     * 1：加载网络上的图片
     * 2：加载手机本地图片
     * 3：加载应用资源的图片
     * Glide支持加载各种各样的图片资源，包括网络图片、本地图片、应用资源、二进制流、Uri对象
     *
     * 占位图：placeholder
     * 只会在第一次加载的时候能看到
     *
     * 缓存策略：diskCacheStrategy(DiskCacheStrategy.NONE)
     * 后面因为图片被缓存了，所以看不到了
     * 我们可以改变一下缓存策略，这样每次加载的时候就能看到图片的占位图了
     *
     * 网络异常占位图：Error
     *
     * 指定图片格式：asBitmap asGif等 比如指定了asBitmap 那么加载出来的图片都是静态的了，包括Gif
     *
     * 强制指定图片大小：.override(100, 100)
     *
     * skipMemoryCache: 是否禁用Glide的内存缓存功能
     */
    private void glideLoadUrl() {

        String imageUrl = "http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg";
        String gifUrl = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01639c586c91bba801219c77f6efc8.gif&refer=http%3A%2F%2Fimg.zcool.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1666841952&t=c70c81db892979e877998c37a117e2d1";
        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .error(R.drawable.compose_bg)
                .placeholder(R.mipmap.ic_launcher)
                .into(customTarget);
    }
}
