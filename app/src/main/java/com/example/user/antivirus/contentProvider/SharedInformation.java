package com.example.user.antivirus.contentProvider;

import android.provider.BaseColumns;

import com.example.user.antivirus.Services.Battery;

/**
 * Created by Max on 09/02/2015.
 */
public class SharedInformation {
    public SharedInformation (){}

    public static final class Battery implements BaseColumns {
        public Battery (){}

        public static final String BATTERY_VALUE = "BATTERY_VALUE";
        public static final String BATTERY_VALUE_DATE = "BATTERY_VALUE_DATE";
    }
}
