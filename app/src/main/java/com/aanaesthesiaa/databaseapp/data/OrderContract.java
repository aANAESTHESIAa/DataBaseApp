package com.aanaesthesiaa.databaseapp.data;

import android.provider.BaseColumns;

public final class OrderContract {
    private OrderContract(){
    };

    public static final class OrderEntry implements BaseColumns {
        public final static String TABLE_NAME = "orders";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NAME = "name";
        public final static String COLUMN_ORDER_NUMBER = "ordernumber";
        public final static String COLUMN_PHONE = "phone";
        public final static String COLUMN_SUMM = "summ";
        public final static String COLUMN_CITY = "city";
        public final static String COLUMN_COMPANY = "company";
    }
}
