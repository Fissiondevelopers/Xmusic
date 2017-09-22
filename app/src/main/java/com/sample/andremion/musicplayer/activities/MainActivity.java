package com.sample.andremion.musicplayer.activities;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.sample.andremion.musicplayer.R;
import com.sample.andremion.musicplayer.view.MusicItem;
import com.sample.andremion.musicplayer.view.RecyclerViewAdapter;

import java.util.ArrayList;

public class MainActivity extends PlayerActivity {

    private View mCoverView;
    private View mTitleView;
    private View mTimeView;
    private View mDurationView;
    private View mProgressView;
    private View mFabView;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    private CameraManager manager;
    private boolean isFlashOn = false;
    Intent mServiceIntent;
    Context ctx;
    public Context getCtx(){
        return ctx;
    }
    FloatingActionButton fab;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_list);


        //
        mCoverView = findViewById(R.id.cover);
        mTitleView = findViewById(R.id.title);
        mTimeView = findViewById(R.id.time);
        mDurationView = findViewById(R.id.duration);
        mProgressView = findViewById(R.id.progress);
        mFabView = findViewById(R.id.fab);

        // Set the recycler adapter
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.tracks);
        assert recyclerView != null;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecyclerViewAdapter(getMusicItems()));
        ctx = this;
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onShake(int count) {
				/*
				 * The following method, "handleShakeEvent(count):" is a stub //
				 * method you would use to setup whatever you want done once the
				 * device has been shook.**/
                if(count==2){
                    Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT).show();
                    onFabClick(mFabView);
                }
            }
        });
    }

    private ArrayList<MusicItem> getMusicItems() {
        final ArrayList<MusicItem> tempAudioList = new ArrayList<>();
        Context context=MainActivity.this;

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.AudioColumns.DATA, MediaStore.Audio.AudioColumns.ALBUM, MediaStore.Audio.ArtistColumns.ARTIST,};
        Cursor c = context.getContentResolver().query(uri,   projection, null, null, null);
        if (c != null) {
            while (c.moveToNext()) {
                String path = c.getString(0);
                String album = c.getString(1);
                String artist = c.getString(2);
                String name = path.substring(path.lastIndexOf("/") + 1);
//                audioModel.setaName(name);
//                audioModel.setaAlbum(album);
//                audioModel.setaArtist(artist);
//                audioModel.setaPath(path);
//                File file=new File(path);
//                MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
//                mediaMetadataRetriever.setDataSource(file.getAbsolutePath());
//                String durationStr = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
//                int timeduration= Integer.parseInt(formateMilliSeccond(Long.parseLong(durationStr)));
                //TODO add time
                MusicItem audioModel=new MusicItem(R.drawable.album_cover_two_door,name,album,"5");
                tempAudioList.add(audioModel);
            }
            c.close();
        }
        return tempAudioList;
    }


    public void onFabClick(View view) {
        //noinspection unchecked
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                new Pair<>(mCoverView, ViewCompat.getTransitionName(mCoverView)),
                new Pair<>(mTitleView, ViewCompat.getTransitionName(mTitleView)),
                new Pair<>(mTimeView, ViewCompat.getTransitionName(mTimeView)),
                new Pair<>(mDurationView, ViewCompat.getTransitionName(mDurationView)),
                new Pair<>(mProgressView, ViewCompat.getTransitionName(mProgressView)),
                new Pair<>(mFabView, ViewCompat.getTransitionName(mFabView)));
        ActivityCompat.startActivity(this, new Intent(this, DetailActivity.class), options.toBundle());
    }
    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        super.onPause();

        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        return;
    }






    @Override
    protected void onDestroy(){
        super.onDestroy();

    }


    private boolean isMyServiceRunning(Class<?> serviceClass){
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo service : manager.getRunningAppProcesses()){
            if(serviceClass.getName().equals(service.getClass())){
                Log.d("isMyservicerunning?",true+"");
                return true;
            }
        }
        Log.d("isMyServiceRunning?",false+"");
        return false;
    }


}




