package com.koreait.exampledatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String NAME = "user.db";
    public static int VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    // 데이터베이스가 생성될 때 호출되는 콜백 메서드
    @Override
    public void onCreate(SQLiteDatabase db) {
        println("onCreate 호출됨");

        String sql = "create table if not exists emp("
                + "_id integer PRIMARY KEY autoincrement,"
                + " name text,"
                + " age integer,"
                + " mobile text)";

        db.execSQL(sql);
    }

   // 데이터베이스가 열렸을 때 호출되는 콜백 메서드
    @Override
    public void onOpen(SQLiteDatabase db) {
        println("onOpen 호출됨");

        String sql = "INSERT INTO emp(name, age, mobile) VALUES('남현우', 13, '010-1234-5678')";

             db.execSQL(sql);
    }

    // 데이터베이스를 업그레이드할 때 호출되는 콜백 메서드
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        println("onUpgrade 호출됨 :" + oldVersion + "->" + newVersion);

        if(newVersion > 1) {
            db.execSQL("drop table if exists emp");
        }
    }

    public void println(String message) {
        Log.d("DatabaseHelper", message);
    }
}
