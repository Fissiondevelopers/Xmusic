package com.sample.andremion.musicplayer.music;



public  class MusicItem {

    private final int mCover;
    private final String mTitle;
    private final String mArtist;
    private final String mDuration;
    private final String musicpath;


    public MusicItem(int cover, String title, String artist, String duration,String mpath) {
        mCover = cover;
        mTitle = title;
        mArtist = artist;
        mDuration = duration;
        musicpath=mpath;
    }

    public int getmCover() {
        return mCover;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmArtist() {
        return mArtist;
    }

    public String getmDuration() {
        return mDuration;
    }

    public String getMusicpath() {
        return musicpath;
    }
}