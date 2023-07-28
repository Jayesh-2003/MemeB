package com.example.soundb;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.soundb.Model.UploadMeme;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class UploadActivity extends AppCompatActivity {
    EditText editTextTitle;
    TextView textViewImage;
    ProgressBar progressBar;
    Uri audioUri;

    StorageReference mstorageRef;
    DatabaseReference databaseReference;
    StorageTask mUploadTask;


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

        editTextTitle = findViewById(R.id.memeTitle);
        textViewImage = findViewById(R.id.textViewMemeFileSelected);
        progressBar = findViewById(R.id.progressBar);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("memes");
        mstorageRef = FirebaseStorage.getInstance().getReference().child("memes");


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

    public void uploadAudioToFirebase(View v) {
        if (textViewImage.getText().toString().equals("No file selected")) {
            Toast.makeText(getApplicationContext(), "Please select an audio file", Toast.LENGTH_SHORT).show();
        } else {
            if (mUploadTask != null && mUploadTask.isInProgress()) {
                Toast.makeText(getApplicationContext(), "Meme upload is already in progress", Toast.LENGTH_LONG);
            } else {
                uploadFile();
            }

        }

    }

    private void uploadFile() {
        if (audioUri != null) {
            Toast.makeText(getApplicationContext(), "Uploading please wait...", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.VISIBLE);

            StorageReference storageReference = mstorageRef.child(System.currentTimeMillis() + "." + getFileExtension(audioUri));
            mUploadTask = storageReference.putFile(audioUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            UploadMeme uploadMeme = new UploadMeme(editTextTitle.getText().toString(), uri.toString());
                            String uploadId = databaseReference.push().getKey();
                            databaseReference.child(uploadId).setValue(uploadMeme);
                        }
                    });


                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    progressBar.setProgress((int) progress);
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "No File Selected", Toast.LENGTH_LONG).show();
        }
    }

    private String getFileExtension(Uri audioUri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(audioUri));
    }

    public void openMainActivity(View v) {
        startActivity(new Intent(UploadActivity.this, MainActivity.class));
    }
}