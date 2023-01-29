package com.example.hi.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class English_ETC4_Parameter extends SQLiteOpenHelper {

    public English_ETC4_Parameter(@Nullable Context context ) {
        super(context,"ETC4_Data", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建一个叫CollectData的表
        db.execSQL("create table ETC4_Data(id integer primary key autoincrement," +
                "wordHead text not null," +
                "usphone text not null,"+
                "spos text not null," +
                "stran text not null," +
                "descj text not null,"+
                "w text not null," +
                "desj text not null," +
                "sContent text not null,"+
                "sCn text not null," +
                "tranOther text not null)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
