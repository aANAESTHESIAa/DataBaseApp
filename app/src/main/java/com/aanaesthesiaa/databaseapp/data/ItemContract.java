package com.aanaesthesiaa.databaseapp.data;

import android.provider.BaseColumns;

public final class ItemContract {
    private ItemContract() {
    };

    public static final class ItemEntry implements BaseColumns {
        public final static String TABLE_NAME = "items";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NAME = "name";
        public final static String COLUMN_DESCRIPTION = "description";
        public final static String COLUMN_PRICE = "price";



    }


}
