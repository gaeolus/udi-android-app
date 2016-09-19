package com.smithsocial.udisampleapp.presenters;

import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.smithsocial.udisampleapp.views.HomeActivity;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

public class HomePresenterImpl extends HomePresenter {
    private HomeActivity homeActivity;
    //private LocalDevices localDevices;

    public HomePresenterImpl(HomeActivity homeActivity){
        this.homeActivity = homeActivity;
    }

    @Override
    public void onResume() {
        if (homeActivity != null){
            homeActivity.showProgress();
        }
        // get devices
        // when devices come back, load them into homeActivity's listview
    }

    @Override
    public void onDestroy() {
        homeActivity = null;
    }

    @Override
    public void reactToList(final ListView listView) {
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (subscriber.isUnsubscribed()) return;
                        subscriber.onNext( (String) listView.getAdapter().getItem(i));
                    }
                });
            }
        });

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                // completed
            }

            @Override
            public void onError(Throwable e) {
                // error
            }

            @Override
            public void onNext(String DeviceId) {
                homeActivity.goToDetails(DeviceId);
            }
        };

        observable.subscribe(observer);
    }

    @Override
    public void reactToFab(final FloatingActionButton fab) {
        Observable<View> observable = Observable.create(new Observable.OnSubscribe<View>() {
            @Override
            public void call(final Subscriber<? super View> subscriber) {
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (subscriber.isUnsubscribed()) return;
                        subscriber.onNext(view);
                    }
                });
            }
        });
        Observer<View> observer = new Observer<View>() {
            @Override
            public void onCompleted() {
                // completed
            }

            @Override
            public void onError(Throwable e) {
                // error
            }

            @Override
            public void onNext(View v) {
                homeActivity.goToSearch();
            }
        };
        observable.subscribe(observer);
    }
}
