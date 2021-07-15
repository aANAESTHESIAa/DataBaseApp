package com.aanaesthesiaa.databaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.aanaesthesiaa.databaseapp.data.EmployeeContract.EmployeeEntry;
import com.aanaesthesiaa.databaseapp.data.DbHelper;

public class AddEmployeeActivity extends AppCompatActivity {
    private Spinner mGenderSpinner;
    private EditText mNameEditText;
    private EditText mLastnameEditText;
    private EditText mCityEditText;
    private EditText mAgeEditText;
    long userId = 0;
    private int mGender = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        mGenderSpinner = (Spinner) findViewById(R.id.AEspinner);
        mNameEditText = (EditText) findViewById(R.id.AEedittextName);
        mLastnameEditText = (EditText) findViewById(R.id.AEedittextLastname);
        mCityEditText = (EditText) findViewById(R.id.AEedittextCity);
        mAgeEditText = (EditText) findViewById(R.id.AEedittextAge);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getLong("id");

        }

        setupSpinner();
        Button AddEmployee = (Button) findViewById(R.id.AEbtnAddemployee);
        AddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEmployee();
                Intent intent = new Intent(AddEmployeeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


    private void setupSpinner() {

        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mGenderSpinner.setAdapter(genderSpinnerAdapter);
        mGenderSpinner.setSelection(2);

        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_female))) {
//                        mGender = 0; // жен
                        mGender = EmployeeEntry.GENDER_FEMALE;
                    } else if (selection.equals(getString(R.string.gender_male))) {
//                        mGender = 1; // муж
                        mGender = EmployeeEntry.GENDER_MALE;
                    } else {
//                        mGender = 2; // Не определен
                        mGender = EmployeeEntry.GENDER_UNKNOWN;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = 2; // Unknown
            }
        });
    }
    private void addEmployee() {
        // Считываем данные из текстовых полей
        String name = mNameEditText.getText().toString().trim();
        String lastname = mLastnameEditText.getText().toString().trim();
        String city = mCityEditText.getText().toString().trim();
        String ageString = mAgeEditText.getText().toString().trim();
        int age = Integer.parseInt(ageString);

        DbHelper mDbHelper = new DbHelper(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EmployeeEntry.COLUMN_NAME, name);
        values.put(EmployeeEntry.COLUMN_LASTNAME, lastname);
        values.put(EmployeeEntry.COLUMN_CITY, city);
        values.put(EmployeeEntry.COLUMN_GENDER, mGender);
        values.put(EmployeeEntry.COLUMN_AGE, age);
        values.put(EmployeeEntry.COLUMN_DEPARTMENT, userId);


        // Вставляем новый ряд в базу данных
        long newRowId = db.insert(EmployeeEntry.TABLE_NAME, null, values);

        // Выводим сообщение в успешном случае или при ошибке
        if (newRowId == -1) {
            // Если ID  -1, значит произошла ошибка
            Toast.makeText(this, "Ошибка при добавлении сотрудника", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Сотрудник добавлен  ", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
}