package com.ashwin.android.rxandroiddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SchedulersActivity extends AppCompatActivity {
    private static final String out1 = "Hello Ashwin";

    private Observable<String> observable;
    private Observer<String> observer;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedulers);

        observable = Observable.just(out1)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());

        observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.w("rx-android", "on-subscribe: (" + Thread.currentThread().getName() + ")");
                disposable = d;
            }

            @Override
            public void onNext(String s) {
                Log.w("rx-android", "on-next: " + s + " (" + Thread.currentThread().getName() + ")");
            }

            @Override
            public void onError(Throwable e) {
                Log.w("rx-android", "on-error (" + Thread.currentThread().getName() + ")");
            }

            @Override
            public void onComplete() {
                Log.w("rx-android", "on-complete (" + Thread.currentThread().getName() + ")");
            }
        };

        observable.subscribe(observer);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
