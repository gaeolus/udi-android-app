package com.smithsocial.udisampleapp.presenters;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.smithsocial.udisampleapp.models.LoadDeviceFromApi;
import com.smithsocial.udisampleapp.models.LoadDeviceFromApiImpl;
import com.smithsocial.udisampleapp.views.SearchActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import udiwrapper.openFDA.Device.Device;
import udiwrapper.openFDA.UDIWrapper;

public class SearchPresenterImpl extends SearchPresenter {
    private String searchValue;
    private String skip;
    private boolean searchChanged;
    private SearchActivity searchActivity;
    private LoadDeviceFromApi loadDeviceFromApi;
    private Subscription searchSubscription;
    private Subscription existsSubscription;
    private Subscription fetchSubscription;
    private Subscription clickSubscription;
    private Subscription listEndSubscription;

    public SearchPresenterImpl(SearchActivity searchActivity){
        this.searchActivity = searchActivity;
        this.loadDeviceFromApi = new LoadDeviceFromApiImpl(searchActivity);
    }

    @Override
    public void onResume() {
        if (searchActivity != null){
            List<String> fields = new ArrayList<>();
            for (UDIWrapper.DeviceProperties field : UDIWrapper.DeviceProperties.values()){
                fields.add(field.name());
            }
            searchActivity.listSearchFields(fields);
            searchActivity.hideProgress();
            searchActivity.noDevice();
        }
    }

    @Override
    public void onDestroy() {
        searchActivity = null;
        if (searchSubscription != null) searchSubscription.unsubscribe();
        if (existsSubscription != null) existsSubscription.unsubscribe();
        if (fetchSubscription != null) fetchSubscription.unsubscribe();
        if (clickSubscription != null) clickSubscription.unsubscribe();
        if (listEndSubscription != null) listEndSubscription.unsubscribe();
    }

    @Override
    public void reactToSearch(final EditText editText) {
        Observable<Pair<UDIWrapper.DeviceProperties, String>> observable = Observable.create(new Observable.OnSubscribe<Pair<UDIWrapper.DeviceProperties, String>>() {
            @Override
            public void call(final Subscriber<? super Pair<UDIWrapper.DeviceProperties, String>> subscriber) {
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        searchChanged = true;
                        String searchProperty = searchActivity.getSelectedSearchField();
                        UDIWrapper.DeviceProperties deviceProperties = UDIWrapper.DeviceProperties.valueOf(searchProperty);
                        String searchText = editText.getText().toString();
                        if (!searchText.isEmpty()){
                            searchActivity.showProgress();
                            subscriber.onNext( new Pair<>(deviceProperties, searchText));
                        } else {
                            searchValue = "";
                            searchActivity.hideProgress();
                            searchActivity.noDevice();
                        }
                    }
                });
            }
        });
        Observer<Pair<UDIWrapper.DeviceProperties, String>> observer = new Observer<Pair<UDIWrapper.DeviceProperties, String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Pair<UDIWrapper.DeviceProperties, String> search) {
                reactToDeviceExists(search.first, search.second);
            }
        };

        searchSubscription = observable.subscribe(observer);
    }

    private void reactToDeviceExists(final UDIWrapper.DeviceProperties deviceProperty, final String deviceValue){
        Observable<Boolean> observable = Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                searchValue = deviceValue;
                subscriber.onNext( loadDeviceFromApi.DeviceExists(deviceProperty, deviceValue) );
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
                    reactToFetch(deviceProperty, deviceValue);
                } else {
                    searchActivity.hideProgress();
                    searchActivity.noDevice();
                }
            }
        };

        existsSubscription = observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void reactToFetch(final UDIWrapper.DeviceProperties p, final String v){
        // once the wrapper is in place, this will be changed accordingly
        Observable<Map<String, Device>> observable = Observable.create(new Observable.OnSubscribe<Map<String, Device>>() {
            @Override
            public void call(Subscriber<? super Map<String, Device>> subscriber) {
                if (!searchValue.isEmpty()){
                    subscriber.onNext( loadDeviceFromApi.getDevices(p, v, skip) );
                }
            }
        });
        Observer<Map<String, Device>> observer = new Observer<Map<String, Device>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Map<String, Device> devices) {

                searchActivity.hideProgress();

                Iterator<String> iterator = devices.keySet().iterator();
                Map<String, String> deviceList = new HashMap<>();
                while (iterator.hasNext()){
                    String key = iterator.next();
                    deviceList.put(key, devices.get(key).getBrandName());
                }

                searchActivity.setDevices( deviceList, searchChanged);

                if (deviceList.size() == 10){
                    searchActivity.loadNextResults();
                }
            }
        };

        fetchSubscription = observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void reactToListEnd(final ListView listView){
        Observable<Integer> observable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(final Subscriber<? super Integer> subscriber) {
                if (subscriber.isUnsubscribed()) return;

                listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {

                    }

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                        if (firstVisibleItem + visibleItemCount == totalItemCount){
                            subscriber.onNext(totalItemCount);
                            subscriber.onCompleted();
                        }
                    }
                });
            }
        });

        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer i) {
                skip = Integer.toString(i);
                String searchProperty = searchActivity.getSelectedSearchField();
                UDIWrapper.DeviceProperties deviceProperties = UDIWrapper.DeviceProperties.valueOf(searchProperty);
                searchChanged = false;
                reactToFetch(deviceProperties, searchValue);
            }
        };

        listEndSubscription = observable.subscribe(observer);
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
                searchActivity.goToDetails((String) device.getKey(), (String) device.getValue());
            }
        };

        clickSubscription = observable.subscribe(observer);
    }
}
