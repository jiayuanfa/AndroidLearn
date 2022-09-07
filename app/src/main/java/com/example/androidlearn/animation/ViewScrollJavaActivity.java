package com.example.androidlearn.animation;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearn.R;

/**
 * View滑动原理
 * 1：scrollTo 和 scrollBy 改变是 View 的什么属性
 * 2：补间动画和属性动画的使用？
 * 3：如何改变 View 的LayoutParams ？
 * 4：Scroller实现平滑滑动的原理？
 */
public class ViewScrollJavaActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_scroll);

        button = findViewById(R.id.mButton);

        button.setOnClickListener(view -> {
            scrollToAndScrollBy();
        });
    }

    /**
     * 示例一：scrollTo 和 scrollBy 改变是 View 的什么属性
     * scrollBy 也是调用了 scrollTo 方法
     * scrollBy和scrollTo方法区别：前者一直调用会以上次移动的位置为基础，而后者每次则从第一次的位置移动
     * 当View的内容往左往上时，mScrollX 和 mScrollY 为正。
     * 当View的内容往右往下时，mScrollX 和 mScrollY 为负。
     * 也就是说在View的坐标系中， mScrollX、mScrollY 分别为View的边缘减去对应内容边缘的大小。
     * 并且 scrollTo 和 scrollBy 改变的是其内容的位置，而不是其在布局中的位置！
     * 上：正
     * 下：负
     * 左：正
     * 右：负
     */
    private void scrollToAndScrollBy() {

        // 往左上移动100
//        button.scrollTo(20, 20);

        button.scrollBy(20, 20);
    }
}
