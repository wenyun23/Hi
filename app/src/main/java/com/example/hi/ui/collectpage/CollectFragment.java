package com.example.hi.ui.collectpage;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.hi.adapter.Collect_Adapter;
import com.example.hi.data.English_ETC4_Factor;
import com.example.hi.databinding.FragmentCollectBinding;
import com.example.hi.tool.DBUtil;

import java.util.ArrayList;

public class CollectFragment extends Fragment {
    private FragmentCollectBinding binding;
    DBUtil db;

    private final ArrayList<English_ETC4_Factor> C_list=new ArrayList<>();
    private final ArrayList<English_ETC4_Factor> L_list=new ArrayList<>();

    private Collect_Adapter adapter;
    private boolean IS_Delete=true;

    @SuppressLint("NotifyDataSetChanged")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCollectBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db=new DBUtil(requireContext());

        //和之前操作一样，无需多介绍
        initDiarye();

        //默认展示收藏区
        SetAdapter(C_list);

        //收藏区
        binding.txtCollectC.setOnClickListener(view -> {
            binding.txtCollectC.setTextColor(Color.RED);
            binding.txtCollectL.setTextColor(Color.BLACK);
            IS_Delete=true;
            SetAdapter(C_list);
        });

        //已学区
        binding.txtCollectL.setOnClickListener(view ->{
            binding.txtCollectL.setTextColor(Color.RED);
            binding.txtCollectC.setTextColor(Color.BLACK);
            IS_Delete=false;
            SetAdapter(L_list);
        });

        return root;
    }

    //设置适配器
    private void SetAdapter(ArrayList<English_ETC4_Factor> list){
        adapter= new Collect_Adapter(list,requireContext());
        binding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        DeleteWords();
        binding.recyclerView.setAdapter(adapter);
    }

    //实现左右滑动即移除
    @SuppressLint("NotifyDataSetChanged")
    private void DeleteWords() {

        int[] old_size=new int[]{C_list.size(),L_list.size()};
        int[] new_size=new int[2];

        //添加左右滑动删除相应列的数据(拖动，滑动)
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.START | ItemTouchHelper.END) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int index=viewHolder.getAdapterPosition();

                if (IS_Delete) {
                    db.dbc.delete("CollectData","wordHead=?",new String[]{C_list.get(index).getWordHead()});
                    Toast.makeText(requireContext(),"移除成功，小伙子，你离成功又近了一步",Toast.LENGTH_SHORT).show();
                }
                else {
                    db.dbl.delete("LearnData","wordHead=?",new String[]{L_list.get(index).getWordHead()});
                    Toast.makeText(requireContext(),"移除成功，小伙子，你离成功又近了一步",Toast.LENGTH_SHORT).show();
                }

                C_list.clear();
                L_list.clear();
                initDiarye();

                new_size[0]=C_list.size();
                new_size[1]=L_list.size();

                if (old_size[0]!=new_size[0]||old_size[1]!=new_size[1])adapter.notifyDataSetChanged();
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView
                    .ViewHolder target) { return false; }

        }).attachToRecyclerView(binding.recyclerView);
    }

    @SuppressLint("Recycle")
    private void initDiarye(){
        db.initDiary_c(C_list);
        db.initDiary_l(L_list);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}