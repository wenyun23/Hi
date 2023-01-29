package com.example.hi.ui.mypage;

import static java.lang.Thread.sleep;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hi.R;
import com.example.hi.data.LearnFactor;
import com.example.hi.data.LearnParameter;
import com.example.hi.databinding.FragmentMyBinding;
import com.example.hi.logonActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyFragment extends Fragment {
    private FragmentMyBinding binding;
    private AsyncTask task;
    private final ArrayList<LearnFactor> listl=new ArrayList<>();

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMyBinding.inflate(inflater, container, false);
        View root= binding.getRoot();

        //将账户名和id展示
        binding.headName.setText(requireActivity().getIntent().getStringExtra("uname"));
        binding.headId.setText("ID:"+requireActivity().getIntent().getStringExtra("snumber"));

        //与之前一样，无需多介绍
        initDiaryl();

        //展示已学单词数量
        binding.myTxtWord.setText(String.valueOf(listl.size()));

        //展示当前系统日期
        task=new GetDate().execute();

        //在线查意
        binding.linearOnline.setOnClickListener(view -> Search());

        //帮助与反馈
        binding.linearHelp.setOnClickListener(this::Help);

        //每日一文
        binding.linearNumber.setOnClickListener(this::Read);

        //给云团好评
        binding.linearZang.setOnClickListener(view -> Zan());

        //退出登录
        binding.linearOut.setOnClickListener(view -> Cancel());

        return root;
    }

    //将已学单词数据库放入集合之中
    private void initDiaryl(){
        //学习单词
        SQLiteDatabase dbl = new LearnParameter(requireContext()).getWritableDatabase();
        Cursor cursorl= dbl.query("LearnData",null,null,null,null,null,null);
        if (cursorl.moveToFirst()){
            do {
                LearnFactor learnFactor=new LearnFactor();
                listl.add(learnFactor);
            }while (cursorl.moveToNext());
        }
        cursorl.close();
    }

    //当前时间,依旧使用AsyncTask
    @SuppressLint("StaticFieldLeak")
    class GetDate extends AsyncTask<Void,String,Void>{

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... voids) {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

            while (true) {

                //如果不是当前界面则需要马上跳出去，不然会抛异常
                if(isCancelled())break;

                //获取当前时间
                Date date = new Date(System.currentTimeMillis());
                publishProgress(simpleDateFormat.format(date));

                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            //和上面那个一个，用于拦截其他情况
            if(isCancelled())return;

            binding.myTxtData.setText(values[0]);
        }
    }

    //在线查意
    private void Search(){
        final EditText inputServer= new EditText(requireContext());
        String[] items=new String[]{"有道查询","HI查询"};
        final int[] choose = new int[1];

        AlertDialog.Builder builder= new AlertDialog.Builder(requireContext())

                .setTitle("选择引擎并输入单词").setView(inputServer).setCancelable(false)

                .setIcon(R.drawable.logo)

                .setSingleChoiceItems(items, 0, (dialogInterface, i) -> choose[0] =i)

                .setNegativeButton("放弃", (dialogInterface, i) -> {})

                .setPositiveButton("查询", (dialogInterface, i) -> {
                    if (inputServer.getText().length()==0){
                        Toast.makeText(requireContext(),"内容不能为空",Toast.LENGTH_SHORT).show();
                    }else {
                        if (choose[0]==0){
                            //该网址为"有道  词典"的网址
                            Uri uri=Uri.parse("https://m.youdao.com/dict?le=eng&q="+inputServer.getText());
                            startActivity(new Intent(Intent.ACTION_VIEW,uri));
                        }else {
                            LoadWord(String.valueOf(inputServer.getText()));
                        }
                    }
                });
        builder.create();
        builder.show();
    }

    //"Hi"引擎查询单词
    private void LoadWord(String word){
        RequestQueue queue= Volley.newRequestQueue(requireContext());
        String url = "http://dict.youdao.com/suggest?q="+word+"&num=1&doctype=json";

        //都是以前学过的东西，我想无需多说
        JsonObjectRequest request=new JsonObjectRequest(
                url,
                null,
                response -> {
                    try {
                        JSONObject dataObject = response.getJSONObject("data");

                        JSONArray array=dataObject.getJSONArray("entries");
                        JSONObject message=array.getJSONObject(0);

                        AlertDialog.Builder builder_show = new AlertDialog.Builder(requireContext())
                                .setTitle("点击空白处，退出")
                                .setMessage(message.getString("entry")+"\n"+message.getString("explain"));

                        builder_show.create();
                        builder_show.show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.d("TAG", "onErrorResponse: "+error.getMessage(),error)
        );
        queue.add(request);
    }

    //帮助与反馈
    private void Help(View view){

        NavController controller= Navigation.findNavController(view);
        controller.navigate(R.id.action_navigation_my_to_helpFragment);
    }

    //每日一文
    private void Read(View view) {
        NavController controller= Navigation.findNavController(view);
        controller.navigate(R.id.action_navigation_my_to_readFragment);
    }

    //给云团好评
    @SuppressLint("ResourceAsColor")
    private void Zan(){

        final RatingBar ratingBar = new RatingBar(requireContext());
        ratingBar.setStepSize(1);

        //为点评框设置颜色
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.CYAN, PorterDuff.Mode.SRC_ATOP);

        AlertDialog.Builder builder=new AlertDialog.Builder(requireContext());
        builder.setTitle("给个好评吧")
                .setView(ratingBar)
                .setCancelable(false)
                .setPositiveButton("确定", (dialog, which) -> {
                    int v= (int) ratingBar.getRating();
                    if (v<2){
                        Toast.makeText(requireContext(), "很抱歉给您带来不便，我们会努力改进的",Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(requireContext(), "感谢您送来的 "+v+" 星评价，我们会继续努力的",Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("我再想想",null);

        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setLayout(740, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    //退出登录
    private void Cancel(){
        Intent intent=new Intent(requireActivity(),logonActivity.class);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("确定退出当前账户");
        builder.setNegativeButton("取消", (dialogInterface, i) -> {

        });
        builder.setPositiveButton("确定", (dialogInterface, i) -> {
            startActivity(intent);
            requireActivity().finish();
        });
        builder.show();
    }

    //当不是当前界面时，要记得关闭AsyncTask,否则还是会抛异常
    @Override
    public void onPause() {
        super.onPause();
        //当不是MyFragment时,关闭异步线程,若不即使关闭则会抛异常
        if(task !=null && task.getStatus() == AsyncTask.Status.RUNNING){
            task.cancel(true);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}