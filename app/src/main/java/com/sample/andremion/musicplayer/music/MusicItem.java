package com.sample.andremion.musicplayer.music;

/**
 * Created by shubham on 22/9/17.
 */

public  class MusicItem {

    private final int mCover;
    private final String mTitle;
    private final String mArtist;
    private final long mDuration;


    public MusicItem(int cover, String title, String artist, long duration) {
        mCover = cover;
        mTitle = title;
        mArtist = artist;
        mDuration = duration;
    }

    public int getCover() {
        return mCover;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getArtist() {
        return mArtist;
    }

    public long getDuration() {
        return mDuration;
    }
}