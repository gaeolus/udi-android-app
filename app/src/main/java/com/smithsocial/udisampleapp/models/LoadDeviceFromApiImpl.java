package com.smithsocial.udisampleapp.models;

import udiwrapper.openFDA.Device.Device;
import udiwrapper.openFDA.UDIWrapper;

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