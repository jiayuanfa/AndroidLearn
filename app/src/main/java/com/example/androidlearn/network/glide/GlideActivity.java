package com.example.androidlearn.network.glide;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.androidlearn.R;

import java.io.File;
import java.io.FileInputStream;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Glide 用法解析
 * <p>
 * ## 源码分析
 * <p>
 * ### with() Glide调用
 * ①获取一个RequestManager对象,Glide会根据我们传入with()方法的参数来确定图片加载的生命周期.\
 * ②一种是Application类型的参数，另一种是传入非Application类型的参数,第一种直接跟应用程序的生命周期同步,第二种是会向当前的Activity当中添加一个隐藏的Fragment,通过监听Fragment的生命周期来跟Activity生命周期同步,来控制图片加载与否\
 * ③在非主线程中使用Glide,都会强制使用Application来处理\
 * <p>
 * ### load()
 * 以图片Url字符串为例 RequestManager调用\
 * ①loadGeneric() ModelLoader对象是用于加载图片的，而我们给load()方法传入不同类型的参数，这里也会得到不同的ModelLoader对象.由于我们刚才传入的参数是String.class，因此最终得到的是StreamStringLoader对象，它是实现了ModelLoader接口的.\
 * ②返回一个DrawableTypeRequest对象\
 * ③DrawableTypeRequest中的asBitmap()和asGif(),它们分别又创建了一个BitmapTypeRequest和GifTypeRequest，如果没有进行强制指定的话，那默认就是使用DrawableTypeRequest。
 * <p>
 * ### into()
 * DrawableTypeRequest的父类DrawableRequestBuilder调用 具体实现在DrawableRequestBuilder的父类GenericRequestBuilder中\
 * ①构建一个Target对象,用来最终展示图片\
 * 根据参数构建不同的Target,如果调用了asBitmap()方法，就会构建出BitmapImageViewTarget对象，否则构建的是GlideDrawableImageViewTarget对象\
 * ②buildRequest()方法构建出了一个Request对象,根据之前的load方法中调用的API来组装这个Request对象\
 * ③执行这个Request\
 * 判断Glide当前是不是处于暂停状态,不是暂停调用Request的begin(),否则添加到待执行队列中\
 * ④begin() GenericRequest调用\
 * 图片URL地址为空,直接使用占位图\
 * 不为空,如果使用了override()为图片指定了一个固定的宽高，一种是没有指定。指定了的话,调用onSizeReady()方法。没指定的话，调用target.getSize()方法。这个target.getSize()方法的内部会根据ImageView的layout_width和layout_height值做一系列的计算，来算出图片应该的宽高,最终都会调用到onSizeReady()方法\
 * ⑤onSizeReady() GenericRequest调用\
 * EngineRunnable的run()--&gt;HttpUrlFetcher中的loadData方法具体的请求得到一个InputStream--&gt;之后再一层层封装得到Resource&lt;GlideDrawable&gt;—&gt; GlideDrawableImageViewTarget的setResource设置上图片
 */
public class GlideActivity extends AppCompatActivity {

    private ImageView mImageView;

    private ImageView mWrapImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_glide);

        mImageView = findViewById(R.id.mIv);
        mWrapImageView = findViewById(R.id.mIv2);

//        glideLoadUrl();   // url图片加载
//        glidePreloadUrl();  // 图片预加载
//        mImageView.setOnClickListener(view -> {
//            showImage();
//        });

//        glideImageDownload();   // 图片下载

        glideImageTransform();   // 图片转换功能

