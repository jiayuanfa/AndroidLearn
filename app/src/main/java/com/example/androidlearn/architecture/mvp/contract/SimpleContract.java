package com.example.androidlearn.architecture.mvp.contract;

import com.example.androidlearn.architecture.mvc.callback.Callback1;
import com.example.androidlearn.architecture.mvc.model.UserInfo;
import com.example.androidlearn.architecture.mvp.http.HttpUtilsForMvp;
import com.example.androidlearn.architecture.mvp.presenter.BasePresenter;
import com.example.androidlearn.architecture.mvp.view.BaseView;

/**
 * Contract 契约类
 * 这是Google MVP与其他实现方式的又一个不同，
 * 契约类用于定义同一个界面的view的接口和presenter的具体实现。
 * 好处是通过规范的方法命名和注释可以清晰的看到整个页面的逻辑
 */
public class SimpleContract {

    public static class Presenter implements BasePresenter {

        private HttpUtilsForMvp httpUtils = new HttpUtilsForMvp();

        /**
         * 提供该外接获取Model的方法
         * @param uid
         * @param callback1
         */
        public void getUserInfo(String uid, Callback1<UserInfo> callback1) {
            callback1.onCallBack(httpUtils.getUserInfo(uid));
        }

        @Override
        public void onDestroy() {

        }
    }

    /**
     * 通过Presenter拿到数据之后
     * 通过调用View的方法
     * 实现逻辑与视图的解耦
     */
    public interface View extends BaseView<Presenter> {
        void setDataToView(UserInfo userInfo);
    }
}
