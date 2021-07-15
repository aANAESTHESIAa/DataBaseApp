package com.aanaesthesiaa.databaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.aanaesthesiaa.databaseapp.data.DepartmentContract.DepartmentEntry;
import com.aanaesthesiaa.databaseapp.data.EmployeeContract.EmployeeEntry;
import com.aanaesthesiaa.databaseapp.data.DbHelper;

public class UpdDelDepartmentActivity extends AppCompatActivity {
    EditText nameBox;
    EditText descBox;
    Button delButton;
    Button saveButton;
    private DbHelper mDbHelper;
    long departmentId = 0;
    Cursor userCursor;
    ListView employeeList;
    Cursor employeeCursor;
    SimpleCursorAdapter employeeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upd_del_department);

        nameBox = (EditText) findViewById(R.id.UDDedittextName);
        descBox = (EditText) findViewById(R.id.UDDedittextDesc);
        delButton = (Button) findViewById(R.id.UDDdeleteButton);
        saveButton = (Button) findViewById(R.id.UDDsaveButton);
        employeeList = (ListView) findViewById(R.id.employeeList);


        employeeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), UpdDelEmployeeActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }});

        mDbHelper = new DbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            departmentId = extras.getLong("id");

        }
        if (departmentId > 0) {
            // получаем элемент по id из бд
            userCursor = db.rawQuery("select * from " + DepartmentEntry.TABLE_NAME + " where " +
                    DepartmentEntry._ID + "=?", new String[]{String.valueOf(departmentId)});
            userCursor.moveToFirst();
            nameBox.setText(userCursor.getString(1));
            descBox.setText(userCursor.getString(2));
            userCursor.close();
        } else {
            // скрываем кнопку удаления
            delButton.setVisibility(View.GONE);
        }

    }
    public void addEmployee(View view){
        Intent intent = new Intent(getApplicationContext(), AddEmployeeActivity.class);
        intent.putExtra("id", departmentId);
        startActivity(intent);
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
        cv.put(DepartmentEntry.COLUMN_NAME, nameBox.getText().toString());
        cv.put(DepartmentEntry.COLUMN_DESCRIPTION, descBox.getText().toString());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        if (departmentId > 0) {
            db.update(DepartmentEntry.TABLE_NAME, cv, DepartmentEntry._ID + "=" + String.valueOf(departmentId), null);
        } else {
            db.insert(DepartmentEntry.TABLE_NAME, null, cv);
        }
        goHome();
    }
    public void delete(View view){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        db.delete(DepartmentEntry.TABLE_NAME, "_id = ?", new String[]{String.valueOf(departmentId)});
        goHome();
    }
    public void addDepartment(View view){
        Intent intent = new Intent(getApplicationContext(), AddDepartmentActivity.class);
        intent.putExtra("id", departmentId);
        startActivity(intent);
    }
    private void displayEmployees() {
        // откроем для чтения базу данных
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        //------------------------------------------------------------------------вывод списка компаний------------------------------------
        // открываем подключение
        employeeList = (ListView)findViewById(R.id.employeeList);
        //получаем данные из бд в виде курсора
        employeeCursor =  db.rawQuery("select * from "+ EmployeeEntry.TABLE_NAME + " where "
                + EmployeeEntry.COLUMN_DEPARTMENT + "=?", new String[]{String.valueOf(departmentId)});
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[] {EmployeeEntry.COLUMN_NAME, EmployeeEntry.COLUMN_LASTNAME};
        // создаем адаптер, передаем в него курсор
        employeeAdapter = new SimpleCursorAdapter(this,android.R.layout.two_line_list_item,
                employeeCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        employeeList.setAdapter(employeeAdapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        displayEmployees();
    }
}