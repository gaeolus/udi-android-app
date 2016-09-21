package com.smithsocial.udisampleapp.models.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DeviceDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "saved_devices.db";
    private static final int DB_VERSION = 1;

    DeviceDbHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_TASK_TABLE =
                "CREATE TABLE " + DeviceContract.DeviceEntry.TABLE_NAME
                + "("
                + DeviceContract.DeviceEntry.DEVICE_ID + " TEXT, "
                + DeviceContract.DeviceEntry.BRAND_NAME + " TEXT);";

        sqLiteDatabase.execSQL(SQL_CREATE_TASK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DeviceContract.DeviceEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
