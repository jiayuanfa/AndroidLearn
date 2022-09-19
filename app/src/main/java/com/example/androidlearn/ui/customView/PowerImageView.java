package com.example.androidlearn.ui.customView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.androidlearn.R;

import java.io.InputStream;
import java.lang.reflect.Field;

public class PowerImageView extends androidx.appcompat.widget.AppCompatImageView implements View.OnClickListener {

    /**
     * 播放GIF动画的关键类
     */
    private Movie mMovie;

    /**
     * 开始播放按钮图片
     */
    private Bitmap mStartButton;

    /**
     * 记录动画开始的时间
     */
    private long mMovieStart;

    /**
     * GIF图片的宽度
     */
    private int mImageWidth;

    /**
     * GIF图片的高度
     */
    private int mImageHeight;

    /**
     * 图片是否正在播放
     */
    private boolean isPlaying;

    /**
     * 是否允许自动播放
     */
    private boolean isAutoPlay;

    public PowerImageView(@NonNull Context context) {
        this(context, null);
    }

    public PowerImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * PowerImageView构造函数，在这里完成必要的初始化工作
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public PowerImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 获取设置的src资源
        int resourceId = getResourceId(context, attrs);

        if (resourceId != 0) {

            // 资源ID不能于空，去获取资源的流
            InputStream is = getResources().openRawResource(resourceId);

            // 使用Movie对流进行解码
            mMovie = Movie.decodeStream(is);

            if (mMovie != null) {

                // 开启硬件加速 解决某些手机不能播放gif的问题
//                this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

                // 返回值不等于空，说明这是个GIF图片，然后获取资源中是否自动播放的属性
                @SuppressLint("Recycle") TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PowerImageView);
                isAutoPlay = typedArray.getBoolean(R.styleable.PowerImageView_auto_play, false);

                // 获取GIF的bitmap，拿到宽高
//                Bitmap bitmap = BitmapFactory.decodeStream(is);
//                mImageWidth = bitmap.getWidth();
//                mImageHeight = bitmap.getHeight();
//                bitmap.recycle();

                if (!isAutoPlay) {
                    // 不允许自动播放，拿到开始播放的图片，并注册点击事件
                    mStartButton = BitmapFactory.decodeResource(getResources(), R.drawable.play);

                    // 给ImageView注册点击事件
                    setOnClickListener(this);
                }
            }
        }

    }

    /**
     * 获取到src指定图片资源所对应的id。
     *
     * @param context
     * @param attrs
     * @return 返回布局文件中指定图片资源所对应的id，没有指定任何图片资源就返回0。
     */
    private int getResourceId(Context context, AttributeSet attrs) {

        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            if (attrs.getAttributeName(i).equals("src")) {
                return attrs.getAttributeResourceValue(i, 0);
            }
        }
        return 0;
    }

    /**
     * 通过Java反射，获取到src指定图片资源所对应的id。
     *
     * @param a
     * @param context
     * @param attrs
     * @return 返回布局文件中指定图片资源所对应的id，没有指定任何图片资源就返回0。
     */
    private int getResourceId(TypedArray a, Context context, AttributeSet attrs) {
        try {
            Field field = TypedArray.class.getDeclaredField("mValue");
            field.setAccessible(true);
            TypedValue typedValueObject = (TypedValue) field.get(a);
            return typedValueObject.resourceId;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (a != null) {
                a.recycle();
            }
        }
        return 0;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == getId()) {
            // 当用户点击图片的时候，开始播放GIF动画
            isPlaying = true;
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mMovie == null) {
            // 空，则说明是一张普通图片，直接调用父类的onDraw()方法即可
            super.onDraw(canvas);
        } else {
            if (isAutoPlay) {
                playMovie(canvas);
                invalidate();
            } else {
                if (isPlaying) {

                    // 正在播放，就一直调用播放，直到播放结束
                    if (playMovie(canvas)) {
                        isPlaying = false;
                    }
                    invalidate();
                } else {

                    // 还没有开始播放，则只绘制GIF第一帧图片，并绘制一个播放按钮
                    mMovie.setTime(0);
                    mMovie.draw(canvas, 10, 100);
//                    int offsetW = getWidth() / 2 - mStartButton.getWidth() / 2;
//                    int offsetH = getHeight() / 2 - mStartButton.getHeight() / 2;
//                    canvas.drawBitmap(mStartButton, offsetW, offsetH, null);


                    int mBitWidth = mStartButton.getWidth();
                    int mBitHeight = mStartButton.getHeight();

                    // 图片大小
                    Rect mSrcRect = new Rect(0, 0, mBitWidth, mBitHeight);
                    int playButtonWH = 50;

                    // 计算左边位置
                    int left = getWidth() / 2 - playButtonWH;
                    // 计算上边位置
                    int top = getHeight() / 2 - playButtonWH;

                    // 绘制区域
                    Rect mDestRect = new Rect(left, top, getWidth() / 2 + playButtonWH, getHeight() / 2 + playButtonWH);
                    canvas.drawBitmap(mStartButton, mSrcRect, mDestRect,null);

                }
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mMovie != null) {
            // 如果是GIF, 需要重写设定其大小
            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        }
    }

    /**
     * 开始播放GIF动画，播放完成返回true，反之false
     * @param canvas
     * @return
     */
    private boolean playMovie(Canvas canvas) {
        long now = SystemClock.uptimeMillis();
        if (mMovieStart == 0) {
            mMovieStart = now;
        }
        int duration = mMovie.duration();
        if (duration == 0) {
            duration = 1000;
        }

        int relTime = (int) ((now - mMovieStart) % duration);
        mMovie.setTime(relTime);
        mMovie.draw(canvas, 10, 100);
        if ((now - mMovieStart) >= duration) {
            mMovieStart = 0;
            return true;
        }
        return false;
    }
}
