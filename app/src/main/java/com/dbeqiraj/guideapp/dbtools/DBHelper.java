package com.dbeqiraj.guideapp.dbtools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION                   = 1;
    public  static final String DB_NAME                         = "spots";

    public static final String TABLE_SPOT                       = "spot";
    public static final String TABLE_SPOT_FIELD_ID              = "ID";
    public static final String TABLE_SPOT_FIELD_NAME            = "NAME";
    public static final String TABLE_SPOT_FIELD_DISTANCE        = "DISTANCE";
    public static final String TABLE_SPOT_FIELD_DESCRIPTION     = "DESCRIPTION";

    private static final String CREATE_TABLE_SPOT = "CREATE TABLE IF NOT EXISTS "
            + TABLE_SPOT
            + " ("
            + TABLE_SPOT_FIELD_ID            + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TABLE_SPOT_FIELD_NAME          + " TEXT NOT NULL, "
            + TABLE_SPOT_FIELD_DISTANCE      + " TEXT NOT NULL, "
            + TABLE_SPOT_FIELD_DESCRIPTION   + " TEXT NOT NULL "
            +")";

    DBHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

        db.execSQL(CREATE_TABLE_SPOT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPOT);
        onCreate(db);
    }
}
