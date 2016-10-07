package com.smithsocial.udisampleapp.models;

import java.util.Map;

import udiwrapper.openFDA.Device.Device;
import udiwrapper.openFDA.UDIWrapper;

public abstract class LoadDeviceFromApi {
    public abstract boolean DeviceExists(UDIWrapper.DeviceProperties deviceProperty, String deviceValue);
    public abstract Map<String, Device> getDevices(UDIWrapper.DeviceProperties deviceProperty, String deviceValue, String skip);
}
