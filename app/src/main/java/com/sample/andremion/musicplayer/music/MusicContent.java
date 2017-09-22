/*
 * Copyright (c) 2016. André Mion
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sample.andremion.musicplayer.music;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;

import com.sample.andremion.musicplayer.R;

import java.io.File;
import java.util.ArrayList;

public class MusicContent {
    Context context;

    public MusicContent(Context context) {
        this.context = context;
    }

    public final ArrayList<MusicItem> ITEMS = getAllAudioFromDevice(context);

    static {
//        ITEMS.add(new MusicItem(R.drawable.album_cover_death_cab, "I will possess your heart", "Death Cab for Cutie", 515));
//        ITEMS.add(new MusicItem(R.drawable.album_cover_the_1975, "You", "the 1975", 591));
//        ITEMS.add(new MusicItem(R.drawable.album_cover_pinback, "The Yellow Ones", "Pinback", 215));
//        ITEMS.add(new MusicItem(R.drawable.album_cover_soad, "Chop suey", "System of a down", 242));
//        ITEMS.add(new MusicItem(R.drawable.album_cover_two_door, "Something good can work", "Two Door Cinema Club", 164));
    }




    public ArrayList<MusicItem> getAllAudioFromDevice(final Context context) {

        final ArrayList<MusicItem> tempAudioList = new ArrayList<>();

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
                File file=new File(path);
                MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                mediaMetadataRetriever.setDataSource(file.getAbsolutePath());
                String durationStr = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                int timeduration= Integer.parseInt(formateMilliSeccond(Long.parseLong(durationStr)));
                MusicItem audioModel=new MusicItem(R.drawable.album_cover_two_door,name,album,timeduration);
                tempAudioList.add(audioModel);
            }
            c.close();
        }
        return tempAudioList;
    }
    public static String formateMilliSeccond(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);

        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        //      return  String.format("%02d Min, %02d Sec",
        //                TimeUnit.MILLISECONDS.toMinutes(milliseconds),
        //                TimeUnit.MILLISECONDS.toSeconds(milliseconds) -
        //                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds)));

        // return timer string
        return finalTimerString;
    }

}
