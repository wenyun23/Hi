package com.example.hi.ui.wordpage;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.hi.R;
import com.example.hi.data.English_ETC4_Factor;
import com.example.hi.databinding.FragmentWordBinding;
import com.example.hi.tool.DBUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class WordFragment extends Fragment{
    private FragmentWordBinding binding;
    private ContentValues values;
    private DBUtil db;

    private final ArrayList<String> English_s=new ArrayList<>();
    private final ArrayList<English_ETC4_Factor> liste=new ArrayList<>();
    private final ArrayList<English_ETC4_Factor> listl=new ArrayList<>();

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentWordBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //初始化,并加载单词数据库，并放入liste集合中
        initDiarye();

        //将单词载入本地数据库
        loadWords();


        //将单词总数展示
        binding.wordTxtTotal.setText(liste.size()+"词");

        //将已学习单词展示
        binding.wordTxtNumber.setText(listl.size() + "词");

        //设置进度条长度、当前进度
        binding.wordProgress.setMax(liste.size());
        binding.wordProgress.setProgress(listl.size());

        //设置闹钟
        binding.imgMyClock.setOnClickListener(view -> {
            Intent alarms = new Intent(AlarmClock.ACTION_SET_ALARM);
            startActivity(alarms);
        });

        //为搜索框实现监听
        binding.wordToSearch.setOnClickListener(view -> {
            //将单词集合传入Search界面，那边需要用到
            Bundle bundle=new Bundle();
            bundle.putParcelableArrayList("LIST_SEARCH", new ArrayList(liste));

            //界面跳转
            NavController controller=Navigation.findNavController(view);
            controller.navigate(R.id.action_navigation_word_to_searchFragment,bundle);
        });

        //通知跳转
        binding.imgDetailInform.setOnClickListener(view -> {
            NavController controller= Navigation.findNavController(view);
            controller.navigate(R.id.action_navigation_word_to_informFragment);
        });

        //点击开始学习吧，跳转到背单词界面
        binding.wordBtnLearn.setOnClickListener(view -> {
            NavController controller= Navigation.findNavController(view);
            controller.navigate(R.id.action_navigation_word_to_detailFragment);
        });

        return root;
    }

    //加载单词
    private void loadWords() {
        try {

            //若是第一次进来，则将单词的基本信息添加到数据库中
            if (liste.size()==0){
            ReadIO();//IO读取本就CET4，并解析Json数据

            for (int i=0;i<English_s.size();i++){//遍历

                JSONObject J_Frist = new JSONObject(new JSONObject(English_s.get(i)).getString("content"));
                JSONObject  J_Second=J_Frist.getJSONObject("word");
                String wordHead=J_Second.getString("wordHead");//英文单词

                JSONObject testJson=new JSONObject(String.valueOf(J_Second.getJSONObject("content")));
                String usphone=testJson.getString("ukphone");//音标

                JSONObject J_syno=new JSONObject(String.valueOf(testJson.getJSONObject("syno")));
                JSONArray J_array_y=J_syno.getJSONArray("synos");
                String spos=J_array_y.getJSONObject(0).getString("pos");//词性
                String stran=J_array_y.getJSONObject(0).getString("tran");//词性中文

                String descj=J_syno.getString("desc");//同近
                JSONObject J_hwds=new JSONObject(String.valueOf(J_array_y.getJSONObject(0)));
                JSONArray J_array_h=J_hwds.getJSONArray("hwds");
                String w=J_array_h.getJSONObject(0).getString("w");//近义词

                JSONObject J_tence=new JSONObject(String.valueOf(testJson.getJSONObject("sentence")));
                JSONArray J_array_s=J_tence.getJSONArray("sentences");
                String desj=J_tence.getString("desc");//例句
                String sContent=J_array_s.getJSONObject(0).getString("sContent");//例句英文
                String sCn=J_array_s.getJSONObject(0).getString("sCn");//例句中文

                JSONArray J_array_t=testJson.getJSONArray("trans");
                String tranOther=J_array_t.getJSONObject(0).getString("tranOther");//其他

                values.put("wordHead",wordHead);
                values.put("usphone",usphone);
                values.put("spos",spos);
                values.put("stran",stran);
                values.put("descj",descj);
                values.put("w",w);
                values.put("desj",desj);
                values.put("sContent",sContent);
                values.put("sCn",sCn);
                values.put("tranOther",tranOther);

                //开始存入
                db.dbe.insert("ETC4_Data",null,values);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //利用IO流读取本地单词
    private void ReadIO() throws IOException {
        InputStream is =getResources().getAssets().open("CET4.json");

        //使用IO流读取json文件内容
        InputStreamReader inputStreamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(inputStreamReader);

        String line;
        while ((line = br.readLine())!=null){
            //边读边用集合存
            English_s.add(line);
        }
        //记得关闭流哦
        br.close();
        is.close();
        inputStreamReader.close();
    }

    private void initDiarye(){
        db=new DBUtil(requireContext());
        values=new ContentValues();
        liste.clear();
        listl.clear();
        db.initDiary_e(liste);
        db.initDiary_l(listl);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}