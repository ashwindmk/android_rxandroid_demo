package com.ashwin.android.rxandroiddemo.operators;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.ashwin.android.rxandroiddemo.R;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

public class FromArrayActivity extends AppCompatActivity {
    private static final String[] arr = {"one", "two", "three", "four"};

    private Observable<String> observable;
    private DisposableObserver<String> observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_from_array);

        observable = Observable.fromArray(arr);

        observer = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                Log.w("rx-android", "on-next: " + s + " (" + Thread.currentThread().getName() + ")");
            }

            @Override
            public void onError(Throwable e) {
                Log.w("rx-android", "on-error: (" + Thread.currentThread().getName() + ")");
            }

            @Override
            public void onComplete() {
                Log.w("rx-android", "on-complete: (" + Thread.currentThread().getName() + ")");
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
