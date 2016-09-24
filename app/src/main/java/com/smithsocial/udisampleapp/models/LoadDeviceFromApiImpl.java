package com.smithsocial.udisampleapp.models;

import udiwrapper.Device.Device;
import udiwrapper.UDIWrapper;

public class LoadDeviceFromApiImpl extends LoadDeviceFromApi {
    @Override
    public boolean DeviceExists(String deviceId) {
        return UDIWrapper.deviceExists(deviceId);
    }

    @Override
    public Device getDevice(String deviceId) {
        return UDIWrapper.fetchDIDevice(deviceId);
    }
}