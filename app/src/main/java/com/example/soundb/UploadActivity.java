package com.example.soundb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class UploadActivity extends AppCompatActivity {
    EditText editTextTitle;
    TextView textViewImage;
    ProgressBar progressBar;
    Uri audioUri;


    ActivityResultLauncher<Intent> intentActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        audioUri = data.getData();
                        textViewImage.setText(getFileName(audioUri, getApplicationContext()));
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        editTextTitle = (EditText) findViewById(R.id.memeTitle);
        textViewImage = (TextView) findViewById(R.id.textViewMemeFileSelected);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


    }

    public void openAudioFileDialog(View view) {
        Intent data = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        data.setType("audio/*");
        data = Intent.createChooser(data, "Choose MemeAudio");
        intentActivityResultLauncher.launch(data);
    }

    String getFileName(Uri uri, Context context) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
            if (result == null) {
                result = uri.getPath();
                int cutt = result.lastIndexOf('/');
                if (cutt != -1) {
                    result = result.substring(cutt + 1);
                }
            }
        }
        return result;
    }

}