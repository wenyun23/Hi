package com.example.hi.ui.wordpage;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.hi.R;
import com.example.hi.data.English_ETC4_Factor;
import com.example.hi.databinding.FragmentDetailBinding;
import com.example.hi.tool.DBUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class detailFragment extends Fragment {
    private FragmentDetailBinding binding;
    private ContentValues valuesl;
    private ContentValues valuesc;
    private DBUtil db;

    private final ArrayList<English_ETC4_Factor> liste=new ArrayList<>();
    private final ArrayList<English_ETC4_Factor> listl=new ArrayList<>();
    private final ArrayList<English_ETC4_Factor> listc=new ArrayList<>();

    private int index;
    private int count;

    private long baseTimer;
    private boolean front=true;

    private TextToSpeech toSpeech;
    private Handler handler;
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding=FragmentDetailBinding.inflate(inflater, container, false);

        //当你开始背单词时，计时器便统计你学习的时长
        CountTime();

        //初始化语音
        ToSpeack();

        initDiarye();

        //对底部状态栏进行设置
        binding.detailProgWord.setMax(liste.size());
        binding.detailTxtNumbers.setText(listl.size() + "/" + liste.size());
        binding.detailProgWord.setProgress(listl.size());

        //进入时设置单词详细信息不可见
        binding.include.detailRelative.setVisibility(View.INVISIBLE);

        //进入时，开始随机单词展示
        RandomWords(false);

        //点击back返回上一页
        binding.imgDetailBack.setOnClickListener(view -> {
            NavController controller= Navigation.findNavController(view);
            controller.navigate(R.id.action_detailFragment_to_navigation_word);
        });

        //点击删除单词
        binding.imgDetailRublish.setOnClickListener(view -> deleteWords());

        //点击收藏单词
        binding.imgDetailCollect.setOnClickListener(view -> CollectWord());

        //点击分享单词
        binding.imgDetailAlter.setOnClickListener(view -> {
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setType("image/jpeg")
                    .putExtra(Intent.EXTRA_STREAM,"分享给云团吧");
            startActivity(Intent.createChooser(intent,"分享"));
        });

        //点击播放读音
        binding.detailTxtWord.setOnClickListener(view -> toSpeech.speak((String) binding.detailTxtWord.getText(),TextToSpeech.QUEUE_FLUSH,null));

        //点击屏幕可以看见单词详细信息
        binding.detailTxtFrontShow.setOnClickListener(view -> {
            binding.detailTxtFrontShow.setVisibility(View.INVISIBLE);
            binding.include.detailRelative.setVisibility(View.VISIBLE);
            ShowWords();
        });

        //已学会
        binding.detailBtnLearned.setOnClickListener(view -> {
            //设置单词不可见
            binding.include.detailRelative.setVisibility(View.INVISIBLE);
            binding.detailTxtFrontShow.setVisibility(View.VISIBLE);

            binding.imgDetailCollect.setImageResource(R.drawable.collect_front);
            front=true;
            RandomWords(true);

            binding.detailTxtNumbers.setText(count+"/"+liste.size());
            binding.detailProgWord.setProgress(count);

        });

        //不认识
        binding.detailBtnLearn.setOnClickListener(view -> {
            //设置单词不可见
            binding.include.detailRelative.setVisibility(View.INVISIBLE);
            binding.detailTxtFrontShow.setVisibility(View.VISIBLE);

            binding.imgDetailCollect.setImageResource(R.drawable.collect_front);
            front=true;
            RandomWords(false);
        });

        return binding.getRoot();
    }

    //计时器
    @SuppressLint("HandlerLeak")
    private void CountTime(){
        //存取当前的系统时间
        baseTimer = SystemClock.elapsedRealtime();

        //利用handler实现异步处理
        handler = new Handler(){
            public void handleMessage(android.os.Message msg) {
                binding.detailTxtTime.setText((String) msg.obj);
            }
        };

        //Timer实现子线程计时操作
        new Timer("专注").scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int time = (int)((SystemClock.elapsedRealtime() - baseTimer) / 1000);
                String hh = new DecimalFormat("00").format(time / 3600);
                String mm = new DecimalFormat("00").format(time % 3600 / 60);
                String ss = new DecimalFormat("00").format(time % 60);
                String timeFormat = hh + ":" + mm + ":" + ss;
                Message msg = new Message();
                msg.obj = timeFormat;
                if (handler!=null){
                    handler.sendMessage(msg);
                }

            }
        }, 0, 1000L);

    }

    //初始化语音
    private void ToSpeack(){
        toSpeech=new TextToSpeech(requireContext(), i -> {
            if(i != TextToSpeech.ERROR){
                //设置语言
                toSpeech.setLanguage(Locale.UK);
            }
        });
    }

    //无需多介绍，与之前一样
    private void initDiarye(){
        db=new DBUtil(requireContext());
        valuesl=new ContentValues();
        valuesc=new ContentValues();

        db.initDiary_e(liste);
        db.initDiary_l(listl);
        db.initDiary_c(listc);
    }

    //随机单词算法
    private void RandomWords(boolean flag){
        //每次随机一个单词，都不要完了先清空集合，在加载哦，这三个集合只是一个暂时的容器
        liste.clear();
        listl.clear();
        listc.clear();
        initDiarye();

        //若是所学的单词，已经超过总单词了，则代表你已经成功掌握了
        if (listl.size()<(liste.size())){

            //随机单词数据库中的单词
            index = (int) (Math.random() * liste.size());

            //下面是那当前展示的单词，与已学单词比较，若是相同，代表学过，不在推送，而是再次调用，随机下一个单词
            //目的就是：学过的单词是不会再出现的，这是单词软件的基本算法
            int i=0;
            for (;i<listl.size();i++){
                if (liste.get(index).getWordHead().equals(listl.get(i).getWordHead()))break;
            }

            //代表此单词没有学过
            if (i==listl.size()){

                ShowWords();//将详细信息展示到界面上

                //这个flag,是用于区别“认识”与“不认识”的，认识需要加一下数据库，因为，已学区需要展示
                if (flag){
                    valuesl.put("wordHead",liste.get(index).getWordHead());
                    valuesl.put("usphone",liste.get(index).getUsphone());
                    valuesl.put("spos",liste.get(index).getSpos());
                    valuesl.put("stran",liste.get(index).getStran());
                    valuesl.put("descj",liste.get(index).getDescj());
                    valuesl.put("w",liste.get(index).getW());
                    valuesl.put("desj",liste.get(index).getDesj());
                    valuesl.put("sContent",liste.get(index).getsContent());
                    valuesl.put("sCn",liste.get(index).getsCn());
                    valuesl.put("tranOther",liste.get(index).getTranOther());
                    db.dbl.insert("LearnData",null,valuesl);
                }
            }else {
                if (flag) RandomWords(flag);else RandomWords(flag);
            }
        }else {
            Toast.makeText(requireContext(),"恭喜你已经掌握了所有单词",Toast.LENGTH_SHORT).show();
        }

        liste.clear();
        listl.clear();
        listc.clear();
        initDiarye();
        count=listl.size();
    }

    //详细信息展示
    @SuppressLint("SetTextI18n")
    private void ShowWords(){
        binding.detailTxtWord.setText(liste.get(index).getWordHead());
        binding.detailTxtUsphone.setText(liste.get(index).getUsphone());
        binding.include.detailTxtLanguageShow.setText(liste.get(index).getSpos()+"."+liste.get(index).getStran());
        binding.include.detailTxtDescjShow.setText(liste.get(index).getW());
        binding.include.detailTxtSCn.setText(liste.get(index).getsContent());
        binding.include.detailTxtSCnShow.setText(liste.get(index).getsCn());
        binding.include.detailTxtTranOtherShow.setText(liste.get(index).getTranOther());

    }

    //将单词移除词库
    @SuppressLint("SetTextI18n")
    private void deleteWords(){
        //弹出删除对话框，老生常谈的事，就是数据库删除操作，无需多介绍
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("亲，您确定移除吗?");
        builder.setCancelable(false);
        builder.setNegativeButton("我再想想", (dialogInterface, i) -> Toast.makeText(requireContext(),"加油，你离成功又进了一步",Toast.LENGTH_SHORT).show());

        builder.setPositiveButton("我想好了", (dialogInterface, i) -> {
            db.dbe.delete("ETC4_Data","id=?",new String[]{index+""});
            Toast.makeText(requireContext(),"已将 ‘"+liste.get(index).getWordHead()+"’ 移除词库",Toast.LENGTH_SHORT).show();

            //记得设置单词不可见
            binding.include.detailRelative.setVisibility(View.INVISIBLE);
            binding.detailTxtFrontShow.setVisibility(View.VISIBLE);

            //并随机下一个哦
            RandomWords(false);

            //移除词库时，下面的进度也必须跟着刷新哦
            liste.clear();
            listl.clear();
            listc.clear();
            initDiarye();

            binding.detailTxtNumbers.setText(count+"/"+liste.size());
            binding.detailProgWord.setProgress(count);
        });
        builder.show();
    }

    //收藏单词
    private void CollectWord(){
        //这东西也没有什么说的，和上面基本差不多
        if (front){
            //换图片
            binding.imgDetailCollect.setImageResource(R.mipmap.collect_later);

            front=false;
            valuesc.put("wordHead",liste.get(index).getWordHead());
            valuesc.put("usphone",liste.get(index).getUsphone());
            valuesc.put("spos",liste.get(index).getSpos());
            valuesc.put("stran",liste.get(index).getStran());
            valuesc.put("descj",liste.get(index).getDescj());
            valuesc.put("w",liste.get(index).getW());
            valuesc.put("desj",liste.get(index).getDesj());
            valuesc.put("sContent",liste.get(index).getsContent());
            valuesc.put("sCn",liste.get(index).getsCn());
            valuesc.put("tranOther",liste.get(index).getTranOther());

            db.dbc.insert("CollectData",null,valuesc);
            Toast.makeText(requireContext(),"已标注为重难单词",Toast.LENGTH_SHORT).show();
        } else {
            binding.imgDetailCollect.setImageResource(R.drawable.collect_front);
            front=true;

            liste.clear();
            listl.clear();
            listc.clear();
            initDiarye();

            int number;

            //找寻当前单词在收藏数据库中的位置，因为再次点击收藏时，就相当于取消收藏了，那自然就要将他从数据库中移除
            //这是算法，你自己看吧，反正算法就是打脑壳，小运写的时候也挺脑壳痛
            if (listc.size()==1)number=listc.size()-Math.abs(listc.size()-2);
            else number=listc.size()-Math.abs(listc.size()-1);

            Log.d("TAG", "CollectWord: "+(listc.size()-1));

            db.dbc.delete("CollectData","id=?",new String[]{listc.get(number).getId()+""});
            Toast.makeText(requireContext(),"已取消重难单词标注",Toast.LENGTH_SHORT).show();
        }

    }

    //结束时切记要关闭语音和计时器进程，否则会闪退
    public void onPause(){
        super.onPause();

        if(toSpeech !=null){
            toSpeech.stop();
            toSpeech.shutdown();
        }
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}