package com.riotfallen.kamuspocket.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.riotfallen.kamuspocket.db.DatabaseContract.DictionaryColumn.MEANING;
import static com.riotfallen.kamuspocket.db.DatabaseContract.DictionaryColumn.WORD;
import static com.riotfallen.kamuspocket.db.DatabaseContract.TABLE_ENG_IND;
import static com.riotfallen.kamuspocket.db.DatabaseContract.TABLE_IND_ENG;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "dbdictionary";

    private static final int DATABASE_VERSION = 1;

    private static String CREATE_TABLE_ENG_IND = "create table " + TABLE_ENG_IND +
            " (" + _ID + " integer primary key autoincrement, " +
            WORD + " text not null, " +
            MEANING + " text not null);";

    private static String CREATE_TABLE_IND_ENG = "create table " + TABLE_IND_ENG +
            " (" + _ID + " integer primary key autoincrement, " +
            WORD + " text not null, " +
            MEANING + " text not null);";

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ENG_IND);
        db.execSQL(CREATE_TABLE_IND_ENG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENG_IND);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IND_ENG);
    }
}
