package com.smithsocial.udisampleapp.models;

import java.util.HashMap;

public abstract class LocalDevices {

    public abstract HashMap<String, String> getSavedDevices();
    public abstract boolean hasSpecificDevice(String deviceId);
    public abstract void saveDevice(String brandName, String deviceId);
    public abstract void deleteDevice(String deviceId);

}
