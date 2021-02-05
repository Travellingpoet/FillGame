package com.vandine.fillgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jaeger.library.StatusBarUtil;

public class FillGameActivity extends AppCompatActivity {


    ImageView fillGame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_game);
        initView();
    }


    private void initView(){
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container,new FillGameFragment())
                .commit();

        fillGame = findViewById(R.id.fill_game_background);

        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(this).load(R.drawable.img_fill_game_background).apply(options).centerCrop().into(fillGame);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}