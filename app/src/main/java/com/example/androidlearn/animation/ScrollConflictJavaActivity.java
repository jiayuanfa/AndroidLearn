package com.example.androidlearn.animation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.androidlearn.R;
import com.example.androidlearn.animation.view.HorizontalScrollerView;
import com.example.androidlearn.animation.view.VerticalScrollerView;

/**
 * 滑动冲突解决
 *
 * 1：常见的滑动冲突场景
 *  1.1：外部滑动方向和内部滑动方向不一致
 *      1.1.1：主页 ViewPager 和 Fragment 配合使用组成的页面滑动效果
 *      1.1.2：竖直滑动的 RecyclerView 的 item 里面 嵌套 水平滑动的 RecyclerView
 *      1.1.3：上面两种本该有滑动冲突，只是ViewPager 和 RecyclerView 帮我们处理了而已
 *
 *  1.2：外部滑动方向和内部滑动方向一致
 *      1.2.1：两层都是水平滑动 或者 都是竖直滑动的话，手指滑动的时候，并不知道用户到底想要滑动那一层，所以滑动的时候就会有问题，要么只有一层滑动，要么两层都在滑动
 *
 *  1.3以上两种情况嵌套
 *      1.3.1：外部滑动方向和内部滑动方向不一致 和 外部滑动方向和内部滑动方向一致 的嵌套
 *
 * 2：滑动冲突的处理规则
 *  2.1：场景一：外部滑动方向和内部滑动方向不一致：左右滑动时让外部的View上拦截点击事件，上下滑动的时候让内部的View拦截处理。根据滑动过程中两个点之间的坐标得出滑动方向来判断到底由谁来拦截
 *  2.2：场景二：外部滑动方向和内部滑动方向一致：根据业务的具体要求来决定是外部还是内部的View来拦截处理事件
 *  2.3：场景一和场景二的混合，参考一二处理规则即可
 *
 * 3：滑动冲突的解决方式
 *  3.1：外部拦截法：当父容器只要在 onInterceptTouchEvent 中拦截了事件(返回true)，后续的事件都不会传到子View
 *  3.2：内部拦截法：父容器不拦截任何事件，所有事件都传递给子元素，如果子元素要处理就直接消耗掉，否则再传递给父容器，这里子元素需要配合 requestDisallowInterceptTouchEvent(true) 才能正常工作
 *
 * 实例验证
 * 1：处理水平滑动和竖直滑动冲突
 * 2：处理水平滑动、竖直滑动、水平滑动一起出现的情况
 */
public class ScrollConflictJavaActivity extends AppCompatActivity implements View.OnClickListener {

    private HorizontalScrollerView horizontalScrollerView;
    private AppCompatButton tab1;
    private AppCompatButton tab2;
    private AppCompatButton tab3;

    private VerticalScrollerView verticalScrollerView1;
    private VerticalScrollerView verticalScrollerView2;
    private VerticalScrollerView verticalScrollerView3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_slide_conflict);

        initView();
    }

    private void initView() {
        horizontalScrollerView = findViewById(R.id.mHSV);
        tab1 = findViewById(R.id.tab_1);
        tab2 = findViewById(R.id.tab_2);
        tab3 = findViewById(R.id.tab_3);

        verticalScrollerView1 = findViewById(R.id.mVSV1);
        verticalScrollerView2 = findViewById(R.id.mVSV2);
        verticalScrollerView3 = findViewById(R.id.mVSV3);

        setUpRsV(verticalScrollerView1);
        setUpRsV(verticalScrollerView2);
        setUpRsV(verticalScrollerView3);

        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);

        horizontalScrollerView.setOnChangeListener(new HorizontalScrollerView.OnChangeListener() {
            @Override
            public void indexChange(int position) {
                tab1.setSelected(false);
                tab2.setSelected(false);
                tab3.setSelected(false);
                switch (position) {
                    case 1:
                        tab2.setSelected(true);
                        break;
                    case 2:
                        tab3.setSelected(true);
                        break;
                    default:
                        tab1.setSelected(true);
                        break;
                }
            }
        });
    }

    /**
     * 测试数据的起始值和个数
     */
    private int start = 0, count = 30;
    private void setUpRsV(VerticalScrollerView verticalScrollerView) {
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = 32;
        for (int i = start; i < count - 1; i++) {
            AppCompatButton button = new AppCompatButton(this);
            button.setLayoutParams(layoutParams);
            button.setText(String.valueOf(i));
            verticalScrollerView.addView(button);
        }
        updateData();
    }

    /**
     * 更新数据
     */
    private void updateData() {
        start += 50;
        count += 50;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        int pos = 0;
        switch (view.getId()) {

            case R.id.tab_1:
                break;

            case R.id.tab_2:
                pos = 1;
                break;

            case R.id.tab_3:
                pos = 2;
                break;

            default:
                pos = 0;
                break;
        }

        horizontalScrollerView.updateChildIndex(pos);
    }
}
