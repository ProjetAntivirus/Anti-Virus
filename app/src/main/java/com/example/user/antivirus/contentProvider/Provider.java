package com.example.user.antivirus.contentProvider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;


/**
 * Created by Max on 09/02/2015.
 */
public class Provider extends ContentProvider{

    public static final Uri CONTENT_URI = Uri.parse("content://com.android.content.Contentprovider.provider");
    public static final String CONTENT_PROVIDER_DB_NAME = "provider";
    public static final int CONTENT_PROVIDER_DB_VERSION = 1;
    public static final String CONTENT_PROVIDER_TABLE_NAME = "battery";
    public static final String CONTENT_PROVIDER_MIME = "vnd.android.cursor.item/vnd.android.content.contentProvider.battery";

    private static class DatabaseHelper extends SQLiteOpenHelper{

        DatabaseHelper (Context context){
            super(context, Provider.CONTENT_PROVIDER_DB_NAME, null, Provider.CONTENT_PROVIDER_DB_VERSION);
        }

        @Override
        public void onCreate (SQLiteDatabase db){
            db.execSQL("CREATE TABLE "
                    + Provider.CONTENT_PROVIDER_TABLE_NAME + " ("
                    + SharedInformation.Battery.BATTERY_VALUE + " VARCHAR(10),"
                    + SharedInformation.Battery.BATTERY_VALUE_DATE + " VARCHAR(20)" + ");" );
        }

        @Override
        public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + Provider.CONTENT_PROVIDER_TABLE_NAME);
            onCreate(db);
        }
    }

    private DatabaseHelper dbHelper;

    @Override
    public boolean onCreate(){
        dbHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri){
        return Provider.CONTENT_PROVIDER_MIME;
    }

    private long getId(Uri uri){
        String lastPathSegment = uri.getLastPathSegment();
        if (lastPathSegment != null){
            try{
                return Long.parseLong(lastPathSegment);
            } catch (NumberFormatException e){
                Log.e("Provider", " Number format Exception : " + e);
            }
        }
        return -1;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try{
            long id = db.insertOrThrow(Provider.CONTENT_PROVIDER_TABLE_NAME, null, values);
            if (id == -1){
                throw  new RuntimeException(String.format("%s : Failed to insert [%s] for unknown reasons.",
                        "Provider", values, uri));
            } else{
                return ContentUris.withAppendedId(uri, id);
            }
        } finally {
            db.close();
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs){
        long id = getId(uri);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            if (id < 0)
                return db.update(Provider.CONTENT_PROVIDER_TABLE_NAME, values, selection, selectionArgs);
            else
                return db.update(Provider.CONTENT_PROVIDER_TABLE_NAME, values, SharedInformation.Battery.BATTERY_VALUE + "=" + id, null);
        } finally {
            db.close();
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs){
        long id = getId(uri);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try{
            if (id<0)
                return db.delete(Provider.CONTENT_PROVIDER_TABLE_NAME, selection, selectionArgs);
            else
                return db.delete(Provider.CONTENT_PROVIDER_TABLE_NAME, SharedInformation.Battery.BATTERY_VALUE + "=" + id, selectionArgs);
        } finally {
            db.close();
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder){
        long id = getId(uri);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (id<0){
            return db.query(Provider.CONTENT_PROVIDER_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        } else {
            return db.query(Provider.CONTENT_PROVIDER_TABLE_NAME, projection, SharedInformation.Battery.BATTERY_VALUE + "=" + id, null, null, null, null);
        }
    }
}
