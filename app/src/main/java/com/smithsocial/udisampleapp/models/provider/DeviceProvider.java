package com.smithsocial.udisampleapp.models.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class DeviceProvider extends ContentProvider {
    private SQLiteOpenHelper dbOpenHelper;
    private SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        dbOpenHelper = new DeviceDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        database = dbOpenHelper.getReadableDatabase();
        Cursor cursor = database.query(DeviceContract.DeviceEntry.TABLE_NAME,
                strings, s, strings1, null, null, s1);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        database = dbOpenHelper.getWritableDatabase();
        database.insert(DeviceContract.DeviceEntry.TABLE_NAME, null, contentValues);
        getContext().getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        getContext().getContentResolver().notifyChange(uri, null);
        return dbOpenHelper.getWritableDatabase().delete(DeviceContract.DeviceEntry.TABLE_NAME, s, strings);
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        getContext().getContentResolver().notifyChange(uri, null);
        return dbOpenHelper.getWritableDatabase()
                .update(DeviceContract.DeviceEntry.TABLE_NAME, contentValues, s, strings);
    }
}
