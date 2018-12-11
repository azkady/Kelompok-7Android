package com.example.vfairoh.projwish.SQLiteOperations;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "WishList.db";
    private static final int DATABASE_VERSION = 1;

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE WishList (idwL integer primary key, detail text null, jumlahUang integer);";
        Log.d("Data", "onCreate:" + sql);
        db.execSQL(sql);

        String sql1 = "CREATE TABLE Transaksi (idTrans integer primary key, timestamp datetime default current_timestamp,  type text null, detail text null, jumlahUang integer);";
        Log.d("Data", "onCreate:" + sql1);
        db.execSQL(sql1);

        String sql2 = "CREATE TABLE Rekap (idRekap integer primary key, timestamp text null,  totalMasuk integer , totalLuar integer, uangSisa integer);";
        Log.d("Data", "onCreate:" + sql2);
        db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS WishList");
            onCreate(db);

            db.execSQL("DROP TABLE IF EXISTS Transaksi");
            onCreate(db);

            db.execSQL("DROP TABLE IF EXISTS Rekap");
            onCreate(db);
        }
    }
}

