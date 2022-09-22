package com.example.androidlearn.architecture.mvvm.model;

import androidx.databinding.BaseObservable;

import com.example.androidlearn.BR;

public class UserInfo {

    private int age;
    private String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
//        notifyPropertyChanged(BR.user);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
//        notifyPropertyChanged(BR.user);
    }

}
