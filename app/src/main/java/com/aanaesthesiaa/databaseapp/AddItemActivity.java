package com.aanaesthesiaa.databaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.aanaesthesiaa.databaseapp.data.OIContract;
import com.aanaesthesiaa.databaseapp.data.DbHelper;

public class AddItemActivity extends AppCompatActivity {
    private CheckBox ChBOS;
    private CheckBox ChBThermo;
    private CheckBox ChBFix;
    private CheckBox ChBBox;
    private CheckBox ChBPo;
    private CheckBox ChBCompile;
    private CheckBox ChBConfigure;
    String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        Button AddItem = (Button) findViewById(R.id.AIbtnSave);
        ChBOS = (CheckBox) findViewById(R.id.checkBoxOS);
        ChBThermo = (CheckBox) findViewById(R.id.checkBoxThermo);
        ChBFix = (CheckBox) findViewById(R.id.checkBoxFix);
        ChBBox = (CheckBox) findViewById(R.id.checkBoxBox);
        ChBPo = (CheckBox) findViewById(R.id.checkBoxPO);
        ChBCompile = (CheckBox) findViewById(R.id.checkBoxcompile);
        ChBConfigure = (CheckBox) findViewById(R.id.checkBoxConfigure);

        AddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertItemOrder();
                Intent intent = new Intent(AddItemActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }


    private void insertItemOrder() {
        DbHelper mDbHelper = new DbHelper(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            orderId = extras.getString("id");

        }

        if (ChBOS.isChecked()){
            ContentValues values = new ContentValues();
            values.put(OIContract.OIEntry.COLUMN_ITEM, 2);
            values.put(OIContract.OIEntry.COLUMN_ORDER, orderId);
            db.insert(OIContract.OIEntry.TABLE_NAME, null, values);
        }
        if (ChBThermo.isChecked()){
            ContentValues values = new ContentValues();
            values.put(OIContract.OIEntry.COLUMN_ITEM, 5);
            values.put(OIContract.OIEntry.COLUMN_ORDER, orderId);
            db.insert(OIContract.OIEntry.TABLE_NAME, null, values);
        }
        if (ChBFix.isChecked()){
            ContentValues values = new ContentValues();
            values.put(OIContract.OIEntry.COLUMN_ITEM, 6);
            values.put(OIContract.OIEntry.COLUMN_ORDER, orderId);
            db.insert(OIContract.OIEntry.TABLE_NAME, null, values);
        }
        if (ChBBox.isChecked()){
            ContentValues values = new ContentValues();
            values.put(OIContract.OIEntry.COLUMN_ITEM, 7);
            values.put(OIContract.OIEntry.COLUMN_ORDER, orderId);
            db.insert(OIContract.OIEntry.TABLE_NAME, null, values);
        }
        if (ChBPo.isChecked()){
            ContentValues values = new ContentValues();
            values.put(OIContract.OIEntry.COLUMN_ITEM, 1);
            values.put(OIContract.OIEntry.COLUMN_ORDER, orderId);
            db.insert(OIContract.OIEntry.TABLE_NAME, null, values);
        }
        if (ChBCompile.isChecked()){
            ContentValues values = new ContentValues();
            values.put(OIContract.OIEntry.COLUMN_ITEM, 3);
            values.put(OIContract.OIEntry.COLUMN_ORDER, orderId);
            db.insert(OIContract.OIEntry.TABLE_NAME, null, values);
        }
        if (ChBConfigure.isChecked()){
            ContentValues values = new ContentValues();
            values.put(OIContract.OIEntry.COLUMN_ITEM, 4);
            values.put(OIContract.OIEntry.COLUMN_ORDER, orderId);
            db.insert(OIContract.OIEntry.TABLE_NAME, null, values);
        }

            Toast.makeText(this, "Заказ №" +orderId + " успешно оформлен", Toast.LENGTH_SHORT).show();
        db.close();
        }
    }




