package com.ashwin.android.rxandroiddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class CodeRefactorActivity extends AppCompatActivity {
    private static final String out1 = "Hello Ashwin";

    private Observable<String> observable;

    private DisposableObserver<String> observer1;
    private DisposableObserver<String> observer2;

    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_refactor);

        observable = Observable.just(out1);

        observer1 = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                Log.w("rx-android", "observer-1: on-next: " + s + " (" + Thread.currentThread().getName() + ")");
            }

            @Override
            public void onError(Throwable e) {
                Log.w("rx-android", "observer-1: on-error: (" + Thread.currentThread().getName() + ")");
            }

            @Override
            public void onComplete() {
                Log.w("rx-android", "observer-1: on-complete: (" + Thread.currentThread().getName() + ")");
            }
        };

        observer2 = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                Log.w("rx-android", "observer-2: on-next: " + s + " (" + Thread.currentThread().getName() + ")");
            }

            @Override
            public void onError(Throwable e) {
                Log.w("rx-android", "observer-2: on-error: (" + Thread.currentThread().getName() + ")");
            }

            @Override
            public void onComplete() {
                Log.w("rx-android", "observer-2: on-complete: (" + Thread.currentThread().getName() + ")");
            }
        };

        compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(
                observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(observer1)
        );

        compositeDisposable.add(
                observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(observer2)
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
