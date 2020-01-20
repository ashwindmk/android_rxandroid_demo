package com.ashwin.android.rxandroiddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

public class MainActivity extends AppCompatActivity {
    private String out1 = "Hello Ashwin!";
    private String out2 = "Have a productive day!";

    private Observable<String> observable;
    private DisposableObserver<String> observer;

    private TextView contentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contentTextView = (TextView) findViewById(R.id.content_textview);

        observable = Observable.just(out1, out2);

        observer = new DisposableObserver<String>() {
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        observable.subscribe(observer);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w("rx-android", "observer dispose");
        observer.dispose();
    }
}
