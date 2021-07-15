package com.aanaesthesiaa.databaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.aanaesthesiaa.databaseapp.data.CompanyContract.CompanyEntry;

import com.aanaesthesiaa.databaseapp.data.DepartmentContract.DepartmentEntry;
import com.aanaesthesiaa.databaseapp.data.DbHelper;


public class UpdDelCompanyActivity extends AppCompatActivity {
    EditText nameBox;
    EditText yearBox;
    EditText cityBox;
    Button delButton;
    Button saveButton;
    private DbHelper mDbHelper;
    Cursor userCursor;
    long companyId = 0;
    ListView departmentList;
    Cursor departmentCursor;
    SimpleCursorAdapter departmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upd_del_company);


        nameBox = (EditText) findViewById(R.id.UDCname);
        yearBox = (EditText) findViewById(R.id.UDCyear);
        cityBox = (EditText) findViewById(R.id.UDCcity);
        delButton = (Button) findViewById(R.id.UDCdeleteButton);
        saveButton = (Button) findViewById(R.id.UDCsaveButton);
        departmentList = (ListView) findViewById(R.id.listDepartment);
        departmentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), UpdDelDepartmentActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }});

        mDbHelper = new DbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            companyId = extras.getLong("id");

        }

        if (companyId > 0) {
            // получаем элемент по id из бд
            userCursor = db.rawQuery("select * from " + CompanyEntry.TABLE_NAME + " where " +
                    CompanyEntry._ID + "=?", new String[]{String.valueOf(companyId)});
            userCursor.moveToFirst();
            nameBox.setText(userCursor.getString(1));
            yearBox.setText(userCursor.getString(3));
            cityBox.setText(userCursor.getString(2));
            userCursor.close();
        } else {
            // скрываем кнопку удаления
            delButton.setVisibility(View.GONE);
        }

    }
    public void save(View view){
        ContentValues cv = new ContentValues();
        cv.put(CompanyEntry.COLUMN_NAME, nameBox.getText().toString());
        cv.put(CompanyEntry.COLUMN_YEAR, Integer.parseInt(yearBox.getText().toString()));
        cv.put(CompanyEntry.COLUMN_CITY, cityBox.getText().toString());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        if (companyId > 0) {
            db.update(CompanyEntry.TABLE_NAME, cv, CompanyEntry._ID + "=" + String.valueOf(companyId), null);
        } else {
            db.insert(CompanyEntry.TABLE_NAME, null, cv);
        }
        goHome();
    }
    public void delete(View view){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        db.delete(CompanyEntry.TABLE_NAME, "_id = ?", new String[]{String.valueOf(companyId)});
        goHome();
    }
    public void addDepartment(View view){
        Intent intent = new Intent(getApplicationContext(), AddDepartmentActivity.class);
        intent.putExtra("id", companyId);
        startActivity(intent);
    }
    private void displayDepartments() {
        // откроем для чтения базу данных
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        //------------------------------------------------------------------------вывод списка отделов------------------------------------
        departmentList = (ListView)findViewById(R.id.listDepartment);
        //получаем данные из бд в виде курсора
        departmentCursor =  db.rawQuery("select * from "+ DepartmentEntry.TABLE_NAME + " where "
                + DepartmentEntry.COLUMN_COMPANY + "=?", new String[]{String.valueOf(companyId)});
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[] {DepartmentEntry.COLUMN_NAME, DepartmentEntry.COLUMN_DESCRIPTION};
        // создаем адаптер, передаем в него курсор
        departmentAdapter = new SimpleCursorAdapter(this,android.R.layout.two_line_list_item,
                departmentCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        departmentList.setAdapter(departmentAdapter);
        //--------
    }


    @Override
    protected void onStart() {
        super.onStart();
        displayDepartments();
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
}




