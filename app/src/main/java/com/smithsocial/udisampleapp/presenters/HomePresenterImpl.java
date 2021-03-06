package com.smithsocial.udisampleapp.presenters;

import android.database.ContentObserver;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.smithsocial.udisampleapp.models.LocalDevices;
import com.smithsocial.udisampleapp.models.LocalDevicesImpl;
import com.smithsocial.udisampleapp.models.provider.DeviceContract;
import com.smithsocial.udisampleapp.views.HomeActivity;

import java.util.HashMap;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HomePresenterImpl extends HomePresenter {
    private HomeActivity homeActivity;
    private LocalDevices localDevices;
    private Subscription dataBaseSubscription;
    private Subscription listSubscription;
    private Subscription fabSubscription;

    public HomePresenterImpl(HomeActivity homeActivity){
        this.homeActivity = homeActivity;
        localDevices = new LocalDevicesImpl(this.homeActivity.getApplicationContext());
    }

    @Override
    public void onResume() {
        if (homeActivity != null){
            homeActivity.showProgress();
        }
        reactToDatabase();
    }

    private void reactToDatabase(){
        Observable<HashMap<String, String>> observable = Observable.create(new Observable.OnSubscribe<HashMap<String, String>>() {
            @Override
            public void call(final Subscriber<? super HashMap<String, String>> subscriber) {
                homeActivity.showProgress();
                subscriber.onNext( localDevices.getSavedDevices() );

                HandlerThread observerThread = new HandlerThread("ContentObserver-thread");
                observerThread.start();
                ContentObserver contentObserver = new ContentObserver(new Handler(observerThread.getLooper())) {
                    @Override
                    public void onChange(boolean selfChange) {
                        super.onChange(selfChange);
                        subscriber.onNext( localDevices.getSavedDevices() );
                    }
                };

                homeActivity.getApplicationContext()
                        .getContentResolver()
                        .registerContentObserver(DeviceContract.BASE_CONTENT_URI, true, contentObserver);
            }
        });

        Observer<HashMap<String, String>> observer = new Observer<HashMap<String, String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(HashMap<String, String> stringStringHashMap) {
                homeActivity.hideProgress();
                if (stringStringHashMap.isEmpty()){
                    homeActivity.noDevices();
                } else {
                    homeActivity.setList(stringStringHashMap);
                }
            }
        };

        dataBaseSubscription = observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void onDestroy() {
        homeActivity = null;
        if (listSubscription != null) listSubscription.unsubscribe();
        if (fabSubscription != null) fabSubscription.unsubscribe();
        if (dataBaseSubscription != null) dataBaseSubscription.unsubscribe();
    }

    @Override
    public void reactToList(final ListView listView) {
        Observable<HashMap.Entry> observable = Observable.create(new Observable.OnSubscribe<HashMap.Entry>() {
            @Override
            public void call(final Subscriber<? super HashMap.Entry> subscriber) {
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (subscriber.isUnsubscribed()) return;
                        HashMap.Entry mapEntry = (HashMap.Entry) listView.getAdapter().getItem(i);
                        subscriber.onNext( mapEntry );
                    }
                });
            }
        });

        Observer<HashMap.Entry> observer = new Observer<HashMap.Entry>() {
            @Override
            public void onCompleted() {
                // completed
            }

            @Override
            public void onError(Throwable e) {
                // error
            }

            @Override
            public void onNext(HashMap.Entry device) {
                homeActivity.goToDetails((String) device.getKey(), (String) device.getValue());
            }
        };

        listSubscription = observable.subscribe(observer);
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
        fabSubscription = observable.subscribe(observer);
    }
}
