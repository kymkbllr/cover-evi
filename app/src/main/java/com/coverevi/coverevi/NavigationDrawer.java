package com.coverevi.coverevi;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public class NavigationDrawer extends BaseAdapter{

    private LayoutInflater layoutInflater;
    private String [] menuogeleri;

    public NavigationDrawer(Activity activity) {
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        menuogeleri= new String[] {
                "anasayfa",
                "hakkımızda",
                "iletişim",
                "çekimlerimiz",
                "etkinlikler"
        };
    }

    @Override
    public int getCount() {
        return menuogeleri.length;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.nav_item, null);

        Button btn = (Button) view.findViewById(R.id.nav_item);
        btn.setText(menuogeleri[position]);

        return view;

    }
}
