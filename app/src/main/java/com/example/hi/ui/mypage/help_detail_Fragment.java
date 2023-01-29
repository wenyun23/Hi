package com.example.hi.ui.mypage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hi.R;
import com.example.hi.databinding.FragmentHelpDetailBinding;

//接收对面传来的东西，展示而已，无需多介绍
public class help_detail_Fragment extends Fragment {
    private String txt_message,txt_show;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        com.example.hi.databinding.FragmentHelpDetailBinding binding = FragmentHelpDetailBinding.inflate(inflater, container, false);
        View root= binding.getRoot();

        //接收help_data传来的内容
        Judge();

        //将内容放置到TextView
        binding.txtMessage.setText(txt_message);
        binding.txtHelpShow.setText(txt_show);

        //为back做监听
        binding.imgHelpBack.setOnClickListener(view -> {
            NavController controller= Navigation.findNavController(view);
            controller.navigate(R.id.action_help_detail_Fragment_to_helpFragment);
        });

        return root;
    }

    private void Judge(){
        assert getArguments() != null;

        switch (getArguments().getInt("id")){
            case 1: {
                txt_message =getArguments().getString("read");
                txt_show =getArguments().getString("detail");
            } break;
            case 2: {
                txt_message =getArguments().getString("book");
                txt_show =getArguments().getString("detail");
            } break;
            case 3: {
                txt_message =getArguments().getString("jian");
                txt_show =getArguments().getString("detail");
            } break;
            case 4: {
                txt_message =getArguments().getString("lian");
                txt_show =getArguments().getString("detail");
            } break;
            case 5: {
                txt_message =getArguments().getString("qin");
                txt_show =getArguments().getString("detail");
            } break;
            case 6: {
                txt_message =getArguments().getString("account");
                txt_show =getArguments().getString("detail");
            } break;
            case 7: {
                txt_message =getArguments().getString("gong");
                txt_show =getArguments().getString("detail");
            } break;
            default: {
                txt_message =getArguments().getString("learn");
                txt_show =getArguments().getString("detail");
            } break;
        }
    }
}