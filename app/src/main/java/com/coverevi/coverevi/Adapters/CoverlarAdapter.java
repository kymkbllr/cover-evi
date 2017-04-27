package com.coverevi.coverevi.Adapters;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.coverevi.coverevi.R;

import java.util.HashMap;
import java.util.Map;

public class CoverlarAdapter implements ListAdapter {
    private Activity activity;
    private LayoutInflater layoutInflater;
    private Map<Integer, String> turler;

    public static Integer [] catIds = {
            30, 31, 4, 25, 24, 23, 26, 14, 22, 21, 13, 12
    };

    private Map<Integer, String> responses;

    public CoverlarAdapter(Activity activity) {
        this.activity = activity;
        this.layoutInflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        turler = new HashMap<Integer, String>();

        turler.put(30, "AKUSTİK MÜZİK");
        turler.put(31, "ALTERNATİF");
        turler.put(4, "COVER");
        turler.put(25, "JAZZ");
        turler.put(24, "BLUES");
        turler.put(23, "FUNK");
        turler.put(26, "ELEKTRONİK MÜZİK");
        turler.put(14, "KARADENİZ MÜZİĞİ");
        turler.put(22, "ETNİK MÜZİK");
        turler.put(21, "TÜRK SANAT MÜZİĞİ");
        turler.put(13, "POPÜLER MÜZİK");
        turler.put(12, "ROCK MÜZİK");

        responses = new HashMap<Integer, String>();
    }

    public void addToResponse(int catId, String response) {
        responses.put(catId, response);
        // Log.i("COVERLOAD", "View'i oluşturulan cover " + turler.get(catId) + ", cevap: " + response);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return 12;
    }


    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.fragment_cover_item, null);

        /*Log.i("COVERLOAD", "View'i oluşturulan cover position "  + position + ", " + turler.get(catIds[position]));
        Log.i("COVERLOAD", "Cevap: " + responses.get(catIds[position]));*/

        TextView tv = (TextView) view.findViewById(R.id.tvCoverItem);
        tv.setText(turler.get(catIds[position]));

        RecyclerView rcYoutube = (RecyclerView) view.findViewById(R.id.rcCoverItem);
        rcYoutube.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        rcYoutube.setAdapter(new YoutubeAdapter(activity, responses.get(catIds[position])));

        return view;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
