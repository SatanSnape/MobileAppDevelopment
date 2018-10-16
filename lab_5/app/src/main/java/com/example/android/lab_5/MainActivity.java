package com.example.android.lab_5;

import android.content.ContentValues;
import android.content.Context;
import android.os.strictmode.CleartextNetworkViolation;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ConcurrentModificationException;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int CM_DELETE_ID = 1;

    Button btnAdd, btnClear;
    EditText etName, etEmail;
    ListView userList;
    DB db;
    SimpleCursorAdapter scAdapter;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // открываем подключение к БД
        db = new DB(this);
        db.open();

        // формируем столбцы сопоставления
        String[] from = new String[] { DB.COLUMN_NAME, DB.COLUMN_EMAIL, DB.COLUMN_DATE };
        int[] to = new int[] { R.id.tvName, R.id.tvEmail, R.id.tvDate };

        // создаем адаптер и настраиваем список
        scAdapter = new SimpleCursorAdapter(this, R.layout.item, null, from, to, 0);
        userList = (ListView) findViewById(R.id.lvUserList);
        userList.setAdapter(scAdapter);

        // добавляем контекстное меню к списку
        registerForContextMenu(userList);

        // создаем лоадер для чтения данных
        getSupportLoaderManager().initLoader(0, null, this);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnClear = (Button) findViewById(R.id.btnClear);
        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);

        toast =  Toast.makeText(getApplicationContext(),"Toast", Toast.LENGTH_SHORT);
    }

    // обработка нажатия кнопки Add
    public void onAddClick(View view) {
        // добавляем запись
        Log.println(Log.ASSERT, "MY_LOG", "New item added");

        // Если поля пустые, просим заполнить их
        if (etName.getText().toString().isEmpty() || etEmail.getText().toString().isEmpty()){
            toast.setText("You must to fill all fields!");
            toast.show();
        } else {
            db.addRec(etName.getText().toString(), etEmail.getText().toString());
            etName.setText("");
            etEmail.setText("");
            toast.setText("Item added.");
            toast.show();
        }
        // получаем новый курсор с данными
        getSupportLoaderManager().getLoader(0).forceLoad();
    }

    // обработка нажатия кнопки Clear
    public void onClearClick(View view) {
        // добавляем запись
        Log.println(Log.ASSERT, "MY_LOG", "Database cleared");
        db.clear();
        toast.setText("Database was cleared.");
        toast.show();
        // получаем новый курсор с данными
        getSupportLoaderManager().getLoader(0).forceLoad();
    }

    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CM_DELETE_ID, 0, "Remove");
    }

    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CM_DELETE_ID) {
            // получаем из пункта контекстного меню данные по пункту списка
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item
                    .getMenuInfo();
            // извлекаем id записи и удаляем соответствующую запись в БД
            db.delRec(acmi.id);
            toast.setText("Item removed.");
            toast.show();
            // получаем новый курсор с данными
            getSupportLoaderManager().getLoader(0).forceLoad();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    protected void onDestroy() {
        super.onDestroy();
        // закрываем подключение при выходе
        db.close();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bndl) {
        return new MyCursorLoader(this, db);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        scAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
    }

    static class MyCursorLoader extends CursorLoader {

        DB db;

        public MyCursorLoader(Context context, DB db) {
            super(context);
            this.db = db;
        }

        @Override
        public Cursor loadInBackground() {
            Cursor cursor = db.getAllData();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return cursor;
        }

    }
}
