package com.example.hi.ui.mypage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.hi.R;
import com.example.hi.databinding.FragmentHelpBinding;
import com.example.hi.tool.help_data;

//这个界面都是一样的东西，我基本就是在凑功能，这界面真没什么可以介绍的
public class helpFragment extends Fragment {
    private final Bundle bundle=new Bundle();
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        com.example.hi.databinding.FragmentHelpBinding binding = FragmentHelpBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //返回上一级界面
        binding.imgHelpBack.setOnClickListener(view -> {
            NavController controller= Navigation.findNavController(view);
            controller.navigate(R.id.action_helpFragment_to_navigation_my);
        });

        //阅读记忆法
        binding.txtHelpRead.setOnClickListener(view -> {
            bundle.putInt("id",1);
            bundle.putString("read",new help_data().messages[0]);
            bundle.putString("detail",new help_data().helps[0]);
            NavController controller= Navigation.findNavController(view);
            controller.navigate(R.id.action_helpFragment_to_help_detail_Fragment,bundle);
        });

        //笔记记忆法
        binding.txtHelpBook.setOnClickListener(view -> {
            bundle.putInt("id",2);
            bundle.putString("book",new help_data().messages[1]);
            bundle.putString("detail",new help_data().helps[1]);
            NavController controller= Navigation.findNavController(view);
            controller.navigate(R.id.action_helpFragment_to_help_detail_Fragment,bundle);
        });

        //间隔反复记忆法
        binding.txtHelpJian.setOnClickListener(view -> {
            bundle.putInt("id",3);
            bundle.putString("jian",new help_data().messages[2]);
            bundle.putString("detail",new help_data().helps[2]);
            NavController controller= Navigation.findNavController(view);
            controller.navigate(R.id.action_helpFragment_to_help_detail_Fragment,bundle);
        });

        //“勤”即勤于查词典“联”即联想记忆法
        binding.txtHelpLian.setOnClickListener(view -> {
            bundle.putInt("id",4);
            bundle.putString("lian",new help_data().messages[3]);
            bundle.putString("detail",new help_data().helps[3]);
            NavController controller= Navigation.findNavController(view);
            controller.navigate(R.id.action_helpFragment_to_help_detail_Fragment,bundle);
        });

        //“勤”即勤于查词典
        binding.txtHelpQin.setOnClickListener(view -> {
            bundle.putInt("id",5);
            bundle.putString("qin",new help_data().messages[4]);
            bundle.putString("detail",new help_data().helps[4]);
            NavController controller= Navigation.findNavController(view);
            controller.navigate(R.id.action_helpFragment_to_help_detail_Fragment,bundle);
        });

        //个人账号问题
        binding.txtHelpAccount.setOnClickListener(view -> {
            bundle.putInt("id",6);
            bundle.putString("account",new help_data().messages[5]);
            bundle.putString("detail",new help_data().helps[5]);
            NavController controller= Navigation.findNavController(view);
            controller.navigate(R.id.action_helpFragment_to_help_detail_Fragment,bundle);
        });

        //功能相关问题
        binding.txtHelpGong.setOnClickListener(view -> {
            bundle.putInt("id",7);
            bundle.putString("gong",new help_data().messages[6]);
            bundle.putString("detail",new help_data().helps[6]);
            NavController controller= Navigation.findNavController(view);
            controller.navigate(R.id.action_helpFragment_to_help_detail_Fragment,bundle);
        });

        //学习相关问题
        binding.txtHelpLearn.setOnClickListener(view -> {
            bundle.putInt("id",8);
            bundle.putString("learn",new help_data().messages[7]);
            bundle.putString("detail",new help_data().helps[7]);
            NavController controller= Navigation.findNavController(view);
            controller.navigate(R.id.action_helpFragment_to_help_detail_Fragment,bundle);
        });

        return root;
    }
}