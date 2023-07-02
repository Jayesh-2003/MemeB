package com.example.soundb;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<SoundObject> soundList = new ArrayList<>();
    RecyclerView SoundView;
    SoundboardRecyclerAdapter SoundAdapter =new SoundboardRecyclerAdapter(soundList);
    RecyclerView.LayoutManager SoundLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> nameList= Arrays.asList(getResources().getStringArray(R.array.soundNames));

        SoundObject[] soundItems = {new SoundObject(nameList.get(0), R.raw.atharva_laugh), new SoundObject(nameList.get(1), R.raw.athrva_vedya), new SoundObject(nameList.get(2), R.raw.i_am_in_danger), new SoundObject(nameList.get(3), R.raw.motivation), new SoundObject(nameList.get(4), R.raw.nachiket_fav), new SoundObject(nameList.get(5), R.raw.og_vedya), new SoundObject(nameList.get(6), R.raw.rajat), new SoundObject(nameList.get(7), R.raw.rajat_allharam), new SoundObject(nameList.get(8), R.raw.rajat_vedya), new SoundObject(nameList.get(9), R.raw.sandesh), new SoundObject(nameList.get(10), R.raw.github_knowledge)};

        soundList.addAll(Arrays.asList(soundItems));

        SoundView = findViewById(R.id.soundboardRecyclerView);
        SoundLayoutManager = new GridLayoutManager(this, 3);
        SoundView.setLayoutManager(SoundLayoutManager);
        SoundView.setAdapter(SoundAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventHandlerClass.releaseMediaPlayer();
    }
}
