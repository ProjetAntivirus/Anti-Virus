package com.example.user.antivirus.contentProvider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

/**
 * Created by Maxime on 20/03/2015.
 */
public class table {
    private table (){}

    public static final class Sms extends SQLiteOpenHelper {
        public static final Uri CONTENT_SMS = Uri.parse("content://" + AntivirusProvider.PROVIDER_NAME + "/SMS");
        static final int uriCodeSMS = 1;

        static final String DATABASE_NAME = "SMSDB";
        static final int DATABASE_VERSION = 2;

        public static final String SMS_ID = "SMS_ID";
        public static final String SMS_DATE = "SMS_DATE";
        public static final String SMS_NUMERO = "SMS_NUMERO";
        public static final String SMS_MESSAGE = "SMS_MESSAGE";

        Sms(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(" CREATE TABLE " + DATABASE_NAME
                    + " (" + SMS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + SMS_DATE + " LONG NOT NULL, "
                    + SMS_NUMERO + " INTEGER NOT NULL, "
                    + SMS_MESSAGE + " TEXT NOT NULL);"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
            onCreate(db);
        }
    }

    public static final class Battery extends SQLiteOpenHelper{
        public static final Uri CONTENT_BATTERY = Uri.parse("content://" + AntivirusProvider.PROVIDER_NAME + "/BATTERY");
        static final int uriCodeBATTERY = 2;

        static final String DATABASE_NAME = "BATTERYDB";
        static final int DATABASE_VERSION = 2;

        public static final String BATTERY_ID = "BATTERY_ID";
        public static final String BATTERY_DATE = "BATTERY_DATE";
        public static final String BATTERY_LEVEL = "BATTERY_LEVEL";

        Battery(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(" CREATE TABLE " + DATABASE_NAME
                            + " (" + BATTERY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + BATTERY_DATE + " LONG NOT NULL, "
                            + BATTERY_LEVEL + " INTEGER NOT NULL);"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
            onCreate(db);
        }
    }

    public static final class Contact extends SQLiteOpenHelper{
        public static final Uri CONTENT_CONTACT = Uri.parse("content://" + AntivirusProvider.PROVIDER_NAME + "/CONTACT");
        static final int uriCodeCONTACT = 3;

        static final String DATABASE_NAME = "CONTACTDB";
        static final int DATABASE_VERSION = 2;

        public static final String CONTACT_ID = "CONTACT_ID";
        public static final String CONTACT_DATE = "CONTACT_DATE";
        public static final String CONTACT_NUMERO = "CONTACT_NUMERO";

        Contact(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(" CREATE TABLE " + DATABASE_NAME
                            + " (" + CONTACT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + CONTACT_DATE + " LONG NOT NULL, "
                            + CONTACT_NUMERO + " INTEGER NOT NULL);"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
            onCreate(db);
        }
    }
}
