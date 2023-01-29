package com.example.hi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hi.data.UserFactor;
import com.example.hi.data.UserParameter;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

//“Hi”英语单词软件————logo界面
public class DownActivity extends AppCompatActivity {
    private final ArrayList<UserFactor> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down);

        ride();//隐藏标题栏

        //将数据库的内容放到list集合中
        initDiary();

        count_down();//倒计时
    }

    //隐藏状态栏
    private void ride(){
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    //logo “1.5”秒载入登录、主界面。
    private void count_down(){
        Timer timer=new Timer();
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                //判断数据中是否注册过用户，如果有用户就直接到主界面
                if (list.size()>0){
                    Intent intent=new Intent(DownActivity.this, MainActivity.class);
                    //利用bundle传入用户名,和“HI”id
                    Bundle bundle = new Bundle();
                    bundle.putString("uname",list.get(0).getUname());
                    bundle.putString("snumber",list.get(0).getSnumber());
                    intent.putExtras(bundle);

                    startActivity(intent);
                }else startActivity(new Intent(DownActivity.this, logonActivity.class));

                finish();
            }
        };timer.schedule(timerTask,1500);
    }

    //利用游标cursor从用户数据库里面拿数据，并放到list集合中
    private void initDiary(){
        //初始化数据库
        SQLiteDatabase db=new UserParameter(DownActivity.this).getWritableDatabase();

        Cursor cursor=db.query("UserData",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range")
                int id=cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range")
                String uname=cursor.getString(cursor.getColumnIndex("uname"));
                @SuppressLint("Range")
                String upwd = cursor.getString(cursor.getColumnIndex("upwd"));
                @SuppressLint("Range")
                String snumber = cursor.getString(cursor.getColumnIndex("snumber"));
                UserFactor userFactor=new UserFactor(id,uname,upwd,snumber);

                list.add(userFactor);

            }while (cursor.moveToNext());
        }
        cursor.close();
    }
}
