package com.smithsocial.udisampleapp.models;

import android.util.Pair;

public abstract class LocalDevices {

    public abstract Pair<String, String> getSavedDevices();
    public abstract void saveDevice(String brandName, String deviceId);
    public abstract void deleteDevice(String deviceId);

}
