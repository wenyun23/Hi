package com.example.hi;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hi.data.UserFactor;
import com.example.hi.data.UserParameter;
import com.example.hi.databinding.ActivityLogonBinding;
import com.example.hi.tool.MyDialog;

import java.util.ArrayList;

//登录界面，负责用户注册信息
public class logonActivity extends AppCompatActivity {
    private ActivityLogonBinding binding;
    private final ArrayList<UserFactor> list=new ArrayList<>();
    private int i=0;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon);

        binding=ActivityLogonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ride();//隐藏标题栏

        Agreement();//弹出协议

        initDiary();//将数据库的数据放入list集合

        //一键清理1
        binding.imgMisktake1.setOnClickListener(view -> binding.editLogonName.setText(""));

        //一键清理2
        binding.imgMisktake2.setOnClickListener(view -> binding.editLogonPwd.setText(""));

        //登录
        binding.btnLogon.setOnClickListener(view -> {
            //获取输入的值
            String uname=binding.editLogonName.getText().toString();
            String upwd=binding.editLogonPwd.getText().toString();

            if (!uname.isEmpty()){//判断账号名是否为空

                if (!upwd.isEmpty()){//判断密码是否为空

                    //判断用户是否勾选协议
                    if (binding.checkLogonBox.isChecked()){
                        Intent intent=new Intent(logonActivity.this,MainActivity.class);
                        int j=0;
                        for (;j<list.size();j++){//遍历匹配，看是否存在用户输入的账号
                            //如果存在，则跳出循环，反之循环遍历
                            if (list.get(j).getUname().equals(uname)&&list.get(j).getUpwd().equals(upwd)) {
                                break;
                            }
                        }
                        //当j的值登录账号集合的长度时，代表不存在该账号，提示
                        if (j==list.size()) {
                            Toast.makeText(this, "抱歉，该账户并不存在，请注册", Toast.LENGTH_SHORT).show();
                        }else {
                            //若是存在，开始登录“Hi”系统，并顺带利用bundle将用户信息传入，用于后面在我的界面展示
                            Bundle bundle = new Bundle();
                            bundle.putString("uname",uname);
                            bundle.putString("snumber",list.get(j).getSnumber());
                            intent.putExtras(bundle);
                            startActivity(intent,bundle);

                            Toast.makeText(this,"恭喜您，登录成功",Toast.LENGTH_SHORT).show();
                            finish();//记得关闭Activity哦
                        }

                    }else {
                        Toast.makeText(this,"请勾选Hi协议",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this,"Hi密码不能为空",Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this,"Hi账号名不能为空",Toast.LENGTH_SHORT).show();
            }
        });
        
        //点击注册
        binding.textRegister.setOnClickListener(view -> {
            //弹出预加载框，使效果显得更加真实
            new Asysnc().execute();
        });
    }

    // 隐藏状态栏
    private void ride(){
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    //实现协议弹窗
    private void Agreement() {
        MyDialog mDialog = new MyDialog(this,logonActivity.this);
        mDialog.setCancelable(false);
        mDialog.show();
        mDialog.getWindow().setLayout(800, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    //重写onActivityResult方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==23&&resultCode==RESULT_OK){
            Toast.makeText(this,"恭喜你成为了Hi用户",Toast.LENGTH_SHORT).show();

            //每当用户注册一个账号后，咋们的集合都要刷新哦。
            list.clear();
            initDiary();
        }
    }

    //利用AsyncTask，弹窗预加载窗口
    @SuppressLint("StaticFieldLeak")
    class Asysnc extends  AsyncTask<Void,Integer,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            do {
                publishProgress(i);
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i += 20;
            } while (i <=100);

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            dialog.setMessage("请稍等...");
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(logonActivity.this);
            dialog.setCancelable(false);
            dialog.show();

            //设置对话框的大小
            WindowManager.LayoutParams params =
                    dialog.getWindow().getAttributes();
            params.width = 550;
            params.height = 300 ;
            dialog.getWindow().setAttributes(params);

        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            dialog.dismiss();//关闭弹窗

            //加载完成就跳转注册界面
            Intent intent=new Intent(logonActivity.this,registerActivity.class);
            startActivityForResult(intent,23);
        }
    }

    //利用游标cursor取账户数据库中的用户信息，并放入list集合之中
    private void initDiary(){
        SQLiteDatabase db = new UserParameter(logonActivity.this).getWritableDatabase();
        Cursor cursor= db.query("UserData",null,null,null,null,null,null);
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