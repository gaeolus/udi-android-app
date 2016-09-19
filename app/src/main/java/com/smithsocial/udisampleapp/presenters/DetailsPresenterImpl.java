package com.smithsocial.udisampleapp.presenters;

import android.widget.Button;

import com.smithsocial.udisampleapp.views.DeviceDetailsActivity;

public class DetailsPresenterImpl extends DetailsPresenter {
    private DeviceDetailsActivity deviceDetailsActivity;
    //private LoadDeviceFromApi loadDeviceFromApi;

    public DetailsPresenterImpl(DeviceDetailsActivity deviceDetailsActivity){
        this.deviceDetailsActivity = deviceDetailsActivity;
    }

    @Override
    public void onResume() {
        if (deviceDetailsActivity != null){
            // get the device ID from DeviceDetailsActivity
        }
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
