package com.smithsocial.udisampleapp.models;

public abstract class LoadDeviceFromApi {
    public abstract boolean DeviceExists(String deviceId);
    public abstract void getDevice(String deviceId); // will return a device
}
