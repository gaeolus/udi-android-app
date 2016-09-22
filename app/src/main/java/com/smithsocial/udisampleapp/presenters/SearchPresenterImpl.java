package com.smithsocial.udisampleapp.presenters;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.smithsocial.udisampleapp.models.LoadDeviceFromApi;
import com.smithsocial.udisampleapp.models.LoadDeviceFromApiImpl;
import com.smithsocial.udisampleapp.views.SearchActivity;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
                        Log.d("lol", charSequence.toString());
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
                if (loadDeviceFromApi.DeviceExists(s)){
                    reactToFetch(s);
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
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext( "Placeholder String" );
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
                searchActivity.hideProgress();
                searchActivity.setDevice();
            }
        };

        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void reactToDeviceClick(View view) {
        // react to clicking on the device that's returned by the search
    }
}
