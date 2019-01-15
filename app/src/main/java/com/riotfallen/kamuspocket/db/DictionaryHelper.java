package com.riotfallen.kamuspocket.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.riotfallen.kamuspocket.model.Dictionary;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.riotfallen.kamuspocket.db.DatabaseContract.DictionaryColumn.MEANING;
import static com.riotfallen.kamuspocket.db.DatabaseContract.DictionaryColumn.WORD;
import static com.riotfallen.kamuspocket.db.DatabaseContract.TABLE_ENG_IND;
import static com.riotfallen.kamuspocket.db.DatabaseContract.TABLE_IND_ENG;

public class DictionaryHelper {

    private Context context;
    private DatabaseHelper databaseHelper;

    private SQLiteDatabase database;

    public DictionaryHelper(Context context) {
        this.context = context;
    }

    public DictionaryHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public ArrayList<Dictionary> getWordByName(String word, boolean isEnglish) {
        String tableName = isEnglish ? TABLE_ENG_IND : TABLE_IND_ENG;
        Cursor cursor = database.rawQuery("SELECT * FROM " + tableName + " WHERE " + WORD + " LIKE '%" + word + "%' ORDER BY " + WORD + " ASC;",
                null);
        cursor.moveToFirst();
        ArrayList<Dictionary> arrayList = new ArrayList<>();
        Dictionary dictionary;
        if (cursor.getCount() > 0) {
            do {
                dictionary = new Dictionary();
                dictionary.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                dictionary.setWord(cursor.getString(cursor.getColumnIndexOrThrow(WORD)));
                dictionary.setMeaning(cursor.getString(cursor.getColumnIndexOrThrow(MEANING)));
                arrayList.add(dictionary);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public void beginTransaction() {
        database.beginTransaction();
    }

    public void setTransactionSuccess() {
        database.setTransactionSuccessful();
    }

    public void endTransaction() {
        database.endTransaction();
    }


    public void insertTransaction(Dictionary dictionary, boolean isEnglish) {
        String tableName = isEnglish ? TABLE_ENG_IND : TABLE_IND_ENG;
        String sql = "INSERT INTO " + tableName + " (" + WORD + ", " + MEANING +
                ") VALUES (?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.bindString(1, dictionary.getWord());
        statement.bindString(2, dictionary.getMeaning());
        statement.execute();
        statement.clearBindings();
    }

    public int delete(int id, boolean isEnglish) {
        String tableName = isEnglish ? TABLE_ENG_IND : TABLE_IND_ENG;
        return database.delete(tableName, _ID + " = '" + id + "'", null);
    }
}
