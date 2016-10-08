package com.smithsocial.udisampleapp.models;

import android.content.Context;

import com.smithsocial.udisampleapp.R;

import java.util.Map;

import udiwrapper.openFDA.Device.Device;
import udiwrapper.openFDA.UDIWrapper;

public class LoadDeviceFromApiImpl extends LoadDeviceFromApi {
    private UDIWrapper udiWrapper;
    private UDIWrapper.Builder udiWrapperBuilder;
    private UDIWrapper.DeviceProperties property = UDIWrapper.DeviceProperties.IDENTIFIER;
    private String searchValue = " ";
    private int skip = 0;

    public LoadDeviceFromApiImpl(Context context){
        udiWrapperBuilder = new UDIWrapper.Builder(context.getString(R.string.FDA_API_KEY)).setLimit(10);
    }

    @Override
    public boolean DeviceExists(UDIWrapper.DeviceProperties deviceProperty, String deviceValue) {
        skip = 0;
        if (udiWrapper == null){
            udiWrapper = udiWrapperBuilder
                    .setSearch(deviceProperty, deviceValue)
                    .build();
        } else {
            if (!this.searchValue.equals(deviceValue) || !(this.property == deviceProperty)){
                udiWrapper.alterSearch(deviceProperty, deviceValue, null, Integer.toString(skip));
            }
        }
        this.searchValue = deviceValue;
        this.property = deviceProperty;
        return udiWrapper.getSearchExists();
    }

    @Override
    public Map<String, Device> getDevices(UDIWrapper.DeviceProperties deviceProperty, String deviceValue) {
        skip = 0;
        if (udiWrapper == null){
            udiWrapper = udiWrapperBuilder
                    .setSearch(deviceProperty, deviceValue)
                    .build();
        } else {
            if (!(this.searchValue.equals(deviceValue)) || !(this.property == deviceProperty)){
                udiWrapper.alterSearch(deviceProperty, deviceValue, null, Integer.toString(skip));
            }
        }
        this.searchValue = deviceValue;
        this.property = deviceProperty;
        return udiWrapper.getDevices();
    }

    @Override
    public Map<String, Device> nextTen(){
        int currentSkip = skip + 10;
        udiWrapper.alterSearch(null, null, null, Integer.toString(currentSkip));
        return udiWrapper.getDevices();
    }
}