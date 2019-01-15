package com.riotfallen.kamuspocket.db;

import android.provider.BaseColumns;

class DatabaseContract {

    static String TABLE_ENG_IND = "table_eng_ind";
    static String TABLE_IND_ENG = "table_ind_eng";

    static final class DictionaryColumn implements BaseColumns {

        // Column Kata
        static String WORD = "word";

        // Column Penjelasan
        static String MEANING = "meaning";
    }

}
