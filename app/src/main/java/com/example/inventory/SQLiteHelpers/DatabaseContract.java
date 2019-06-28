package com.example.inventory.SQLiteHelpers;

import android.provider.BaseColumns;

public class DatabaseContract {

    public DatabaseContract() {}

    public static final class DatabaseEntry implements BaseColumns{
        public static final String TABLE_COMPONENTS = "components";
        public static final String COMPONENTS_ID = "ID";
        public static final String COMPONENTS_COMP = "COMPONENTS";
        public static final String COMPONENTS_CAT = "CATEGORY";
        public static final String COMPONENTS_COUNT = "COUNT";
        public static final String COMPONENTS_DATE = "DATE";
        public static final String COMPONENTS_ADMIN = "ADMIN";





    }

}
