package com.example.soundb;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soundb.Model.UploadMeme;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static MediaPlayer mp;
    List<UploadMeme> mUpload;
    ValueEventListener valueEventListener;


    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    SoundboardRecyclerAdapter adapter;
    RecyclerView.LayoutManager SoundLayoutManager;
    private Button uploadBtn;

    public static void releaseMediaPlayer() {
        if (mp != null) {
            mp.release();
            mp = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.soundboardRecyclerView);
        recyclerView.setHasFixedSize(true);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 6));
        }

        mUpload = new ArrayList<>();
        adapter = new SoundboardRecyclerAdapter(MainActivity.this, mUpload);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("memes");
        valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUpload.clear();
                for (DataSnapshot dss : snapshot.getChildren()) {
                    UploadMeme uploadMeme = dss.getValue(UploadMeme.class);
                    uploadMeme.setmKey(dss.getKey());
                    mUpload.add(uploadMeme);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        List<String> nameList = Arrays.asList(getResources().getStringArray(R.array.soundNames));


        uploadBtn = findViewById(R.id.btn_upload);
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadBtn(v);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseMediaPlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
    }

    public void uploadBtn(View view) {
//
        startActivity(new Intent(MainActivity.this, UploadActivity.class));
    }


    public void playMeme(List<UploadMeme> arrayListMemes, int adapterPosition) throws IOException {
        UploadMeme uploadMeme = arrayListMemes.get(adapterPosition);
        if (mp != null) {
            mp.stop();
            mp.release();
            mp = null;
        }
        mp = new MediaPlayer();
        mp.setDataSource(uploadMeme.getMemeLink());
        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
        mp.prepareAsync();

    }
}
