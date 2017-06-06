package com.dbeqiraj.guideapp.dbtools;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.compat.BuildConfig;

public class DBProvider extends ContentProvider {

    private DBHelper mOpenHelper;
    private SQLiteDatabase db;
    static final String PROVIDER_NAME   =   BuildConfig.APPLICATION_ID;
    static final String URL             =   "content://" + PROVIDER_NAME + "/"+ DBHelper.DB_NAME;
    public static final Uri CONTENT_URI =   Uri.parse(URL);

    private String table_name   =   "";
    private Context context     =   null;

    public void initializeMOpenHelper(Context context){
        mOpenHelper = new DBHelper(context);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;

    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        /* Try to insert a record. */
        db = mOpenHelper.getWritableDatabase();

        long rowID = db.insert(	table_name, "", values);

        /* If record is added successfully. */

        if (rowID > 0)
        {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            context.getContentResolver().notifyChange(_uri, null);
            db.close();
            return _uri;
        }
        db.close();
        throw new SQLException("Failed to add a record in table " + table_name);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // TODO Auto-generated method stub
        db = mOpenHelper.getWritableDatabase();
        int result = db.delete(table_name, selection, selectionArgs);
        db.close();
        return result;
    }


    @Override
    public Cursor query(Uri uri, String[] columns, String selection, String[] selectionArgs, String orderBy) {
        // TODO Auto-generated method stub

        db = mOpenHelper.getWritableDatabase();
        // db.close();
        return db.query(table_name, columns, selection, selectionArgs, null, null, orderBy);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // TODO Auto-generated method stub

        db = mOpenHelper.getWritableDatabase();
        int result = db.update(table_name, values, selection, selectionArgs);
        db.close();
        return result;
    }

    @Override
    public String getType(Uri uri) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean onCreate() {
        // TODO Auto-generated method stub
        return false;
    }
}
