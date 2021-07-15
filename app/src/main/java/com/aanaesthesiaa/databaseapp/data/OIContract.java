package com.aanaesthesiaa.databaseapp.data;

import android.provider.BaseColumns;

public final class OIContract {
    private OIContract(){
};
public static final class OIEntry implements BaseColumns {
    public final static String TABLE_NAME = "itemorders";

    public final static String _ID = BaseColumns._ID;
    public final static String COLUMN_ITEM = "itemtid";
    public final static String COLUMN_ORDER = "orderid";
}
}
