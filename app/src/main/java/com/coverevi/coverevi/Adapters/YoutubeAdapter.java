package com.coverevi.coverevi.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.coverevi.coverevi.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class YoutubeAdapter extends RecyclerView.Adapter<YoutubeAdapter.MyViewHolder> {
    private LayoutInflater layoutInflater;
    private ArrayList<CoverItem> coverItemList;
    private Activity activity;

    public YoutubeAdapter(Activity activity, String response) {
        this.activity = activity;
        layoutInflater =(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        coverItemList = new ArrayList<>();

        try {
            JSONObject oCover = new JSONObject(response);
            JSONArray aArray = oCover.getJSONArray("results");

            for (int i = 0; i < aArray.length(); i++) {
                CoverItem coverItem = new CoverItem();
                JSONObject jsonObject = aArray.getJSONObject(i);

                coverItem.title = jsonObject.getString("title");
                coverItem.youtube_id = jsonObject.getString("youtube_id");
                coverItem.thumbnail = jsonObject.getString("thumbnail");

                coverItemList.add(coverItem);
            }

        } catch (Exception e) {
            Log.i("Exception!", "An exception has occured while reading cover data! " + e.getMessage());
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.youtube_item, null);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Picasso.with(activity.getApplicationContext()).load(coverItemList.get(position).thumbnail).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return coverItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.ivYoutube);
        }
    }

    public class CoverItem {
        public String title;
        public String youtube_id;
        public String thumbnail;
    }
}


