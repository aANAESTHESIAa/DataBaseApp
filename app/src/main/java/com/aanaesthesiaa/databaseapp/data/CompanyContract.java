package com.aanaesthesiaa.databaseapp.data;

import android.provider.BaseColumns;

public final class CompanyContract {
    private CompanyContract() {
    };

    public static final class CompanyEntry implements BaseColumns {
        public final static String TABLE_NAME = "company";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NAME = "name";
        public final static String COLUMN_YEAR = "year";
        public final static String COLUMN_CITY = "city";
    }
}

