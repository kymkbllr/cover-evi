package com.coverevi.coverevi.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coverevi.coverevi.Misc.DPPXConverter;
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
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        Picasso.with(activity.getApplicationContext()).load(coverItemList.get(position).thumbnail).into(holder.imageBtn);

        holder.imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://youtu.be/" + coverItemList.get(position).youtube_id));
                activity.startActivity(browserIntent);
            }
        });
        holder.tvYoutube.setText(coverItemList.get(position).title);
        //bringtofront yazıyı öne getirmesi için.ama işe yaramadı.

/*
        // son itemin margini 0 olacak
        if (position == coverItemList.size() - 1) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    (int) DPPXConverter.convertDpToPixel(100, activity.getApplicationContext()),
                    LinearLayout.LayoutParams.MATCH_PARENT
            );

            // left top right bottom
            params.setMargins(0, 0, 0, 0);

            holder.cardView.setLayoutParams(params);
        }
        */
    }

    @Override
    public int getItemCount() {
        return coverItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageButton imageBtn;
        public CardView cardView;
        public TextView tvYoutube;

        public MyViewHolder(View view) {
            super(view);
            imageBtn = (ImageButton) view.findViewById(R.id.ivYoutube);
            cardView = (CardView) view.findViewById(R.id.cwYoutube);
            tvYoutube = (TextView) view.findViewById(R.id.tvYoutube);
        }
    }

    public class CoverItem {
        public String title;
        public String youtube_id;
        public String thumbnail;
    }
}


