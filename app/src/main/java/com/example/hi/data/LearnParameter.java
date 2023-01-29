package com.example.hi.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

//存储已学的单词数据库,结构和单词数据库一样
public class LearnParameter extends SQLiteOpenHelper {

    public LearnParameter(@Nullable Context context) {
        super(context,"LearnData", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建一个叫WordsData的表
        db.execSQL("create table LearnData(id integer primary key autoincrement," +
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
