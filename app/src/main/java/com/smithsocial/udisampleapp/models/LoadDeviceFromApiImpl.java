package com.smithsocial.udisampleapp.models;

public class LoadDeviceFromApiImpl extends LoadDeviceFromApi {
    @Override
    public boolean DeviceExists(String deviceId) {
        return false; //return whether or not the device exists
    }

    @Override
    public void getDevice(String deviceId) {
        // return a device with a given DI string
    }
}