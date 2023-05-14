package com.example.busticketing;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DataHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ticketDB.db";
    private static final int DATABASE_VERSION = 1;

    public DataHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE sesi(\n" +
                "    idUser INTEGER NOT NULL,\n" +
                "    cond int(5) NOT NULL,\n" +
                "    FOREIGN KEY (idUser) REFERENCES account(idUser)\n" +
                ");\n";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql ="Delete FROM sesi";
        db.execSQL(sql);
    }
}
