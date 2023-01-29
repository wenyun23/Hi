package com.example.hi.tool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hi.data.CollectParameter;
import com.example.hi.data.English_ETC4_Factor;
import com.example.hi.data.English_ETC4_Parameter;
import com.example.hi.data.LearnParameter;

import java.util.ArrayList;

//此类用于查询数据库，并添加到集合之中
public class DBUtil {
    public final SQLiteDatabase dbe;
    public final SQLiteDatabase dbl;
    public final SQLiteDatabase dbc;

    public DBUtil(Context context) {
        dbe=new English_ETC4_Parameter(context).getWritableDatabase();
        dbl= new LearnParameter(context).getWritableDatabase();
        dbc= new CollectParameter(context).getWritableDatabase();
    }

    public void initDiary_e(ArrayList<English_ETC4_Factor> liste){
        Cursor cursore=dbe.query("ETC4_Data",null,null,null,null,null,null);
        if (cursore.moveToFirst()){
            do {
                @SuppressLint("Range")
                int id=cursore.getInt(cursore.getColumnIndex("id"));
                @SuppressLint("Range")
                String wordHead=cursore.getString(cursore.getColumnIndex("wordHead"));
                @SuppressLint("Range")
                String usphone = cursore.getString(cursore.getColumnIndex("usphone"));
                @SuppressLint("Range")
                String spos = cursore.getString(cursore.getColumnIndex("spos"));
                @SuppressLint("Range")
                String stran = cursore.getString(cursore.getColumnIndex("stran"));
                @SuppressLint("Range")
                String descj=cursore.getString(cursore.getColumnIndex("descj"));
                @SuppressLint("Range")
                String w = cursore.getString(cursore.getColumnIndex("w"));
                @SuppressLint("Range")
                String desj = cursore.getString(cursore.getColumnIndex("desj"));
                @SuppressLint("Range")
                String sContent = cursore.getString(cursore.getColumnIndex("sContent"));
                @SuppressLint("Range")
                String sCn = cursore.getString(cursore.getColumnIndex("sCn"));
                @SuppressLint("Range")
                String tranOther=cursore.getString(cursore.getColumnIndex("tranOther"));

                English_ETC4_Factor english_etc4_factor=new English_ETC4_Factor
                        (id,wordHead,usphone,spos,stran,descj,w,desj,sContent,sCn,tranOther);

                liste.add(english_etc4_factor);
            }while (cursore.moveToNext());
        }
        cursore.close();
    }

    public void initDiary_l(ArrayList<English_ETC4_Factor> listl){
        Cursor cursorl= dbl.query("LearnData",null,null,null,null,null,null);

        if (cursorl.moveToFirst()){
            do {
                @SuppressLint("Range")
                int id=cursorl.getInt(cursorl.getColumnIndex("id"));
                @SuppressLint("Range")
                String wordHead=cursorl.getString(cursorl.getColumnIndex("wordHead"));
                @SuppressLint("Range")
                String usphone = cursorl.getString(cursorl.getColumnIndex("usphone"));
                @SuppressLint("Range")
                String spos = cursorl.getString(cursorl.getColumnIndex("spos"));
                @SuppressLint("Range")
                String stran = cursorl.getString(cursorl.getColumnIndex("stran"));
                @SuppressLint("Range")
                String descj=cursorl.getString(cursorl.getColumnIndex("descj"));
                @SuppressLint("Range")
                String w = cursorl.getString(cursorl.getColumnIndex("w"));
                @SuppressLint("Range")
                String desj = cursorl.getString(cursorl.getColumnIndex("desj"));
                @SuppressLint("Range")
                String sContent = cursorl.getString(cursorl.getColumnIndex("sContent"));
                @SuppressLint("Range")
                String sCn = cursorl.getString(cursorl.getColumnIndex("sCn"));
                @SuppressLint("Range")
                String tranOther=cursorl.getString(cursorl.getColumnIndex("tranOther"));

                English_ETC4_Factor english_etc4_factor=new English_ETC4_Factor
                        (id,wordHead,usphone,spos,stran,descj,w,desj,sContent,sCn,tranOther);

                listl.add(english_etc4_factor);
            }while (cursorl.moveToNext());
        }
        cursorl.close();
    }

    public void initDiary_c(ArrayList<English_ETC4_Factor> listc){
        Cursor cursorc=dbc.query("CollectData",null,null,null,null,null,null);
        if (cursorc.moveToFirst()){
            do {
                @SuppressLint("Range")
                int id=cursorc.getInt(cursorc.getColumnIndex("id"));
                @SuppressLint("Range")
                String wordHead=cursorc.getString(cursorc.getColumnIndex("wordHead"));
                @SuppressLint("Range")
                String usphone = cursorc.getString(cursorc.getColumnIndex("usphone"));
                @SuppressLint("Range")
                String spos = cursorc.getString(cursorc.getColumnIndex("spos"));
                @SuppressLint("Range")
                String stran = cursorc.getString(cursorc.getColumnIndex("stran"));
                @SuppressLint("Range")
                String descj=cursorc.getString(cursorc.getColumnIndex("descj"));
                @SuppressLint("Range")
                String w = cursorc.getString(cursorc.getColumnIndex("w"));
                @SuppressLint("Range")
                String desj = cursorc.getString(cursorc.getColumnIndex("desj"));
                @SuppressLint("Range")
                String sContent = cursorc.getString(cursorc.getColumnIndex("sContent"));
                @SuppressLint("Range")
                String sCn = cursorc.getString(cursorc.getColumnIndex("sCn"));
                @SuppressLint("Range")
                String tranOther=cursorc.getString(cursorc.getColumnIndex("tranOther"));

                English_ETC4_Factor english_etc4_factor=new English_ETC4_Factor
                        (id,wordHead,usphone,spos,stran,descj,w,desj,sContent,sCn,tranOther);

                listc.add(english_etc4_factor);
            }while (cursorc.moveToNext());
        }
        cursorc.close();
    }
}
