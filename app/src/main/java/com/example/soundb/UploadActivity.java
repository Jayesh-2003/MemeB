package com.example.soundb;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UploadActivity extends AppCompatActivity {
    EditText editTextTitle;
    TextView textViewImage;
    ProgressBar progressBar;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.upload_meme);

        editTextTitle = findViewById(R.id.memeTitle);
        textViewImage = findViewById(R.id.textViewMemeFileSelected);
        progressBar = findViewById(R.id.progressBar);


    }

}
