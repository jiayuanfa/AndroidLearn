package com.example.androidlearn.animation;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 平滑移动的方法
 * Scroller
 */
public class TestScroller extends androidx.appcompat.widget.AppCompatTextView {

    private Scroller mScroller;

    public TestScroller(@NonNull Context context) {
        super(context);
    }

    public TestScroller(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
    }


    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    /**
     * 平滑移动的方法
     * @param destX
     * @param destY
     */
    public void smoothScrollTo(int destX, int destY) {
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        int deltaX = destX - scrollX;
        int deltaY = destY - scrollY;
        mScroller.startScroll(scrollX, scrollY, deltaX, deltaY, 1000);
        invalidate();
    }
}
