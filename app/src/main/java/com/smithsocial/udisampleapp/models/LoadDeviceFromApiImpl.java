package com.smithsocial.udisampleapp.models;

import android.content.Context;

import com.smithsocial.udisampleapp.R;

import udiwrapper.openFDA.Device.Device;
import udiwrapper.openFDA.UDIWrapper;

public class LoadDeviceFromApiImpl extends LoadDeviceFromApi {
    private UDIWrapper udiWrapper;
    private UDIWrapper.Builder udiWrapperBuilder;

    public LoadDeviceFromApiImpl(Context context){
        udiWrapperBuilder = new UDIWrapper.Builder(context.getString(R.string.FDA_API_KEY));
    }

    @Override
    public boolean DeviceExists(String deviceId) {
        if (udiWrapper == null){
            udiWrapper = udiWrapperBuilder
                    .setSearch(null, deviceId)
                    .build();
        } else {
            udiWrapper.alterSearch(null, deviceId, null, null);
        }
        return udiWrapper.getSearchExists();
    }

    @Override
    public Device getDevice(String deviceId) {
        if (udiWrapper == null){
            udiWrapper = udiWrapperBuilder
                    .setSearch(null, deviceId)
                    .build();
        } else {
            udiWrapper.alterSearch(null, deviceId, null, null);
        }
        return udiWrapper.getDevice(deviceId);
    }
}