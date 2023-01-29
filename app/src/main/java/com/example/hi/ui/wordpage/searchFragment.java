package com.example.hi.ui.wordpage;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.hi.R;
import com.example.hi.adapter.Search_base;
import com.example.hi.data.English_ETC4_Factor;
import com.example.hi.databinding.FragmentSearchBinding;

import java.util.ArrayList;

public class searchFragment extends Fragment {
    private ArrayList<String> data;
    private String Search_txt;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        com.example.hi.databinding.FragmentSearchBinding binding = FragmentSearchBinding.inflate(inflater, container, false);
        View v= binding.getRoot();

        data=new ArrayList<>();

        //下面三行是键盘自动出来
        binding.wordSearchTxt.requestFocus();
        InputMethodManager keywords1=(InputMethodManager)requireActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        keywords1.showSoftInput( binding.wordSearchTxt,0);

        //准备适配器并，设置到listview中
        Search_base adapter=new Search_base(requireContext(),data);
        binding.listview.setAdapter(adapter);

        //为搜索框设置监听
        binding.wordSearchTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                Search_txt=editable.toString().trim();
                data.clear();
                //如果搜索框中输入了内容，开始过滤展示
                if (Search_txt.length()!=0)GetList(Search_txt);
                adapter.notifyDataSetChanged();
            }
        });

        //点击back，返回主页
        binding.imgSearchBack.setOnClickListener(view -> {
            NavController controller= Navigation.findNavController(view);
            controller.navigate(R.id.action_searchFragment_to_navigation_word);

            //记得关闭键盘哦
            InputMethodManager keywords2 =(InputMethodManager)requireActivity()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            keywords2.hideSoftInputFromWindow(view.getWindowToken(),0);
        });

        return v;
    }

    //这里我采取的是递归算法查询，而非过滤器，若有不懂看注释
    private void GetList(String txt) {
        //判断并接收传递过来的数据
        assert getArguments() != null;
        ArrayList list = getArguments().getParcelableArrayList("LIST_SEARCH");

        //这里需要强转一下，变成我们所需要的类型
        ArrayList<English_ETC4_Factor> list_Search= (ArrayList<English_ETC4_Factor>) list;

        //遍历集合中的所有单词
        for (int i = 0; i <list_Search.size(); i++) {

            String word = list_Search.get(i).getWordHead();//seal  cessation  sensible

            if (Filtration(word,txt,0)) data.add(word);
        }

    }

    //递归查询算法（单词，输入的关键字，是否是递归调用）,此算法是我自己写的，若是不看懂，带着参数在脑海中跑一遍，就明白了
    private boolean Filtration(String word, String txt,int flag) {

        for (int j = 0; j < txt.length(); j++) {//关键字作为外层循环

            for (int k =0; k < word.length(); k++) {//单词为内层循环

                //此处了flag主要是用来区别是否是递归调用
                if (flag==0){//若不是递归调用，进入此处
                    //当关键字是一个字符时，若是此单词中有相同单词，直接返回TRUE，表示此单词是我们需要的
                    if (txt.length()<2&&txt.charAt(j) == word.charAt(k)) {
                        return true;
                    }
                }else {//反之是递归调用，进入此处
                    if (txt.length()<2) {
                        if (txt.charAt(j) == word.charAt(k))return true;
                        if (txt.charAt(j) != word.charAt(k))return false;
                    }
                }

                //拦截器
                if (txt.length()>1&&txt.charAt(j) != word.charAt(k)) return false;

                //若是两个字符即以上，截取算法，递归再比较
                if (txt.length()>1&&txt.charAt(j) == word.charAt(k)) {
                    return Filtration(word.substring(word.indexOf( word.charAt(k))+1),txt.substring(txt.indexOf(txt.charAt(j))+1),1);
                }
            }
        }
        return false;
    }
}
