package com.coverevi.coverevi.Adapters;

import android.app.Activity;
import android.content.Context;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;

import com.coverevi.coverevi.Fragments.HaberDetayFragment;
import com.coverevi.coverevi.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class HaberAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private ArrayList<HaberItem> haberItemList;
    private Activity activity;

    public HaberAdapter(Activity activity, String response) {
        this.activity = activity;
        layoutInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        haberItemList = new ArrayList<>();

        try {
            JSONObject oHaber = new JSONObject(response);
            JSONArray aArray = oHaber.getJSONArray("results");

            for (int i = 0; i < aArray.length(); i++) {
                HaberItem haberItem = new HaberItem();
                JSONObject jsonObject = aArray.getJSONObject(i);

                haberItem.title = jsonObject.getString("title");
                haberItem.introtext = jsonObject.getString("introtext");
                haberItem.image_url = jsonObject.getString("resim");

                haberItemList.add(haberItem);
            }

        } catch (Exception e) {
            Log.i("Exception!", "An exception has occured while reading cover data! " + e.getMessage());
        }
    }

    @Override
    public int getCount() {
        return haberItemList.size();
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
        View view = layoutInflater.inflate(R.layout.haber_item, null);

        ImageButton ibHaber = (ImageButton) view.findViewById(R.id.ibHaber);
        Picasso.with(activity.getApplicationContext()).load(haberItemList.get(position).image_url).into(ibHaber);

        ibHaber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = activity.getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                Bundle bundle = new Bundle();

                bundle.putString("title", haberItemList.get(position).title);
                bundle.putString("introtext", haberItemList.get(position).introtext);
                bundle.putString("image_url", haberItemList.get(position).image_url);

                HaberDetayFragment haberDetayFragment = new HaberDetayFragment();
                haberDetayFragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.anaicerik_fragment, haberDetayFragment, "HaberDetay");
                fragmentTransaction.addToBackStack("HaberDetay");
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    public class HaberItem {
        public String title;
        public String introtext;
        public String image_url;
    }
}
