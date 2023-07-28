package com.example.soundb.Model;

import com.google.firebase.database.Exclude;

public class UploadMeme {
    public String memeTitle, memeLink, mKey;

    public UploadMeme() {

    }

    public UploadMeme(String memeTitle, String memeLink) {

        this.memeTitle = memeTitle;
        this.memeLink = memeLink;

    }

    public String getMemeTitle() {
        return memeTitle;
    }

    public void setMemeTitle(String memeTitle) {
        this.memeTitle = memeTitle;
    }

    public String getMemeLink() {
        return memeLink;
    }

    public void setMemeLink(String memeLink) {
        this.memeLink = memeLink;
    }

    @Exclude
    public String getmKey() {
        return mKey;
    }

    @Exclude
    public void setmKey(String mKey) {
        this.mKey = mKey;
    }
}
