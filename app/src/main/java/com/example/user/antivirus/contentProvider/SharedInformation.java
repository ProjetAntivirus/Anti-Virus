package com.example.user.antivirus.contentProvider;

/**
 * Created by Max on 11/02/2015.
 */
public class SharedInformation {
    public class BatteryInformation{
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String TABLE_NAME = "BatteryTable";
        public static final String CREATE_DB_TABLE = " CREATE TABLE " + TABLE_NAME
                + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +  NAME + " TEXT NOT NULL);";
    }
}
