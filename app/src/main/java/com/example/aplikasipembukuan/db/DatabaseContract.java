package com.example.aplikasipembukuan.db;

import android.provider.BaseColumns;

public class DatabaseContract {
    static String TABLE_PENGELUARAN = "pengeluaran";

    static final class PengeluaranColumns implements BaseColumns{
        static String BARANG = "barang";
        static String  HARGA = "harga";
        static String JUMLAH = "jumlah";
        static String TOTAL = "total";
        static String TGLPENGELUARAN = "tgl_pengeluaran";
    }
}
