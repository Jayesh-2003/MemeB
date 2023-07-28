package com.example.soundb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soundb.Model.UploadMeme;

import java.io.IOException;
import java.util.List;


public class SoundboardRecyclerAdapter extends RecyclerView.Adapter<SoundboardRecyclerAdapter.SoundboardViewHolder> {

    Context context;
    List<UploadMeme> arrayListMemes;


    public SoundboardRecyclerAdapter(Context context, List<UploadMeme> arrayListMemes) {
        this.context = context;
        this.arrayListMemes = arrayListMemes;
    }

    @Override
    public SoundboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sound_item, parent, false);
        return new SoundboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SoundboardViewHolder holder, int position) {
        UploadMeme uploadMeme = arrayListMemes.get(position);
        holder.titleText.setText(uploadMeme.getMemeTitle());


    }

    @Override
    public int getItemCount() {
        return arrayListMemes.size();
    }

    public class SoundboardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView titleText;

        public SoundboardViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.textViewItem);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //we need to play selected audio
            try {
                ((MainActivity) context).playMeme(arrayListMemes, getAdapterPosition());
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}
