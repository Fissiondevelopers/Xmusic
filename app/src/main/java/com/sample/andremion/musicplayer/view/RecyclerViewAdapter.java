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

package com.sample.andremion.musicplayer.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sample.andremion.musicplayer.R;
import com.sample.andremion.musicplayer.activities.MainActivity;
import com.sample.andremion.musicplayer.music.MusicItem;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>  {

    ArrayList<MusicItem> mValues;
    Context context;
    SharedPreferences sharedpreferences;



    public RecyclerViewAdapter(ArrayList<MusicItem> items, Context context) {
        this.mValues = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mCoverView.setImageResource(holder.mItem.getmCover());
        holder.mTitleView.setText(holder.mItem.getmTitle());
        holder.mArtistView.setText(holder.mItem.getmArtist());
        holder.mDurationView.setText(DateUtils.formatElapsedTime(Long.parseLong(holder.mItem.getmDuration())));
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = mValues.get(position).getMusicpath();
                String name=mValues.get(position).getmTitle();
                String artist=mValues.get(position).getmArtist();
                Toast.makeText(context, name+artist, Toast.LENGTH_SHORT).show();
                MainActivity.name.setText(name+"-"+artist);
                sharedpreferences = context.getSharedPreferences("FIFO", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("msname",name);
                editor.commit();
//                DetailActivity.namedetail.setText(name+"-"+artist);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mCoverView;
        public final TextView mTitleView;
        public final TextView mArtistView;
        public final TextView mDurationView;
        public MusicItem mItem;
        public ViewHolder(View view) {
            super(view);
            mView = view;
            mCoverView = (ImageView) view.findViewById(R.id.cover);
            mTitleView = (TextView) view.findViewById(R.id.title);
            mArtistView = (TextView) view.findViewById(R.id.artist);
            mDurationView = (TextView) view.findViewById(R.id.duration);
        }
    }
}
