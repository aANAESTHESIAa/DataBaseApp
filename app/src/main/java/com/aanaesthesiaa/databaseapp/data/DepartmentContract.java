package com.aanaesthesiaa.databaseapp.data;

import android.provider.BaseColumns;

public final class DepartmentContract {
    private DepartmentContract() {
    };

    public static final class DepartmentEntry implements BaseColumns {
        public final static String TABLE_NAME = "department";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NAME = "name";
        public final static String COLUMN_DESCRIPTION = "description";
        public final static String COLUMN_COMPANY = "company";
    }
}