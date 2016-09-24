package com.smithsocial.udisampleapp.models;

import udiwrapper.Device.Device;

public abstract class LoadDeviceFromApi {
    public abstract boolean DeviceExists(String deviceId);
    public abstract Device getDevice(String deviceId); // will return a device
}
