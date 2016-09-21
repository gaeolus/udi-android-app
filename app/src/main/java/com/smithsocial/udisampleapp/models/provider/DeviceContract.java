package com.smithsocial.udisampleapp.models.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class DeviceContract {

    public static final String CONTENT_AUTHORITY = "com.smithsocial.sampleudiapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class DeviceEntry implements BaseColumns {
        // table name
        public static final String TABLE_NAME = "saved_devices";

        // columns
        public static final String BRAND_NAME = "brand_name";
        public static final String DEVICE_ID = "device_id";
    }

}
