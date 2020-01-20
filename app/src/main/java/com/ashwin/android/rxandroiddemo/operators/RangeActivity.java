package com.ashwin.android.rxandroiddemo.operators;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import com.ashwin.android.rxandroiddemo.R;
import io.reactivex.observers.DisposableObserver;

public class RangeActivity extends AppCompatActivity {
    private Observable<Integer> observable;
    private DisposableObserver<Integer> observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_range);

        observable = Observable.range(1, 10);

        observer = new DisposableObserver<Integer>() {
            @Override
            public void onNext(Integer integer) {
                Log.w("rx-android", "on-next: i: " + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.w("rx-android", "on-error", e);
            }

            @Override
            public void onComplete() {
                Log.w("rx-android", "on-complete");
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
