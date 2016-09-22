package com.smithsocial.udisampleapp.models;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.smithsocial.udisampleapp.models.provider.DeviceContract;

import java.util.HashMap;

public class LocalDevicesImpl extends LocalDevices {
    private ContentResolver contentResolver;

    public LocalDevicesImpl(Context context){
        this.contentResolver = context.getContentResolver();
    }

    @Override
    public HashMap<String, String> getSavedDevices() {
        Cursor cursor = contentResolver.query(DeviceContract.BASE_CONTENT_URI, null, null, null, null);

        if (cursor != null){
            HashMap<String, String> hashMap = new HashMap<>();
            int brandNameColumnIndex = cursor.getColumnIndex(DeviceContract.DeviceEntry.BRAND_NAME);
            int deviceIdColumnIndex = cursor.getColumnIndex(DeviceContract.DeviceEntry.DEVICE_ID);

            while (cursor.moveToNext()){
                hashMap.put(cursor.getString(deviceIdColumnIndex), cursor.getString(brandNameColumnIndex));
            }
            cursor.close();
            return hashMap;
        }
        return null;
    }

    @Override
    public void saveDevice(String brandName, String deviceId){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DeviceContract.DeviceEntry.DEVICE_ID, deviceId);
        contentValues.put(DeviceContract.DeviceEntry.BRAND_NAME, brandName);

        contentResolver.insert(DeviceContract.BASE_CONTENT_URI, contentValues);
    }

    @Override
    public void deleteDevice(String deviceId) {
        contentResolver.
                delete(DeviceContract.BASE_CONTENT_URI,
                        DeviceContract.DeviceEntry.DEVICE_ID + " = '" + deviceId + "'",
                        null);
    }
}
