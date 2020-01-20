package com.ashwin.android.rxandroiddemo.operators;

import androidx.appcompat.app.AppCompatActivity;
import com.ashwin.android.rxandroiddemo.R;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class FlatMapActivity extends AppCompatActivity {
    private static final List<String> strList = new ArrayList<>(Arrays.asList("one", "two", "three", "four"));

    private Observable<Integer> observable;
    private DisposableObserver<Integer> observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat_map);

        observable = Observable.fromIterable(strList)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<String, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(String s) throws Exception {
                        Log.w("rx-android", "flat-map: apply s: " + s + " (" + Thread.currentThread().getName() + ")");
                        switch (s) {
                            case "one":
                                return Observable.just(1);
                            case "two":
                                return Observable.just(2);
                            case "three":
                                return Observable.just(3);
                            case "four":
                                return Observable.just(4);
                        }
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());

        observer = new DisposableObserver<Integer>() {
            @Override
            public void onNext(Integer integer) {
                Log.w("rx-android", "on-next: i: " + integer + " (" + Thread.currentThread().getName() + ")");
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
