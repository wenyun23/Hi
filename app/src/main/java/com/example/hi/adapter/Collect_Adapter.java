package com.example.hi.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hi.R;
import com.example.hi.data.English_ETC4_Factor;

import java.util.ArrayList;

//适配器一、收藏界面适配器，采取的是RecyclerView
public class Collect_Adapter extends RecyclerView.Adapter<Collect_Adapter.My_Collect>{
    private final ArrayList<English_ETC4_Factor> list;
    private final Context context;

    public Collect_Adapter(ArrayList<English_ETC4_Factor> list1, Context con_text) {
        this.list=list1;
        this.context=con_text;
    }

    @NonNull
    @Override
    public My_Collect onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams")
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.collect_cell,parent,false);
        return new My_Collect(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull My_Collect holder, int position) {
        holder.collect_englisg.setText(list.get(position).getWordHead());
        holder.collect_word.setText(list.get(position).getSpos()+"."+list.get(position).getStran());

        //为每一个recycleView中的item设置单击事件
        holder.itemView.setOnClickListener(view -> {
            AlertDialog.Builder builder= new AlertDialog.Builder(context)
                    .setTitle(list.get(position).getWordHead()+" 的详细信息")
                    .setMessage("英文："+list.get(position).getWordHead()+"\n词性："+
                            list.get(position).getSpos()+"\n音标："+
                            list.get(position).getUsphone()+"\n中文："+
                            list.get(position).getStran()+"\n同义："+
                            list.get(position).getW()+"\n英文例句："+
                            list.get(position).getsContent()+"\n例句翻译："+
                            list.get(position).getsCn()+"\n其他列句："+
                            list.get(position).getTranOther());
            builder.create();
            builder.show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class My_Collect extends RecyclerView.ViewHolder{
        TextView collect_word,collect_englisg;
        public My_Collect(@NonNull View itemView) {
            super(itemView);
            collect_englisg=itemView.findViewById(R.id.txt_collect_English);
            collect_word=itemView.findViewById(R.id.txt_collect_Chinese);
        }
    }
}
