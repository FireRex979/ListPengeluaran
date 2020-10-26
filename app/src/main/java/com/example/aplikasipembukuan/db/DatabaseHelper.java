package com.example.aplikasipembukuan.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "dbpengeluaranapp";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABLE_PENGELUARAN = String.format( "CREATE TABLE %s"+
            " (%s INTEGER PRIMARY KEY AUTOINCREMENT,"+ //id
            " %s TEXT NOT NULL,"+  //barang
            " %s INTEGER NOT NULL,"+ //jumlah
            " %s INTEGER NOT NULL,"+ //harga
            " %s INTEGER NOT NULL,"+ //total
            " %s TEXT NOT NULL)", //tgl_pengeluaran
            DatabaseContract.TABLE_PENGELUARAN,
            DatabaseContract.PengeluaranColumns._ID,
            DatabaseContract.PengeluaranColumns.BARANG,
            DatabaseContract.PengeluaranColumns.JUMLAH,
            DatabaseContract.PengeluaranColumns.HARGA,
            DatabaseContract.PengeluaranColumns.TOTAL,
            DatabaseContract.PengeluaranColumns.TGLPENGELUARAN
    );

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_PENGELUARAN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_PENGELUARAN);
        onCreate(db);
    }
}
