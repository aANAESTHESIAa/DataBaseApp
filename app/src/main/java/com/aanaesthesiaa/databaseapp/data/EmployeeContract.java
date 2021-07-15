package com.aanaesthesiaa.databaseapp.data;

import android.provider.BaseColumns;

public final class EmployeeContract {
    private EmployeeContract() {
    };

    public static final class EmployeeEntry implements BaseColumns {
        public final static String TABLE_NAME = "employees";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NAME = "name";
        public final static String COLUMN_LASTNAME = "lastname";
        public final static String COLUMN_CITY = "city";
        public final static String COLUMN_GENDER = "gender";
        public final static String COLUMN_AGE = "age";
        public final static String COLUMN_DEPARTMENT = "department";


        public static final int GENDER_FEMALE = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_UNKNOWN = 2;


    }

}