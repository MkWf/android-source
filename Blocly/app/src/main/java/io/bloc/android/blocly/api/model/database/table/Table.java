package io.bloc.android.blocly.api.model.database.table;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Mark on 1/19/2015.
 */
public abstract class Table {

    public static interface Builder {
        public long insert(SQLiteDatabase writableDB);
    }

    protected static final String COLUMN_ID = "id";

    public abstract String getName();

    public abstract String getCreateStatement();

    public void onUpgrade(SQLiteDatabase writableDatabase, int oldVersion, int newVersion) {
        // Nothing
    }
}
