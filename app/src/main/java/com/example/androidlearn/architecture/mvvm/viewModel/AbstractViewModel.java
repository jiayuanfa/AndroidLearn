package com.example.androidlearn.architecture.mvvm.viewModel;

import androidx.viewbinding.ViewBinding;

public abstract class AbstractViewModel <T extends ViewBinding> implements BaseViewModel{

    public T mViewDataBinding;
    public AbstractViewModel(T viewDataBinding) {
        mViewDataBinding = viewDataBinding;
    }

    @Override
    public void onDestroy() {

    }
}
