package com.example.hi;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hi.data.UserParameter;
import com.example.hi.databinding.ActivityRegisterBinding;

import java.util.Random;

public class registerActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private TextWatcher textWatcher;
    private String uname=null,upwd=null;
    private SQLiteDatabase db;
    private ContentValues values;

    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        binding=ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //隐藏标题栏
        ride();

        //为两个edittext添加监听器
        Text_Watcher();

        //初始化数据库
        db=new UserParameter(registerActivity.this).getWritableDatabase();
        values=new ContentValues();

        //设置注册按钮不可用
        binding.btnRegister.setEnabled(false);

        //将监听事件设置进去
        binding.editRegisterName.addTextChangedListener(textWatcher);
        binding.editRegisterPwd.addTextChangedListener(textWatcher);

        //为返回键设置监听
        binding.imgSignBack.setOnClickListener(view -> finish());

        //将用户输入的信息保存到本地数据库中，完成注册
        binding.btnRegister.setOnClickListener(view -> {

            //每次创建一个新用户时，都需要生成一个新的专属ID号
            Random random=new Random();
            String snumber= "20200"+(4102000+random.nextInt(100));

            if (upwd.length()>5&&upwd.length()<9){
                values.put("uname",uname);
                values.put("upwd",upwd);
                values.put("snumber",snumber);
                db.insert("UserData",null,values);
                setResult(RESULT_OK,new Intent());
                finish();
            }else Toast.makeText(this, "密码必须在六位数和八位数之间", Toast.LENGTH_SHORT).show();

        });
    }

    //隐藏状态栏
    private void ride(){
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    //为两个edittext添加监听
    public void Text_Watcher(){
        textWatcher=new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //获得输入的值
                uname=binding.editRegisterName.getText().toString().trim();
                upwd=binding.editRegisterPwd.getText().toString().trim();

                //如果这账户名与密码皆不为空,则“确认注册”按钮可用
                binding.btnRegister.setEnabled(!uname.isEmpty()&&!upwd.isEmpty());
            }
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) { }
        };
    }

}