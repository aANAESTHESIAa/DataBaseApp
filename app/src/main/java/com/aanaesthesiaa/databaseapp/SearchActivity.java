package com.aanaesthesiaa.databaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.aanaesthesiaa.databaseapp.data.CompanyContract;
import com.aanaesthesiaa.databaseapp.data.DepartmentContract;
import com.aanaesthesiaa.databaseapp.data.EmployeeContract;
import com.aanaesthesiaa.databaseapp.data.DbHelper;

public class SearchActivity extends AppCompatActivity {
    ListView searchList;
    Cursor searchCursor;
    SimpleCursorAdapter searchAdapter;
    private DbHelper mDbHelper;
    String lastname;
    EditText lastnameBox;
    private static final String TAG = SearchActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        lastnameBox = (EditText) findViewById(R.id.SearchEditTextLastName);
        searchList = (ListView)findViewById(R.id.SearchListView);

        Button ExecSearch = (Button) findViewById(R.id.SearchBtn);
        ExecSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                displaySearchRes();
            }
        });

        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), UpdDelEmployeeActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }});
    }

    private void displaySearchRes() {
        lastname = lastnameBox.getText().toString().trim();
        // откроем для чтения базу данных
        mDbHelper = new DbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

            //------------------------------------------------------------------------вывод списка компаний------------------------------------
            // открываем подключение


            //получаем данные из бд в виде курсора
            searchCursor =  db.rawQuery("select * from "+ EmployeeContract.EmployeeEntry.TABLE_NAME + " WHERE " + EmployeeContract.EmployeeEntry.COLUMN_LASTNAME+  " = ?", new String[] {lastname});
            String[] headers = new String[] {EmployeeContract.EmployeeEntry.COLUMN_NAME , EmployeeContract.EmployeeEntry.COLUMN_CITY};           // TODO: fix приделать отображение компании
            // создаем адаптер, передаем в него курсор
            searchAdapter = new SimpleCursorAdapter(this,android.R.layout.two_line_list_item,
                    searchCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
            searchList.setAdapter(searchAdapter);
            //------------------------------------------------------------------------------------------------------------------------------------
    db.close();
    }









//    SQLiteDatabase db = this.getReadableDatabase();
//    Cursor c = db.rawQuery("SELECT column1,column2,column3 FROM table ", null);
//if (c.moveToFirst()){
//        do {
//            // Passing values
//            String column1 = c.getString(0);
//            String column2 = c.getString(1);
//            String column3 = c.getString(2);
//            // Do something Here with values
//        } while(c.moveToNext());
//    }
//c.close();
//db.close();

}