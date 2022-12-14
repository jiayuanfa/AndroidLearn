package com.example.androidlearn.animation.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * 竖直滑动的View里面添加的水平滑动的View
 */
public class ItemHorizontalScrollerView extends ViewGroup {

    /**
     * 平滑滑动用
     */
    private Scroller scroller;
    /**
     * 最后一次事件点击的位置
     */
    private float lastX, lastY;
    /**
     * 布局的宽度 和  内容的宽度
     */
    private int mWidth, mContentWidth;

    public ItemHorizontalScrollerView(Context context) {
        this(context, null);
    }

    public ItemHorizontalScrollerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemHorizontalScrollerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scroller = new Scroller(context);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    /**
     * 测量
     * 为什么要重新测量
     * 是因为动态添加了View
     * 导致需要重新测量一遍每个子View的大小，这样才能决定父控件的大小
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        mWidth = widthSize;
        mContentWidth = 0;
        int contentMaxHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
            int cWidth = layoutParams.getMarginEnd() + layoutParams.getMarginStart() + child.getMeasuredWidth();
            int cHeight = layoutParams.topMargin + layoutParams.bottomMargin + child.getMeasuredHeight();
            contentMaxHeight = Math.max(cHeight, contentMaxHeight);
            mContentWidth += cWidth;
        }
        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : mContentWidth,
                heightMode == MeasureSpec.EXACTLY ? heightSize : contentMaxHeight);
    }

    /**
     * 为什么要重新布局
     * 因为添加了一堆View，如果不指定怎么布局，那么就布局没有规则。默认规则不满足我们的视觉需求
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            MarginLayoutParams layoutParams = (MarginLayoutParams) view.getLayoutParams();
            // 从左到右依次布局
            view.layout(left + layoutParams.leftMargin, layoutParams.topMargin,
                    left + layoutParams.leftMargin + view.getMeasuredWidth(),
                    layoutParams.topMargin + view.getMeasuredHeight());
            left += view.getWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
        }
    }


    // **********************************滑动冲突处理代码*********************************//

    /**
     * 以下是滑动冲突处理相关代码
     * 滑动规则：在滑动内部可以水平滑动的子View时，先让内部的子View水平滑动，当滑动到 最左边 或者 左右边的时候，再把事件交给上层去处理。
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        int scrollX = getScrollX();
        boolean used = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                }
                used = true;
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = (int) (x - lastX);
                if (scrollX <= 0 && dx > 0) {
                    //在最左边并且左滑时
                    if (scrollX == 0) {
                        dx = 0;
                    } else {
                        dx += scrollX;
                    }
                } else if (scrollX + mWidth >= mContentWidth && dx < 0) {
                    //在最右边并且右滑时
                    if (scrollX + mWidth >= mContentWidth) {
                        dx = 0;
                    } else {
                        dx += scrollX + mWidth - mContentWidth;
                    }
                } else {
                    used = true;
                }

                //跟随手指滑动
                scrollBy(-dx, 0);

                //在不需要在左滑和右滑的时候 事件交给父控件处理
                if (dx == 0 && !used) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (scrollX < 0) {
                    smoothScrollBy(-scrollX);
                } else if (mContentWidth <= mWidth) {
                    smoothScrollBy(-scrollX);
                } else if (mContentWidth - scrollX < mWidth) {
                    smoothScrollBy(mContentWidth - scrollX - mWidth);
                } else {
                    //惯性滑动效果
                }
                break;
            default:
                break;
        }
        lastX = x;
        return used;
    }

    /**
     * 平滑滑动
     *
     * @param dx
     */
    private void smoothScrollBy(int dx) {
        scroller.startScroll(getScrollX(), 0, dx, 0, 500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
    }
}
