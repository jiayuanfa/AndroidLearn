package com.example.androidlearn.animation.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * 可以水平滚动的View
 */
public class HorizontalScrollerView extends ViewGroup {

    private Scroller scroller;  // 平滑滚动器
    private VelocityTracker velocityTracker;    // 滑动速度计算
    private float x, y, lastX, lastY, lastInterceptX, lastInterceptY;   // 记录上一次触摸时间的位置
    private int mChildWidth, mChildHeight, mChildSize;  // 子控件的宽高、个数
    private OnChangeListener onChangeListener;  // 当前的子View索引变化
    private int childIndex = 0; // 当前的子View索引

    public HorizontalScrollerView(Context context) {
        super(context);
    }

    public HorizontalScrollerView(Context context, AttributeSet attrs) {
//        super(context, attrs);
        this(context, attrs, 0);
    }

    public HorizontalScrollerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化滚动器
     * 初始化滑动速度计算器
     * @param context
     */
    private void init(Context context) {
        scroller = new Scroller(context);
        velocityTracker = VelocityTracker.obtain();
    }

    /**
     * 设置子View索引变化回调
     */
    public void setOnChangeListener(OnChangeListener onChangeListener) {
        this.onChangeListener = onChangeListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        mChildSize = getChildCount();
        for (int i = 0; i < mChildSize; i++) {

            // 默认都设置一样大 占满父布局
            measureChild(getChildAt(i), widthSize, heightSize);
        }

        mChildWidth = widthSize;
        mChildHeight = heightSize;

        // 设置测量宽高
        setMeasuredDimension(widthSize, heightSize);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);

            // 左上右下
            /**
             * 从左到右依次排列
             */
            view.layout(left, 0, left + mChildWidth, mChildHeight);
            left += mChildWidth;
        }
    }

    /**
     * 事件分发的时候拿到触摸点
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        x = ev.getX();
        y = ev.getY();

        lastX = x;
        lastY = y;

        boolean res = super.dispatchTouchEvent(ev);
        return res;
    }

    /**
     * 外部拦截法
     * 计算手指在屏幕上滑动的X轴距，Y轴距
     * 如果x > y ，则拦截事件，左右滑动，反之，不拦截
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        /**
         * 以下代码为内部拦截法
         * 也就是父控件在down的时候不拦截
         * 其余时候拦截
         * 子控件在move的时候，判断是否阻止父控件拦截
         */
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                if (!scroller.isFinished()) {
//                    scroller.abortAnimation();
//                    return true;
//                }
//                return false;
//            case MotionEvent.ACTION_MOVE:
//            case MotionEvent.ACTION_UP:
//            default:
//                return true;
//        }

        /**
         * 以下代码为外部拦截法
         * 为什么外部拦截法，不需要子控件去做什么就能拦截呢？
         * 就是因为外部拦截法，比较简单粗暴。不需要经过协商，而内部拦截法则需要递归。
         */
        boolean intercept;
        float x = ev.getX();
        float y = ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                    intercept = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = x - lastInterceptX;
                float dy = y - lastInterceptY;
                // 水平滑动距离大于竖直滑动
                intercept = Math.abs(dx)  > Math.abs(dy) + 10;
                break;
            case MotionEvent.ACTION_UP:
            default:
                intercept = false;
                break;
        }
        lastX = x;
        lastY = y;

        lastInterceptX = x;
        lastInterceptY = y;
        return intercept;
    }

    /**
     * 在事件处理的时候通过判断滑动速度来移动内容View
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        velocityTracker.addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                }
                break;

            case MotionEvent.ACTION_MOVE:
                int dx = (int)(x - lastX);
                // 跟随手指滑动 因为正左，负右  所有要加个负值
                scrollBy(-dx, 0);
                break;

            /**
             * 手指抬起的时候，计算一下滑动速度
             * 以保证手指松开之后 继续滑动一段距离
             */
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                // 计算1S内的速度
                velocityTracker.computeCurrentVelocity(1000);
                // 获取水平的滑动速度
                float xVelocity = velocityTracker.getXVelocity();

                // 如果水平滑动距离大于100  则直接向左或者向右移动一个子View
                if (Math.abs(xVelocity) > 100) {
                    childIndex = xVelocity > 0 ? childIndex - 1 : childIndex + 1;
                } else {
                    // 反之，则移动到当前移动距离+子View宽度一半的定位的子View位置
                    childIndex = (scrollX + mChildWidth / 2) / mChildWidth;
                }

                // 防止数组越界
                childIndex = Math.max(0, Math.min(childIndex, mChildSize - 1));

                if (onChangeListener != null) {
                    onChangeListener.indexChange(childIndex);
                }

                // 根据index计算还需滑动到整个child的偏移
                int sx = childIndex * mChildWidth - scrollX;

                // 通过Scroller来平滑移动
                smoothScrollBy(sx);

                break;

            default:
                break;
        }
        return true;
    }

    /**
     * 通过Scroller平滑移动
     * @param sx
     */
    private void smoothScrollBy(int sx) {

        // 从手指拿起的点开始，即为View滑动开始的点，也就是getScrollX
        scroller.startScroll(getScrollX(), 0, sx, 0, 500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
    }

    /**
     * 供外界使用的滑动方法
     * 更新当前可见子View的索引
     */
    public void updateChildIndex(int pos) {
        if (!scroller.isFinished()) {
            scroller.abortAnimation();
        }
        smoothScrollBy(pos * mChildWidth - getScrollX());
        if (onChangeListener != null) {
            onChangeListener.indexChange(pos);
        }
    }

    public interface OnChangeListener {
        /**
         * 索引变化
         *
         * @param index 索引
         */
        void indexChange(int index);
    }
}
