package com.coverevi.coverevi.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.coverevi.coverevi.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kymkbllr on 08.06.2017.
 */

public class Cekimlerimiz extends BaseAdapter {
    private final ArrayList<CekimlerimizItem> CekimlerimizItemList;
    private LayoutInflater layoutInflater;
    private Activity activity;


    public Cekimlerimiz(Activity activity, String response) {
        this.activity = activity;
        layoutInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        CekimlerimizItemList = new ArrayList<>();

        try {
            JSONObject oHaber = new JSONObject(response);
            JSONArray aArray = oHaber.getJSONArray("results");

            for (int i = 0; i < aArray.length(); i++) {

                Cekimlerimiz.CekimlerimizItem cekimlerimizItem = new CekimlerimizItem();
                JSONObject jsonObject = aArray.getJSONObject(i);

                cekimlerimizItem.title = jsonObject.getString("title");
                cekimlerimizItem.introtext = jsonObject.getString("introtext");
                cekimlerimizItem.video = jsonObject.getString("video");
                cekimlerimizItem.youtube_id = jsonObject.getString("youtube_id");

                CekimlerimizItemList.add(cekimlerimizItem);
            }

        } catch (Exception e) {
            Log.i("Exception!", "An exception has occured while reading cover data! " + e.getMessage());
        }
    }

    @Override
    public int getCount() {
        return CekimlerimizItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.cekim_item, null);

        ImageButton onizleme  = (ImageButton) view.findViewById(R.id.onizleme);

        onizleme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://youtu.be/" + CekimlerimizItemList.get(position).youtube_id));
                activity.startActivity(browserIntent);
            }
        });
        TextView textView = (TextView) view.findViewById(R.id.cekim_id);

        textView.setText(CekimlerimizItemList.get(position).title);

        Picasso.with(activity.getApplicationContext())
                // yutup videosunun önizleme resmini nerden yükleyeceğini anlaması için https si ile bi url aldım hepsinde sabit
                //ama her videoda farklı önizleme gerçekleyeceğinden cekimlerimizitemlistle farkı aldım.//
                .load("https://img.youtube.com/vi/" +CekimlerimizItemList.get(position).youtube_id +"/0.jpg")
                .into(onizleme);

        return view;
    }

    public class CekimlerimizItem {
        public String title;
        public String introtext;
        public String video;
        public String youtube_id;
    }

}
