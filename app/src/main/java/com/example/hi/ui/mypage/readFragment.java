package com.example.hi.ui.mypage;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hi.R;
import com.example.hi.databinding.FragmentReadBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class readFragment extends Fragment {
    private FragmentReadBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentReadBinding.inflate(inflater,container, false);
        View v=binding.getRoot();

        //加载一文
        LoadRead();

        binding.imgReadBack.setOnClickListener(view -> {
            NavController controller= Navigation.findNavController(view);
            controller.navigate(R.id.action_readFragment_to_navigation_my);
        });

        //下拉刷新加载一文
        binding.swip.setOnRefreshListener(this::LoadRead);
        binding.swip.setColorSchemeResources(R.color.purple_500);

        return v;
    }

    //加载一文
    private void LoadRead() {
        //拦截
        if (binding.swip.isRefreshing()) binding.swip.setRefreshing(false);

        RequestQueue queue= Volley.newRequestQueue(requireContext());
        Random random=new Random();
        int year,month,day;

        //随机"每日一文"文
        year=random.nextInt(7)+2;
        month=random.nextInt(8)+1;
        day=random.nextInt(8)+1;

        String url = "https://interface.meiriyiwen.com/article/day?dev=1"+"&date=201"+year+"0"+month+"1"+day;

        //与之前一样，无需多介绍
        JsonObjectRequest request=new JsonObjectRequest(
                url,
                null,
                response -> {
                    try {
                        JSONObject dataObject = response.getJSONObject("data");

                        String title=dataObject.getString("title");
                        String wc=dataObject.getString("wc");
                        String author=dataObject.getString("author");
                        String digest=dataObject.getString("digest");
                        String content=dataObject.getString("content");

                        binding.txtReadTitle.setText(title);
                        binding.txtReadWc.setText(wc);
                        binding.txtReadAuthor.setText(author);
                        binding.txtReadDigest.setText(digest);
                        binding.txtReadContent.setText(content);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.d("TAG", "onErrorResponse: "+error.getMessage(),error)
        );
        queue.add(request);
    }
}