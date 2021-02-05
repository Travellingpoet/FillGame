package com.vandine.fillgame;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jaeger.library.StatusBarUtil;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FillGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FillGameFragment extends Fragment {
    TextView answerA,answerB,answerC,answerD;
    ImageView superManA,superManB,superManC,superManD;
    TimeProgressView timeProgressView;
    private final int postDelayIntervel = 1000;     //延时时间
    private final int maxRunTime = 18000;    //无操作时最大计时时间
    private int noControlTime = 0;     //没有操作时计时判断
    private int currentWordRunTime;
    private Handler mHandler = new Handler();
    View topView;


    public FillGameFragment() {
        // Required empty public constructor
    }

    public static FillGameFragment newInstance() {
        FillGameFragment fragment = new FillGameFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fill_game, container, false);
        initView(view);
        initListener();
        StatusBarUtil.setTransparentForImageView(getActivity(),null);
        int actionBarHeight = titleUtil.getStatusBarHeight(getContext());
        setHeight(actionBarHeight);
        return view;
    }

    private void initView(View view){
        answerA = view.findViewById(R.id.txt_wordA);
        answerB= view   .findViewById(R.id.txt_wordB);
        answerC = view.findViewById(R.id.txt_wordC);
        answerD = view.findViewById(R.id.txt_wordD);

        superManA = view.findViewById(R.id.img_super_manA);
        superManB = view.findViewById(R.id.img_super_manB);
        superManC = view.findViewById(R.id.img_super_manC);
        superManD = view.findViewById(R.id.img_super_manD);
        topView = view.findViewById(R.id.top_view);


        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(this).load(R.drawable.img_superman).apply(options).centerCrop().into(superManA);
        Glide.with(this).load(R.drawable.img_superman).apply(options).centerCrop().into(superManB);
        Glide.with(this).load(R.drawable.img_superman).apply(options).centerCrop().into(superManC);
        Glide.with(this).load(R.drawable.img_superman).apply(options).centerCrop().into(superManD);


        timeProgressView = view.findViewById(R.id.time_progress);
        timeProgressView.setTotalAndCurrentCount(100,50);
    }

    private void initListener(){
        answerA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new XPopup.Builder(getContext())
                        .asBottomList("", new String[]{"条目1", "条目2", "条目3", "条目4", "条目5"},
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {

                                    }
                                })
                        .show();
            }
        });
    }

    /**
     * 时间控制：用于计算十八秒：一个单词如果18秒无（按钮）操作，则停止计时
     */
    private void timeControl() {
        if (noControlTime == maxRunTime) {       //到最大值，即倒计时已经停止运行,现在重新开始计时
            mHandler.removeCallbacks(timer);
            noControlTime = 0;    //触摸即为0
            mHandler.postDelayed(timer, postDelayIntervel);
        }
        noControlTime = 0;    //触摸即为0
    }

    //计时时间
    private Runnable timer = new Runnable() {
        @Override
        public void run() {
            currentWordRunTime += postDelayIntervel;                  //当前运行时间+1000
            noControlTime += postDelayIntervel;                        //触摸加时间
            if (noControlTime < maxRunTime) {           //触摸时间不到最大值，接着运行
                mHandler.postDelayed(this, postDelayIntervel);         //一秒后再次运行
            }
        }
    };

    /**
     * 设置一个占位高度，避免内容整体上移
     * @param
     */
    private void setHeight(int actionBarHeight) {
        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) topView.getLayoutParams();
        params.height = actionBarHeight;
        topView.setLayoutParams(params);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHandler.removeCallbacks(timer);
    }
}