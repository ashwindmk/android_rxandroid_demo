package com.ashwin.android.rxandroiddemo.operators;

import androidx.appcompat.app.AppCompatActivity;
import com.ashwin.android.rxandroiddemo.R;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class FromIterableActivity extends AppCompatActivity {
    private static final List<String> list = new ArrayList<>(Arrays.asList("one", "two", "three", "four"));

    private Observable<String> observable;
    private DisposableObserver<String> observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_from_iterable);

        observable = Observable.fromIterable(list)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

        observer = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                Log.w("rx-android", "on-next: s: " + s + " (" + Thread.currentThread().getName() + ")");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("rx-android", "on-error", e);
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
        observer.dispose();
    }
}
