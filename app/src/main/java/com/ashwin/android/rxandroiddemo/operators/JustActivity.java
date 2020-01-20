package com.ashwin.android.rxandroiddemo.operators;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.ashwin.android.rxandroiddemo.R;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class JustActivity extends AppCompatActivity {
    private String out1 = "Hello Ashwin!";
    private String out2 = "Have a productive day!";

    private Observable<String> observable;
    private Observer<String> observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_just);

        observable = Observable.just(out1, out2);

        observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.w("rx-android", "on-subscribe");
            }

            @Override
            public void onNext(String s) {
                Log.w("rx-android", "on-next: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.w("rx-android", "on-error");
            }

            @Override
            public void onComplete() {
                Log.w("rx-android", "on-complete");
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        observable.subscribe(observer);
    }
}
