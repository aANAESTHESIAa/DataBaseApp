package com.aanaesthesiaa.databaseapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import com.aanaesthesiaa.databaseapp.data.CompanyContract;
import com.aanaesthesiaa.databaseapp.data.OrderContract;
import com.aanaesthesiaa.databaseapp.data.DbHelper;

import java.util.UUID;

public class AddOrderActivity extends AppCompatActivity {

    private EditText mNameEditText;
    private DbHelper mDbHelper;
    private EditText mCityEditText;
    private EditText mPhoneEditText;
    private Cursor companyCursor;
    SimpleCursorAdapter companyAdapter;
    Spinner mCompanySpinner;
    long selectedCompanyId = 0;
    String uniqueId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mCompanySpinner = (Spinner) findViewById(R.id.spinner_company);

        mNameEditText = (EditText) findViewById(R.id.AOedittextName);
        mCityEditText = (EditText) findViewById(R.id.AOedittextCity);
        mPhoneEditText = (EditText) findViewById(R.id.AOedittextPhone);
        setupSpinner();
        Button AddItem = (Button) findViewById(R.id.AObtnAddItem);
        uniqueId = UUID.randomUUID().toString();
        AddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertOrder();
                Intent intent = new Intent(AddOrderActivity.this, AddItemActivity.class);
                intent.putExtra("id", uniqueId);
                startActivity(intent);
            }
        });
    }


    /**
     * Настраиваем spinner для выбора контрагента.
     */
    private void setupSpinner() {

        // откроем для чтения базу данных
        mDbHelper = new DbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        //------------------------------------------------------------------------вывод списка компаний------------------------------------
        // открываем подключение
        //получаем данные из бд в виде курсора
        companyCursor =  db.rawQuery("select * from "+ CompanyContract.CompanyEntry.TABLE_NAME,null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[] {CompanyContract.CompanyEntry.COLUMN_NAME};
        // создаем адаптер, передаем в него курсор
        companyAdapter = new SimpleCursorAdapter(this,android.R.layout.simple_spinner_item,
                companyCursor, headers, new int[]{android.R.id.text1}, 0);
        mCompanySpinner.setAdapter(companyAdapter);
        companyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCompanyId = id;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        mCompanySpinner.setOnItemSelectedListener(itemSelectedListener);

    }


    private void insertOrder() {
        // Считываем данные из текстовых полей
        String name = mNameEditText.getText().toString().trim();
        String phone = mPhoneEditText.getText().toString().trim();
        String city = mCityEditText.getText().toString().trim();
        String id =  String.valueOf(selectedCompanyId);

        DbHelper mDbHelper = new DbHelper(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(OrderContract.OrderEntry.COLUMN_NAME, name);
        values.put(OrderContract.OrderEntry.COLUMN_PHONE, phone);
        values.put(OrderContract.OrderEntry.COLUMN_CITY, city);
        values.put(OrderContract.OrderEntry.COLUMN_COMPANY, id);
        values.put(OrderContract.OrderEntry.COLUMN_ORDER_NUMBER, uniqueId);

        long newRowId = db.insert(OrderContract.OrderEntry.TABLE_NAME, null, values);

        // Выводим сообщение в успешном случае или при ошибке
        if (newRowId == -1) {
            // Если ID  -1, значит произошла ошибка
            Toast.makeText(this, "Ошибка при добавлении Заказа", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Заказ добавлен", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
}

