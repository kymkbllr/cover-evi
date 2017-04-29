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
import java.util.Locale;

public class EtkinlikAdapter implements ListAdapter {
    private LayoutInflater layoutInflater;
    private String Response;
    private ArrayList<EtkinlikItem> etkinlikler;
    private boolean isEmpty = false;
    private int type;

    public EtkinlikAdapter(Activity activity, String response, int type) {
        this.layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.Response = response;
        this.type = type;

        etkinlikler = new ArrayList<>();

        try {
            JSONObject oHaber = new JSONObject(response);
            JSONArray aArray = oHaber.getJSONArray("results");

            for (int i = 0; i < aArray.length(); i++) {
                JSONObject jsonObject = aArray.getJSONObject(i);
                String title = jsonObject.getString("title");
                String event = jsonObject.getString("icerik");
                String adres = jsonObject.getString("adres");
                String tarih = jsonObject.getString("tarih");
                String saat = jsonObject.getString("saat");

                EtkinlikItem item = new EtkinlikItem();
                item.title = title;
                item.event = event;
                item.address = adres;
                item.tarih = tarih;
                item.saat = saat;

                etkinlikler.add(item);
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
        if (type == 0) {
            View view = layoutInflater.inflate(R.layout.simple_list_item_1, null);
            TextView tv1 = (TextView) view.findViewById(R.id.text1);

            if (!isEmpty) {
                tv1.setText("- " + etkinlikler.get(position).title);
            } else {
                tv1.setText("(yaklaşan etkinlik yok)");
            }

            return view;
        } else {
            View view = layoutInflater.inflate(R.layout.fragment_etkinlik_item, null);

            TextView title = (TextView) view.findViewById(R.id.tvEtkinlikItemTitle);
            TextView event = (TextView) view.findViewById(R.id.tvEtkinlikItemEvent);
            TextView gun = (TextView) view.findViewById(R.id.tvEtkinlikGun);
            TextView ay = (TextView) view.findViewById(R.id.tvEtkinlikAy);
            TextView saat = (TextView) view.findViewById(R.id.tvEtkinlikSaat);
            TextView adres = (TextView) view.findViewById(R.id.tvEtkinlikAdres);

            title.setText(etkinlikler.get(position).title);
            event.setText(etkinlikler.get(position).event);
            gun.setText(etkinlikler.get(position).getGun());
            ay.setText(etkinlikler.get(position).getAy());
            saat.setText(etkinlikler.get(position).saat);
            adres.setText(etkinlikler.get(position).address);

            return view;
        }
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

    public class EtkinlikItem {
        public String title;
        public String event;
        public String address;
        public String tarih, saat;

        public String getGun() {
            String [] parts = this.tarih.split("-");
            return String.format(Locale.ENGLISH, "%02d", Integer.valueOf(parts[2]));
        }

        public String getAy() {
            String [] parts = this.tarih.split("-");
            String [] aylar = new String [] {
                    "Ocak", "Şubat", "Mart", "Nisan", "Mayıs", "Haziran", "Temmuz", "Ağustos",
                    "Eylül", "Ekim", "Kasım", "Aralık"
            };

            return aylar[Integer.valueOf(parts[1]) - 1];
        }
    }
}