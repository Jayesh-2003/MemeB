package com.example.soundb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    String[] soundList={"BabyCry","AtharvaLaugh","FuckOff","Run","FuckThisShitImOut","AreeVedya"};

    RecyclerView SoundView;
    SoundboardRecyclerAdapter SoundAdapter =new SoundboardRecyclerAdapter(soundList);
    RecyclerView.LayoutManager SoundLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SoundView =(RecyclerView) findViewById(R.id.soundboardRecyclerView);
        SoundLayoutManager = new GridLayoutManager(this,3);
        SoundView.setLayoutManager(SoundLayoutManager);
        SoundView.setAdapter(SoundAdapter);
    }
}