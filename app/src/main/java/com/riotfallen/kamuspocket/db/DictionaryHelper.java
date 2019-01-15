package com.riotfallen.kamuspocket.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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

    public long insert(Dictionary dictionary, boolean isEnglish) {
        String tableName = isEnglish ? TABLE_ENG_IND : TABLE_IND_ENG;
        ContentValues initialValues = new ContentValues();
        initialValues.put(WORD, dictionary.getWord());
        initialValues.put(MEANING, dictionary.getMeaning());
        return database.insert(tableName, null, initialValues);
    }

    public int delete(int id, boolean isEnglish) {
        String tableName = isEnglish ? TABLE_ENG_IND : TABLE_IND_ENG;
        return database.delete(tableName, _ID + " = '" + id + "'", null);
    }
}
