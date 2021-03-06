package com.smithsocial.udisampleapp.presenters;

import android.database.ContentObserver;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.widget.CheckBox;

import com.smithsocial.udisampleapp.models.LoadDeviceFromApi;
import com.smithsocial.udisampleapp.models.LoadDeviceFromApiImpl;
import com.smithsocial.udisampleapp.models.LocalDevices;
import com.smithsocial.udisampleapp.models.LocalDevicesImpl;
import com.smithsocial.udisampleapp.models.provider.DeviceContract;
import com.smithsocial.udisampleapp.views.DeviceDetailsActivity;

import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import udiwrapper.openFDA.Device.Device;
import udiwrapper.openFDA.UDIWrapper;

public class DetailsPresenterImpl extends DetailsPresenter {
    private String deviceId;
    private String deviceName;
    private DeviceDetailsActivity deviceDetailsActivity;
    private LoadDeviceFromApi loadDeviceFromApi;
    private LocalDevices localDevices;
    private Subscription deviceLoadSubscription;
    private Subscription deviceFetchSubscription;
    private Subscription deviceSaveSubscription;

    public DetailsPresenterImpl(DeviceDetailsActivity deviceDetailsActivity, String deviceName, String deviceId){
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.deviceDetailsActivity = deviceDetailsActivity;
        this.loadDeviceFromApi = new LoadDeviceFromApiImpl(deviceDetailsActivity);
        this.localDevices = new LocalDevicesImpl(deviceDetailsActivity.getApplicationContext());
    }

    @Override
    public void onResume() {
        reactToDeviceLoad();
        reactToDeviceFetch();
    }

    private void reactToDeviceLoad(){
        Observable<Boolean> observable = Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(final Subscriber<? super Boolean> subscriber) {
                subscriber.onNext( localDevices.hasSpecificDevice(deviceId) );

                HandlerThread observerThread = new HandlerThread("ContentObserver-thread");
                observerThread.start();
                ContentObserver contentObserver = new ContentObserver(new Handler(observerThread.getLooper())) {
                    @Override
                    public void onChange(boolean selfChange) {
                        super.onChange(selfChange);
                        subscriber.onNext( localDevices.hasSpecificDevice(deviceId) );
                    }
                };

                deviceDetailsActivity.getApplicationContext()
                        .getContentResolver()
                        .registerContentObserver(DeviceContract.BASE_CONTENT_URI, true, contentObserver);
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
            public void onNext(Boolean b) {
                deviceDetailsActivity.setDeviceIsSaved(b);
            }
        };
        deviceLoadSubscription = observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void reactToDeviceFetch(){
        // once the wrapper is in place, this will be changed accordingly
        Observable<Map<String, Device>> observable = Observable.create(new Observable.OnSubscribe<Map<String, Device>>() {
            @Override
            public void call(Subscriber<? super Map<String, Device>> subscriber) {
                deviceDetailsActivity.showProgress();
                subscriber.onNext( loadDeviceFromApi.getDevices(UDIWrapper.DeviceProperties.IDENTIFIER, deviceId) ); //loadDeviceFromApi.getDevice(deviceId);
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
            public void onNext(Map<String, Device> device) {
                deviceDetailsActivity.hideProgress();
                Device thisDevice = device.get(deviceId);
                deviceDetailsActivity.setDeviceName(thisDevice.getBrandName());
                deviceDetailsActivity.setDeviceId(thisDevice.getDeviceIdentifier());
                deviceDetailsActivity.setDeviceExpirationBool(thisDevice.hasExpirationDate());
                deviceDetailsActivity.setSterilizePriorToUse(thisDevice.isSterilizationPriorToUse());
                deviceDetailsActivity.setDeviceDescription(thisDevice.getDescription());
                //set the rest of the stuff here
            }
        };

        deviceFetchSubscription = observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void onDestroy() {
        deviceDetailsActivity = null;
        if (deviceLoadSubscription != null) deviceLoadSubscription.unsubscribe();
        if (deviceFetchSubscription != null) deviceFetchSubscription.unsubscribe();
        if (deviceSaveSubscription != null) deviceSaveSubscription.unsubscribe();
    }

    @Override
    public void reactToSave(final CheckBox checkBox) {
        Observable<Boolean> observable = Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(final Subscriber<? super Boolean> subscriber){
                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        subscriber.onNext(checkBox.isChecked());
                    }
                });
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
            public void onNext(Boolean o) {
                if (o) {
                    localDevices.saveDevice(deviceName, deviceId);
                } else {
                    localDevices.deleteDevice(deviceId);
                }
            }
        };

        deviceSaveSubscription = observable.subscribe(observer);
    }
}
