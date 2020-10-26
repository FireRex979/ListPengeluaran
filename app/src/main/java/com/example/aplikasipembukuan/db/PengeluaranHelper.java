package com.example.aplikasipembukuan.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.aplikasipembukuan.entity.Pengeluaran;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.aplikasipembukuan.db.DatabaseContract.PengeluaranColumns.BARANG;
import static com.example.aplikasipembukuan.db.DatabaseContract.PengeluaranColumns.HARGA;
import static com.example.aplikasipembukuan.db.DatabaseContract.PengeluaranColumns.JUMLAH;
import static com.example.aplikasipembukuan.db.DatabaseContract.PengeluaranColumns.TGLPENGELUARAN;
import static com.example.aplikasipembukuan.db.DatabaseContract.PengeluaranColumns.TOTAL;
import static com.example.aplikasipembukuan.db.DatabaseContract.TABLE_PENGELUARAN;

public class PengeluaranHelper {
    private static String DATABASE_TABLE = TABLE_PENGELUARAN;
    private Context context;
    private DatabaseHelper databaseHelper;

    private SQLiteDatabase database;

    public PengeluaranHelper(Context context){
        this.context = context;
    }

    public PengeluaranHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        databaseHelper.close();
    }

    public ArrayList<Pengeluaran> query(){
        ArrayList<Pengeluaran> arrayList = new ArrayList<Pengeluaran>();
        Cursor cursor = database.query(DATABASE_TABLE,null,null,null,null,null,_ID+" DESC", null);
        cursor.moveToFirst();
        Pengeluaran pengeluaran;
        if (cursor.getCount()>0){
            do {
                pengeluaran = new Pengeluaran();
                pengeluaran.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                pengeluaran.setBarang(cursor.getString(cursor.getColumnIndexOrThrow(BARANG)));
                pengeluaran.setJumlah(cursor.getInt(cursor.getColumnIndexOrThrow(JUMLAH)));
                pengeluaran.setHarga(cursor.getInt(cursor.getColumnIndexOrThrow(HARGA)));
                pengeluaran.setTotal(cursor.getInt(cursor.getColumnIndexOrThrow(TOTAL)));
                pengeluaran.setTglPengeluaran(cursor.getString(cursor.getColumnIndexOrThrow(TGLPENGELUARAN)));
                arrayList.add(pengeluaran);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(Pengeluaran pengeluaran){
        ContentValues initialValues = new ContentValues();
        initialValues.put(BARANG, pengeluaran.getBarang());
        initialValues.put(JUMLAH, pengeluaran.getJumlah());
        initialValues.put(HARGA, pengeluaran.getHarga());
        initialValues.put(TOTAL, pengeluaran.getTotal());
        initialValues.put(TGLPENGELUARAN, pengeluaran.getTglPengeluaran());
        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    public int update(Pengeluaran pengeluaran){
        ContentValues args = new ContentValues();
        args.put(BARANG, pengeluaran.getBarang());
        args.put(JUMLAH, pengeluaran.getJumlah());
        args.put(HARGA, pengeluaran.getHarga());
        args.put(TOTAL, pengeluaran.getTotal());
        args.put(TGLPENGELUARAN, pengeluaran.getTglPengeluaran());
        return database.update(TABLE_PENGELUARAN, args, _ID+ " = '"+pengeluaran.getId() + "'", null);
    }

    public int delete(int id){
        return database.delete(TABLE_PENGELUARAN, _ID + " = '" + id + "'", null);
    }
}
