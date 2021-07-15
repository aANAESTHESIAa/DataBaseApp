package com.aanaesthesiaa.databaseapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import androidx.navigation.ui.AppBarConfiguration;

import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import com.aanaesthesiaa.databaseapp.data.CompanyContract;
import com.aanaesthesiaa.databaseapp.databinding.ActivityMainBinding;
import android.widget.ListView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.aanaesthesiaa.databaseapp.data.CompanyContract.CompanyEntry;
import com.aanaesthesiaa.databaseapp.data.DbHelper;


public class MainActivity extends AppCompatActivity {
    ListView companyList;
    Cursor companyCursor;
    SimpleCursorAdapter companyAdapter;

    private DbHelper mDbHelper;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        companyList = (ListView) findViewById(R.id.listCompany);
        companyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), UpdDelCompanyActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }});

        setSupportActionBar(binding.toolbar);
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddOrderActivity.class);
                startActivity(intent);
            }
        });
        mDbHelper = new DbHelper(this);



        Button AddCompany = (Button) findViewById(R.id.btnAddCompany);
        AddCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCompanyActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent myIntent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(myIntent);
                return true;
        }



        return super.onOptionsItemSelected(item);
    }
    private void displayDatabaseInfo() {
        // откроем для чтения базу данных
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

            //------------------------------------------------------------------------вывод списка компаний------------------------------------
            // открываем подключение
            companyList = (ListView)findViewById(R.id.listCompany);
            //получаем данные из бд в виде курсора
            companyCursor =  db.rawQuery("select * from "+ CompanyContract.CompanyEntry.TABLE_NAME, null);
            // определяем, какие столбцы из курсора будут выводиться в ListView
            String[] headers = new String[] {CompanyEntry.COLUMN_NAME, CompanyEntry.COLUMN_CITY};
            // создаем адаптер, передаем в него курсор
            companyAdapter = new SimpleCursorAdapter(this,android.R.layout.two_line_list_item,
                    companyCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
            companyList.setAdapter(companyAdapter);
            //------------------------------------------------------------------------------------------------------------------------------------
            db.close();
    }
}


//TODO: отображение заказа с ценами и т д
// TODO: сделать поиск чего нибудь по параметрам
