package com.smithsocial.udisampleapp.presenters;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.widget.EditText;

import com.smithsocial.udisampleapp.models.LoadDeviceFromApi;
import com.smithsocial.udisampleapp.models.LoadDeviceFromApiImpl;
import com.smithsocial.udisampleapp.views.SearchActivity;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import udiwrapper.Device.Device;

public class SearchPresenterImpl extends SearchPresenter {
    private SearchActivity searchActivity;
    private LoadDeviceFromApi loadDeviceFromApi;

    public SearchPresenterImpl(SearchActivity searchActivity){
        this.searchActivity = searchActivity;
        this.loadDeviceFromApi = new LoadDeviceFromApiImpl();
    }

    @Override
    public void onResume() {
        if (searchActivity != null){
            searchActivity.hideProgress();
            searchActivity.noDevice();
        }
    }

    @Override
    public void onDestroy() {
        searchActivity = null;
    }

    @Override
    public void reactToSearch(final EditText editText) {
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        searchActivity.showProgress();
                        subscriber.onNext( charSequence.toString() );
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
            }
        });
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                reactToDeviceExists(s);
            }
        };

        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void reactToDeviceExists(final String deviceId){
        Observable<Boolean> observable = Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                subscriber.onNext( loadDeviceFromApi.DeviceExists(deviceId) );
            }
        });

        Observer<Boolean> observer = new Observer<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Boolean aBoolean) {
                if ( aBoolean ) {
                    reactToFetch(deviceId);
                } else {
                    searchActivity.hideProgress();
                    searchActivity.noDevice();
                }
            }
        };

        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void reactToFetch(final String s){
        // once the wrapper is in place, this will be changed accordingly
        Observable<Device> observable = Observable.create(new Observable.OnSubscribe<Device>() {
            @Override
            public void call(Subscriber<? super Device> subscriber) {
                subscriber.onNext( loadDeviceFromApi.getDevice(s) );
            }
        });
        Observer<Device> observer = new Observer<Device>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Device device) {
                searchActivity.hideProgress();
                searchActivity.setDevice( s, device.getBrandName() );
            }
        };

        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void reactToDeviceClick(final Pair<String, String> stringPair) {
        Observable<Pair<String, String>> observable = Observable.create(new Observable.OnSubscribe<Pair<String, String>>() {
            @Override
            public void call(final Subscriber<? super Pair<String, String>> subscriber) {

                subscriber.onNext(stringPair);

            }
        });

        Observer<Pair<String, String>> observer = new Observer<Pair<String, String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Pair<String, String> stringStringPair) {
                searchActivity.goToDetails(stringStringPair.first, stringStringPair.second);
            }
        };

        observable.subscribe(observer);
    }
}
