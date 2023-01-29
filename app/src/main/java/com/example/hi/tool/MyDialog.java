package com.example.hi.tool;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.hi.R;

import java.util.Objects;

//这段代码也是直接从网上找的，一个对话框展示
public class MyDialog extends AlertDialog implements View.OnClickListener {
    TextView text_read;
    Button mBtnCancel, mBtnConnect;
    Context mContext;
    Activity mActivity;

    public MyDialog(Context context, Activity activity) {
        super(context);
        mContext = context;
        mActivity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialoglayout);

        mBtnCancel = (Button) findViewById(R.id.btn_cancel);
        Objects.requireNonNull(mBtnCancel).setOnClickListener(this);
        mBtnConnect = (Button) findViewById(R.id.btn_connect);
        Objects.requireNonNull(mBtnConnect).setOnClickListener(this);
        text_read = (TextView) findViewById(R.id.read_text);

        final SpannableStringBuilder style = new SpannableStringBuilder();

        //设置文字
        style.append("请您在使用前点击阅读《Hi用户政策》，如果同意，请点击下方“同意”按钮开始使用软件。");

        //设置部分文字点击事件
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(mContext, "给我点好评，否则头给你卸了!", Toast.LENGTH_LONG).show();
            }
        };

        style.setSpan(clickableSpan, 11, 20, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text_read.setText(style);

        //设置部分文字颜色
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#0000FF"));
        style.setSpan(foregroundColorSpan, 11, 20, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        //配置给TextView
        text_read.setMovementMethod(LinkMovementMethod.getInstance());
        text_read.setText(style);

    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                this.dismiss();
                mActivity.finish();
                break;
            case R.id.btn_connect:
                this.dismiss();
                Toast.makeText(mContext,"开始登录吧",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}


