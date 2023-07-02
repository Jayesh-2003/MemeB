package com.example.soundb;

import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;

public class EventHandlerClass {
    private static final String LOG_TAG="EVENTHANDLER";
    private static MediaPlayer mp;

    public static void startMediaPlayer(View view, Integer soundID){
        try{
            if(soundID!=null){
                if(mp!=null){
                    mp.reset();
                }
                mp= MediaPlayer.create(view.getContext(),soundID);
                mp.start();
            }
        }
        catch (Exception e){
            Log.e(LOG_TAG, "Failed to Initilize MediaPlayer: "+e.getMessage() );
        }
    }
    public static void releaseMediaPlayer(){
        if(mp!=null){
            mp.release();
            mp=null;
        }
    }
}
