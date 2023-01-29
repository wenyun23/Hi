package com.example.hi;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.example.hi.databinding.ActivityMainBinding;

/*
* 基于Android的“Hi”英语学习软件，制作人：移动互联应用技术2002班（蒋文云）
* 功能：实现了对单词的记忆，软件参考：“知米背单词、百词斩”
* 界面风格：“骑士助手”
* 单词来源：有道CET4.json
* 最后，开发不易，感谢喜欢，我是小云。
* 个人GitHub账号：https://github.com/wenyun23
* */

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private long exitTime=0;
    private boolean IsBack=true;
    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //为viewBinding申请权限
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navController= Navigation.findNavController(this,
                R.id.nav_host_fragment_activity_main);

        //如果是三个主界面，则正常显示，否则隐藏底部状态栏
        navController.addOnDestinationChangedListener((controller, nd, NavDestination) -> {
            if (nd.getId() == R.id.navigation_word || nd.getId() == R.id.navigation_my
                    || nd.getId() == R.id.navigation_collect
            ) {
                binding.appBarMain.navView.setVisibility(View.VISIBLE);
                IsBack=true;
            } else {
                binding.appBarMain.navView.setVisibility(View.GONE);
                IsBack=false;
            }
        });

        NavigationUI.setupWithNavController(binding.appBarMain.navView,navController);
    }

    //点击两次退出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)&&IsBack){

            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //因为点击搜：单词吧，需要利用Bundle传递数据，而当数据大于512K时就会抛异常，重写该返回可以避免异常
    @Override
    protected void onSaveInstanceState(@NonNull Bundle instanceState) {
        super.onSaveInstanceState(instanceState);
        instanceState.clear();
    }
}