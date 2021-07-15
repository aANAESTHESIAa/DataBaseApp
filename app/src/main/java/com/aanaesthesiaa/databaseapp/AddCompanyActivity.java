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

import com.aanaesthesiaa.databaseapp.data.CompanyContract;
import com.aanaesthesiaa.databaseapp.data.DbHelper;

public class AddCompanyActivity extends AppCompatActivity {

    private EditText mNameEditText;
    private EditText mYearEditText;
    private EditText mCityEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);

        mNameEditText = (EditText) findViewById(R.id.editTextCompanyName);
        mYearEditText = (EditText) findViewById(R.id.editTextYear);
        mCityEditText = (EditText) findViewById(R.id.editTextCity);


        Button AddCompany = (Button) findViewById(R.id.saveCompany);
        AddCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertCompany();
                Intent intent = new Intent(AddCompanyActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


    private void insertCompany() {
        // Считываем данные из текстовых полей
        String name = mNameEditText.getText().toString().trim();
        String year = mYearEditText.getText().toString().trim();
        String city = mCityEditText.getText().toString().trim();

        DbHelper mDbHelper = new DbHelper(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CompanyContract.CompanyEntry.COLUMN_NAME, name);
        values.put(CompanyContract.CompanyEntry.COLUMN_YEAR, year);
        values.put(CompanyContract.CompanyEntry.COLUMN_CITY, city);


        long newRowId = db.insert(CompanyContract.CompanyEntry.TABLE_NAME, null, values);

        // Выводим сообщение в успешном случае или при ошибке
        if (newRowId == -1) {
            // Если ID  -1, значит произошла ошибка
            Toast.makeText(this, "Ошибка при добавлении компании", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Компания добавлена", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }


}