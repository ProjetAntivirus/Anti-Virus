package com.example.user.antivirus.contentProvider;

import android.net.Uri;
import android.provider.BaseColumns;
/**
 * Created by Max on 28/12/2014.
 */
public class DataBaseTable{
    public DataBaseTable (){
    }

    public interface TableBatteryColums {
        public static final String NOM_APPLICATION = "Nom de l'application";
        public static final String POURCENTAGE_BATTERY = "Pourcentage battery";
    }


    public static final String CONTENT_AUTHORITY = "com.iut.database.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"
            + CONTENT_AUTHORITY);
    private static final String CONTENT_TYPE_FORMAT = "vnd.android.cursor.dir/vnd."
            .concat(CONTENT_AUTHORITY).concat(".");
    private static final String CONTENT_ITEM_TYPE_FORMAT = "vnd.android.cursor.item/vnd."
            .concat(CONTENT_AUTHORITY).concat(".");

    public static final String PATH_SYSTEM_BATTERY = "battery";

    public static class SystemBattery implements TableBatteryColums, BaseColumns {
        private static final String PATH = PATH_SYSTEM_BATTERY;
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH).build();
        public static final String CONTENT_TYPE = CONTENT_TYPE_FORMAT
                .concat(PATH);
        public static final String CONTENT_ITEM_TYPE = CONTENT_ITEM_TYPE_FORMAT
                .concat(PATH);

        public static final String PATH_BASEID = "baseid";

        public static Uri buildBaseIdUri(final long _id) {
            return CONTENT_URI.buildUpon().appendPath(PATH_BASEID)
                    .appendPath(Long.toString(_id)).build();
        }

        public static String getBaseIdUri(final Uri uri) {
            return uri.getLastPathSegment();
        }
    }
}
