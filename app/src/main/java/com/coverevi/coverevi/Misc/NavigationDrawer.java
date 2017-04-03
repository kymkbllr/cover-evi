package com.coverevi.coverevi.Misc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.coverevi.coverevi.MainActivity;
import com.coverevi.coverevi.R;
import com.coverevi.coverevi.Activities.cekimlerimiz;
import com.coverevi.coverevi.Activities.coverlar;
import com.coverevi.coverevi.Activities.etkinlikler;
import com.coverevi.coverevi.Activities.hakkimizda;
import com.coverevi.coverevi.Activities.iletisim;

public class NavigationDrawer extends BaseAdapter{

    private Activity activity;
    private LayoutInflater layoutInflater;
    private String [] menuogeleri;

    public NavigationDrawer(Activity activity) {
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        menuogeleri= new String[] {
                "anasayfa",
                "coverlar",
                "çekimlerimiz",
                "etkinlikler",
                "hakkımızda",
                "iletişim",
        };

        this.activity = activity;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.nav_item, null);

        Button btn = (Button) view.findViewById(R.id.nav_item);
        btn.setText(menuogeleri[position]);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;

                switch (position) {
                    default: intent = new Intent(activity, MainActivity.class); break;
                    case 1: intent = new Intent(activity, coverlar.class); break;
                    case 2: intent = new Intent(activity, cekimlerimiz.class); break;
                    case 3: intent = new Intent(activity, etkinlikler.class); break;
                    case 4: intent = new Intent(activity, hakkimizda.class); break;
                    case 5: intent = new Intent(activity, iletisim.class); break;
                }

                activity.startActivity(intent);
            }
        });

        return view;

    }
}
