package com.example.hi.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//该数据负责管理用户账户信息
import androidx.annotation.Nullable;

//用户信息数据库
public class UserParameter extends SQLiteOpenHelper {

    public UserParameter(Context context) {
        super(context, "UserData", null, 1);
    }

    public UserParameter(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建一个叫UserData的表，id，uname,upwd,snumber四个属性
        db.execSQL("create table UserData(id integer primary key autoincrement," +
                "uname text not null," +
                "upwd text not null,"+
                "snumber text not null)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
