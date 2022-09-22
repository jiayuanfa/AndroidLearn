package com.example.androidlearn.architecture.mvp.view;

import com.example.androidlearn.architecture.mvp.presenter.BasePresenter;

/**
 * BaseView是所有View的父类，将android中的view抽象话出来，只有跟view相关的操作都由baseView的实现类去完成
 * 通过interface集成interface的方式实现Presenter与view的关联
 * 也就是后面只需要实现BaseView的子类
 * 就能达到使用的时候一定要先初始化Presenter的目的了
 * @param <P>
 */
public interface BaseView<P extends BasePresenter> {
    void setPresenter(P presenter);
}
