package com.smithsocial.udisampleapp.presenters;

import android.widget.Button;

import com.smithsocial.udisampleapp.models.LoadDeviceFromApi;
import com.smithsocial.udisampleapp.models.LoadDeviceFromApiImpl;
import com.smithsocial.udisampleapp.views.DeviceDetailsActivity;

public class DetailsPresenterImpl extends DetailsPresenter {
    private DeviceDetailsActivity deviceDetailsActivity;
    private LoadDeviceFromApi loadDeviceFromApi;

    public DetailsPresenterImpl(DeviceDetailsActivity deviceDetailsActivity){
        this.deviceDetailsActivity = deviceDetailsActivity;
        this.loadDeviceFromApi = new LoadDeviceFromApiImpl();
    }

    @Override
    public void onResume() {
        if (deviceDetailsActivity != null){
        }
    }

    private void reactToDeviceLoad(){
        // wait until the wrapper is added
    }

    @Override
    public void onDestroy() {
        deviceDetailsActivity = null;
    }

    @Override
    public void reactToSave(Button saveButton) {
        // react to the save button
    }
}
