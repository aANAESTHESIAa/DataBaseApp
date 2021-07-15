package com.aanaesthesiaa.databaseapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.aanaesthesiaa.databaseapp.data.PersonContract.GuestEntry;
import com.aanaesthesiaa.databaseapp.data.EmployeeContract.EmployeeEntry;
import com.aanaesthesiaa.databaseapp.data.CompanyContract.CompanyEntry;
import com.aanaesthesiaa.databaseapp.data.DepartmentContract.DepartmentEntry;
import com.aanaesthesiaa.databaseapp.data.OrderContract.OrderEntry;
import com.aanaesthesiaa.databaseapp.data.ItemContract.ItemEntry;
import com.aanaesthesiaa.databaseapp.data.OIContract.OIEntry;


public class DbHelper extends SQLiteOpenHelper  {

    public static final String LOG_TAG = DbHelper.class.getSimpleName();

    /**
     * Имя файла базы данных
     */
    private static final String DATABASE_NAME = "hotel.db";

    /**
     * Версия базы данных. При изменении схемы увеличить на единицу
     */
    private static final int DATABASE_VERSION = 1;


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Строка для создания таблицы----------------------------------------------------------------------
        String SQL_CREATE_GUESTS_TABLE = "CREATE TABLE " + GuestEntry.TABLE_NAME + " ("
                + PersonContract.GuestEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + GuestEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + GuestEntry.COLUMN_CITY + " TEXT NOT NULL, "
                + GuestEntry.COLUMN_GENDER + " INTEGER NOT NULL DEFAULT 3, "
                + GuestEntry.COLUMN_AGE + " INTEGER NOT NULL DEFAULT 0);";
        db.execSQL(SQL_CREATE_GUESTS_TABLE);



        // Строка для создания таблицы----------------------------------------------------------------------
        String SQL_CREATE_COMPANY_TABLE = "CREATE TABLE " + CompanyEntry.TABLE_NAME + " ("
                + CompanyContract.CompanyEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CompanyEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + CompanyEntry.COLUMN_CITY + " TEXT NOT NULL, "
                + CompanyEntry.COLUMN_YEAR + " INTEGER NOT NULL)";
        db.execSQL(SQL_CREATE_COMPANY_TABLE);



        // Строка для создания таблицы-----------------------------------------------------------------------
        String SQL_CREATE_DEPARTMENT_TABLE = "CREATE TABLE " + DepartmentEntry.TABLE_NAME + " ("
                + DepartmentContract.DepartmentEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DepartmentEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + DepartmentEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, "
                + DepartmentEntry.COLUMN_COMPANY + " INTEGER, "
                + "FOREIGN KEY(" + DepartmentEntry.COLUMN_COMPANY + ") REFERENCES "
                + CompanyEntry.TABLE_NAME + "( "+CompanyEntry._ID +") ON DELETE CASCADE);";
        db.execSQL(SQL_CREATE_DEPARTMENT_TABLE);



       // db.execSQL("DROP TABLE " + ItemEntry.TABLE_NAME + ";");

        // Строка для создания таблицы-----------------------------------------------------------------------
        String SQL_CREATE_ORDER_TABLE = "CREATE TABLE " + OrderEntry.TABLE_NAME + " ("
                + OrderContract.OrderEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + OrderEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + OrderEntry.COLUMN_ORDER_NUMBER + " TEXT UNIQUE, "
                + OrderEntry.COLUMN_SUMM + " INTEGER NOT NULL DEFAULT 0, "
                + OrderEntry.COLUMN_PHONE + " TEXT NOT NULL, "
                + OrderEntry.COLUMN_CITY + " TEXT NOT NULL, "
                + OrderEntry.COLUMN_COMPANY + " INTEGER, "
                + "FOREIGN KEY(" + OrderEntry.COLUMN_COMPANY + ") REFERENCES "
                + CompanyEntry.TABLE_NAME + "( "+CompanyEntry._ID +") ON DELETE CASCADE);";
        db.execSQL(SQL_CREATE_ORDER_TABLE);



        String SQL_CREATE_EMPLOYEES_TABLE = "CREATE TABLE " + EmployeeEntry.TABLE_NAME + " ("
                + EmployeeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EmployeeEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + EmployeeEntry.COLUMN_LASTNAME + " TEXT NOT NULL, "
                + EmployeeEntry.COLUMN_CITY + " TEXT NOT NULL, "
                + EmployeeEntry.COLUMN_GENDER + " INTEGER NOT NULL DEFAULT 3, "
                + EmployeeEntry.COLUMN_AGE + " INTEGER NOT NULL DEFAULT 0,"
                + EmployeeEntry.COLUMN_DEPARTMENT + " INTEGER, "
                + "FOREIGN KEY(" + EmployeeEntry.COLUMN_DEPARTMENT + ") REFERENCES "
                + DepartmentEntry.TABLE_NAME + "( "+DepartmentEntry._ID +") ON DELETE CASCADE);";
        db.execSQL(SQL_CREATE_EMPLOYEES_TABLE);


        String SQL_CREATE_OI_TABLE = "CREATE TABLE " + OIEntry.TABLE_NAME + " ("
                + OIEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + OIEntry.COLUMN_ORDER + " INTEGER, "
                + OIEntry.COLUMN_ITEM + " INTEGER NOT NULL REFERENCES "
                + ItemEntry.TABLE_NAME + "( "+ItemEntry._ID +") ,"
                + "FOREIGN KEY(" + OIEntry.COLUMN_ORDER + ") REFERENCES "
                + OrderEntry.TABLE_NAME + "( "+OrderEntry._ID +"));";
        db.execSQL(SQL_CREATE_OI_TABLE);


        String SQL_CREATE_ITEM_TABLE = "CREATE TABLE " + ItemEntry.TABLE_NAME + " ("
                + ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ItemEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + ItemEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, "
                + ItemEntry.COLUMN_PRICE + " INTEGER)";
        db.execSQL(SQL_CREATE_ITEM_TABLE);

    }
    /**
     * Вызывается при обновлении схемы базы данных
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//    Запишем в журнал
        Log.w("SQLite", "Обновляемся с версии " + oldVersion + " на версию " + newVersion);
        // Удаляем старую и создаём новую
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        // Создаём новую таблицу
        onCreate(db);
    }
}
