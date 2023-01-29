package com.example.hi.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.hi.R;
import java.util.List;

//适配器二、查询界面适配器，采取的是BaseAdapter
public class Search_base extends BaseAdapter {

    private final Context mcontext;
    private final List<String> list;

    public Search_base(Context context,List<String> stringList) {
        this.mcontext=context;
        this.list=stringList;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint({"InflateParams","ViewHolder"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v= LayoutInflater.from(mcontext).inflate(R.layout.search_cell,null);

         TextView show=v.findViewById(R.id.txt_search_show);
            show.setText(list.get(i));
         return v;
    }
}
