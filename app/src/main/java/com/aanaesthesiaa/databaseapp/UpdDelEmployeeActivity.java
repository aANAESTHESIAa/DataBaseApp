package com.aanaesthesiaa.databaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aanaesthesiaa.databaseapp.data.DepartmentContract;
import com.aanaesthesiaa.databaseapp.data.EmployeeContract.EmployeeEntry;
import com.aanaesthesiaa.databaseapp.data.DbHelper;

public class UpdDelEmployeeActivity extends AppCompatActivity {
    private DbHelper mDbHelper;
    EditText nameBox;
    EditText lastnameBox;
    EditText ageBox;
    TextView companyNameTV;
    EditText cityBox;
    Button delButton;
    Button saveButton;
    Cursor employeeCursor;
    Cursor newCursor;
    long employeeId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upd_del_employee);
        nameBox = (EditText) findViewById(R.id.UDEname);
        lastnameBox = (EditText) findViewById(R.id.UDElastname);
        ageBox = (EditText) findViewById(R.id.UDEage);
        companyNameTV = (TextView) findViewById(R.id.textViewShowCompany);
        cityBox = (EditText) findViewById(R.id.UDEcity);
        delButton = (Button) findViewById(R.id.UDEdeleteButton);
        saveButton = (Button) findViewById(R.id.UDEsaveButton);

        mDbHelper = new DbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            employeeId = extras.getLong("id");

        }

        if (employeeId > 0) {
            // получаем элемент по id из бд
            employeeCursor = db.rawQuery("select * from " + EmployeeEntry.TABLE_NAME + " where " +
                    EmployeeEntry._ID + "=?", new String[]{String.valueOf(employeeId)});
            employeeCursor.moveToFirst();
            nameBox.setText(employeeCursor.getString(1));
            lastnameBox.setText(employeeCursor.getString(2));
            ageBox.setText(employeeCursor.getString(5));
            cityBox.setText(employeeCursor.getString(3));
            String companyname = employeeCursor.getString(6);
            employeeCursor.close();


            newCursor = db.rawQuery(" SELECT company FROM department WHERE _id = ?",new String[]{String.valueOf(companyname)});
            newCursor.moveToFirst();
            companyname = newCursor.getString(0);
            newCursor.close();
            newCursor = db.rawQuery("SELECT name FROM company WHERE _id = ?",new String[]{String.valueOf(companyname)});
            newCursor.moveToFirst();
            companyname = newCursor.getString(0);
            companyNameTV.setText(companyname);


        } else {
            // скрываем кнопку удаления
            delButton.setVisibility(View.GONE);
        }
    }

    private void goHome(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        // закрываем подключение
        db.close();
        // переход к главной activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void save(View view){
        ContentValues cv = new ContentValues();
        cv.put(EmployeeEntry.COLUMN_NAME, nameBox.getText().toString());
        cv.put(EmployeeEntry.COLUMN_LASTNAME, lastnameBox.getText().toString());
        cv.put(EmployeeEntry.COLUMN_AGE, Integer.parseInt(ageBox.getText().toString()));
        cv.put(EmployeeEntry.COLUMN_CITY, cityBox.getText().toString());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        if (employeeId > 0) {
            db.update(EmployeeEntry.TABLE_NAME, cv, EmployeeEntry._ID + "=" + String.valueOf(employeeId), null);
        } else {
            db.insert(EmployeeEntry.TABLE_NAME, null, cv);
        }
        goHome();
    }
    public void delete(View view){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        db.delete(EmployeeEntry.TABLE_NAME, "_id = ?", new String[]{String.valueOf(employeeId)});
        goHome();
    }
}