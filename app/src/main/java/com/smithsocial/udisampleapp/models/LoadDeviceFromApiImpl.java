package com.smithsocial.udisampleapp.models;

import android.content.Context;

import com.smithsocial.udisampleapp.R;

import java.util.Map;

import udiwrapper.openFDA.Device.Device;
import udiwrapper.openFDA.UDIWrapper;

public class LoadDeviceFromApiImpl extends LoadDeviceFromApi {
    private UDIWrapper udiWrapper;
    private UDIWrapper.Builder udiWrapperBuilder;

    public LoadDeviceFromApiImpl(Context context){
        udiWrapperBuilder = new UDIWrapper.Builder(context.getString(R.string.FDA_API_KEY));
    }

    @Override
    public boolean DeviceExists(UDIWrapper.DeviceProperties deviceProperty, String deviceValue) {
        if (udiWrapper == null){
            udiWrapper = udiWrapperBuilder
                    .setSearch(deviceProperty, deviceValue)
                    .build();
        } else {
            udiWrapper.alterSearch(deviceProperty, deviceValue, null, null);
        }
        return udiWrapper.getSearchExists();
    }

    @Override
    public Map<String, Device> getDevices(UDIWrapper.DeviceProperties deviceProperty, String deviceValue, String skip) {
        if (udiWrapper == null){
            udiWrapper = udiWrapperBuilder
                    .setSearch(deviceProperty, deviceValue)
                    .build();
        } else {
            udiWrapper.alterSearch(deviceProperty, deviceValue, "10", skip);
        }
        return udiWrapper.getDevices();
    }
}