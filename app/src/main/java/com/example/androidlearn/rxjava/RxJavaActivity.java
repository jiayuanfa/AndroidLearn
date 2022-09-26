package com.example.androidlearn.rxjava;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearn.R;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.Subject;

/**
 * RxJava
 */
public class RxJavaActivity extends AppCompatActivity {

    private Observer<String> observer;
    private Subscriber<String> subscriber;
    private Observable<String> observable;
    private @NonNull ObservableEmitter<String> mEmitter;

    private Disposable mInterval;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_empty);

//        rxJava();   // 示例1
//        rxJavaCreateStep(); // 示例2    分解写法
//        rxJavaSimple(); // 示例3    集合写法
        rxJavaSimpleForInterval();  // 示例3 定时器、轮询任务写法

    }

    /**
     * RxJava3
     * 定时器
     * 轮询
     * map
     */
    private void rxJavaSimpleForInterval() {
        Observable.interval(1, TimeUnit.SECONDS)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Throwable {
                        return aLong * 5;
                    }
                })
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mInterval = d;
                    }

                    @Override
                    public void onNext(@NonNull Long aLong) {
                        System.out.println("interval:" + aLong);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void rxJavaSimple() {
        Observable.create((ObservableOnSubscribe<String>) emitter -> {
            mEmitter = emitter;

            mEmitter.onNext("Fage");
            mEmitter.onNext("Start work");
            mEmitter.onComplete();

        }).subscribe(new Subject<String>() {
            @Override
            public boolean hasObservers() {
                return false;
            }

            @Override
            public boolean hasThrowable() {
                return false;
            }

            @Override
            public boolean hasComplete() {
                return false;
            }

            @Override
            public @io.reactivex.rxjava3.annotations.Nullable Throwable getThrowable() {
                return null;
            }

            @Override
            protected void subscribeActual(@NonNull Observer<? super String> observer) {

            }

            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {
                System.out.println("onNext:" + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     *  RxJava的使用步骤
     *
     *  1：创建观察者 Observer、实现了 Observer 的抽象类：Subscriber、
     *  Subscriber 对 Observer 接口进行了一些扩展，但他们的基本使用方式是完全一样的
     *
     *  2：创建被观察者 Observable
     *
     *  3: 订阅 Subscribe
     */
    private void rxJavaCreateStep() {

        createObserver();
        createSubscriber();

        createObservable();
        subscribe();
    }

    private void subscribe() {
        observable.subscribe(observer);
    }

    private void createObservable() {
        observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                emitter.onNext("Hello");
                emitter.onNext("Hi");
                emitter.onNext("FaGe");
                emitter.onComplete();
            }
        });
    }

    private void createSubscriber() {
        subscriber = new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(String s) {
                System.out.println("observer onNext:" + s);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    private void createObserver() {
        observer = new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {
                System.out.println("observer onNext:" + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    /**
     * 1: RxJava流程  Observable -> OnSubscribe -> onNext -> onNext ->
     * 2: 线程调度  subscribeOn -> observeOn
     */
    private void rxJava() {
        Observable.just("Hello world")
                .map(String::length)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(word -> {
                    System.out.println("got " + word + " @ " +
                            Thread.currentThread().getName());
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mInterval.dispose();
    }
}