//        glideLoadLocalImage();
//        glideLoadResImage();
//        glideLoadImageBytes();
//        glideLoadImageUri();
    }

    /**
     * Glide 默认对图片的ScaleType进行了FIT_CENTER操作
     * 这样会导致图片被放大，而不是图片本身的大小
     * 那么如果我们就需要原图大小显示怎么办呢？
     * <p>
     * 1：可以禁用掉图片转换操作
     * 2：可以使用原图展示
     * <p>
     * 图片变换的核心步骤
     * 1：校验，如果原图为空，或者原图的尺寸和目标裁剪尺寸相同，那么就放弃裁剪
     * 2：通过数学计算来算出画布的缩放的比例以及偏移值
     * 3：判断缓存池中取出的Bitmap对象是否为空，如果不为空就可以直接使用，如果为空则要创建一个新的Bitmap对象
     * 4：将原图Bitmap对象的alpha值复制到裁剪Bitmap对象上面
     * 5：裁剪Bitmap对象进行绘制，并将最终的结果进行返回
     */
    private void glideImageTransform() {
        String url = "https://www.baidu.com/img/bd_logo1.png";
//        Glide.with(this)
//                .load(url)
//                .into(mWrapImageView);

//        Glide.with(this)
//                .load(url)
//                .dontTransform()
//                .into(mWrapImageView);

        // 实测该方法比较靠谱 原图展示
//        Glide.with(this)
//                .load(url)
//                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
//                .into(mWrapImageView);

        // 中心裁剪效果
        String url1 = "http://cn.bing.com/az/hprichbg/rb/AvalancheCreek_ROW11173354624_1920x1080.jpg";
//        Glide.with(this)
//                .load(url1)
//                .override(500, 500)
//                .centerCrop()
//                .into(mWrapImageView);

        /**
         * 自定义图片变换
         * 圆角化、圆形化、黑白化、模糊化等等，甚至你将原图片完全替换成另外一张图
         * 自定义一个类让它继承自BitmapTransformation ，
         * 然后重写transform()方法，
         * 并在这里去实现具体的图片变换逻辑就可以了
         */
//        Glide.with(this)
//                .load(url1)
//                .override(500, 500)
//                .transform(new CircleCrop())
//                .into(mWrapImageView);

        /**
         * 图片模糊
         */
//        Glide.with(this)
//                .load(url1)
//                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
//                .apply(RequestOptions.bitmapTransform(new BlurTransformation()))
//                .into(mImageView);

        /**
         * 模糊
         * 圆角
         * 黑白
         */
        MultiTransformation<Bitmap> mT = new MultiTransformation<>(
                new BlurTransformation(),
                new RoundedCornersTransformation(100, 0, RoundedCornersTransformation.CornerType.ALL),
                new GrayscaleTransformation()
        );
        Glide.with(this)
                .load(url1)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .apply(RequestOptions.bitmapTransform(mT))
                .into(mImageView);

    }

    /**
     * 图片下载
     */
    @SuppressLint("CheckResult")
    private void glideImageDownload() {
        String url = "http://cn.bing.com/az/hprichbg/rb/TOAD_ZH-CN7336795473_1920x1080.jpg";
        RequestManager requestManager = Glide.with(getApplicationContext());
        RequestBuilder<File> requestBuilder = requestManager.downloadOnly();
        requestBuilder.load(url);
        requestBuilder.listener(new RequestListener<File>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<File> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(File resource, Object model, Target<File> target, DataSource dataSource, boolean isFirstResource) {
                try {
                    FileInputStream fis = new FileInputStream(resource);
                    Bitmap bmp = BitmapFactory.decodeStream(fis);
                    mImageView.setImageBitmap(bmp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
        requestBuilder.preload();
    }

    /**
     * 图片预加载
     */
    private void glidePreloadUrl() {
        String imageUrl = "http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg";
        String gifUrl = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01639c586c91bba801219c77f6efc8.gif&refer=http%3A%2F%2Fimg.zcool.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1666841952&t=c70c81db892979e877998c37a117e2d1";
        Glide.with(this)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .preload();
    }

    /**
     * 图片显示
     */
    private void showImage() {
        String imageUrl = "http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg";
        Glide.with(this)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(mImageView);
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
     * <p>
     * 占位图：placeholder
     * 只会在第一次加载的时候能看到
     * <p>
     * 缓存策略：diskCacheStrategy(DiskCacheStrategy.NONE)
     * 后面因为图片被缓存了，所以看不到了
     * 我们可以改变一下缓存策略，这样每次加载的时候就能看到图片的占位图了
     * <p>
     * 网络异常占位图：Error
     * <p>
     * 指定图片格式：asBitmap asGif等 比如指定了asBitmap 那么加载出来的图片都是静态的了，包括Gif
     * <p>
     * 强制指定图片大小：.override(100, 100)
     * <p>
     * skipMemoryCache: 是否禁用Glide的内存缓存功能
     */
    private void glideLoadUrl() {

        String imageUrl = "http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg";
        String gifUrl = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01639c586c91bba801219c77f6efc8.gif&refer=http%3A%2F%2Fimg.zcool.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1666841952&t=c70c81db892979e877998c37a117e2d1";
        Glide.with(this)
                .load(imageUrl)
                .error(R.drawable.compose_bg)
                .placeholder(R.mipmap.ic_launcher)
                .into(mImageView);
    }
}
