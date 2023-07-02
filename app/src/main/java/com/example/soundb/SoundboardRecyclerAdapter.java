package com.example.soundb;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SoundboardRecyclerAdapter extends RecyclerView.Adapter<SoundboardRecyclerAdapter.SoundboardViewHolder> {
    private ArrayList<SoundObject> soundObjects;
    public SoundboardRecyclerAdapter(ArrayList<SoundObject> soundObjects){
        this.soundObjects=soundObjects;
    }
    @Override
    public SoundboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.sound_item,null);
        return new SoundboardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SoundboardViewHolder holder, int position) {
        final SoundObject object= soundObjects.get(position);
        final Integer soundID =object.getItemID();
        holder.itemTextView.setText(object.getItemName());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventHandlerClass.startMediaPlayer(v,soundID);
            }
        });

    }

    @Override
    public int getItemCount() {
        return soundObjects.size();
    }
    public class SoundboardViewHolder extends RecyclerView.ViewHolder{

        TextView itemTextView;
        public SoundboardViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTextView=(TextView) itemView.findViewById(R.id.textViewItem);
        }
    }
}
