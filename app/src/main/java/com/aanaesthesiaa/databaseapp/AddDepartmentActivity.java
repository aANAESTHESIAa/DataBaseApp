package com.aanaesthesiaa.databaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aanaesthesiaa.databaseapp.data.DepartmentContract;
import com.aanaesthesiaa.databaseapp.data.DbHelper;

public class AddDepartmentActivity extends AppCompatActivity {
    private EditText mNameEditText;
    private EditText mDescriptionEditText;
    long userId = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_department);

        mNameEditText = (EditText) findViewById(R.id.editTextDeparmentName);
        mDescriptionEditText = (EditText) findViewById(R.id.editTextADdescription);
        Button AddDepartment = (Button) findViewById(R.id.btnSaveDepartment);
        AddDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertDepartment();
                Intent intent = new Intent(AddDepartmentActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
    private void insertDepartment() {
        // Считываем данные из текстовых полей
        String name = mNameEditText.getText().toString().trim();
        String description = mDescriptionEditText.getText().toString().trim();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getLong("id");

        }

        DbHelper mDbHelper = new DbHelper(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DepartmentContract.DepartmentEntry.COLUMN_NAME, name);
        values.put(DepartmentContract.DepartmentEntry.COLUMN_DESCRIPTION, description);
        values.put(DepartmentContract.DepartmentEntry.COLUMN_COMPANY, userId);

        // Вставляем новый ряд в базу данных
        long newRowId = db.insert(DepartmentContract.DepartmentEntry.TABLE_NAME, null, values);

        // Выводим сообщение в успешном случае или при ошибке
        if (newRowId == -1) {
            // Если ID  -1, значит произошла ошибка
            Toast.makeText(this, "Ошибка при добавлении отдела компании", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Отдел компании добавлен", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
}