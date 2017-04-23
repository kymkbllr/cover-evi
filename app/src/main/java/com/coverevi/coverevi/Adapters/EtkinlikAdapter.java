package com.coverevi.coverevi.Adapters;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.coverevi.coverevi.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by cezvedici on 16.03.2017.
 */

public class EtkinlikAdapter implements ListAdapter {
    private LayoutInflater layoutInflater;
    private String Response;
    private ArrayList<String> etkinlikler;
    private boolean isEmpty = false;

    public EtkinlikAdapter(Activity activity, String response) {
        this.layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.Response = response;

        etkinlikler = new ArrayList<>();

        try {
            JSONObject oHaber = new JSONObject(response);
            JSONArray aArray = oHaber.getJSONArray("results");

            for (int i = 0; i < aArray.length(); i++) {
                JSONObject jsonObject = aArray.getJSONObject(i);
                String title = jsonObject.getString("title");

                etkinlikler.add(title);
            }

        } catch (Exception e) {
            Log.i("Exception!", "An exception has occured while reading cover data! " + e.getMessage());
        }

        if (etkinlikler.size() == 0) {
            isEmpty = true;
        }
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
        return etkinlikler.size() == 0 ? 1 : etkinlikler.size();
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
        View view = layoutInflater.inflate(R.layout.simple_list_item_1, null);
        TextView tv1 = (TextView) view.findViewById(R.id.text1);

        if (!isEmpty) {
            tv1.setText("- " + etkinlikler.get(position));
        } else {
            tv1.setText("(yaklaşan etkinlik yok)");
        }

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