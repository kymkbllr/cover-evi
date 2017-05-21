package com.coverevi.coverevi.Misc;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import android.app.Fragment;

import com.coverevi.coverevi.Fragments.*;
import com.coverevi.coverevi.MainActivity;
import com.coverevi.coverevi.R;

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
                //ana ekran fragmentini başlat
                FragmentManager f = activity.getFragmentManager();
                FragmentTransaction ft = f.beginTransaction();

                Fragment a;
                String tag;

                switch (position){
                    case 1: {
                        a = new Coverlar();
                        tag = "Coverlar";
                        break;
                    }
                        case 2: {
                            a = new Cekimlerimiz();
                            tag = "Çekimlerimiz";
                            break;
                        }
                    case 3: {
                        a = new Etkinlikler();
                        tag = "Etkinlikler";
                        break;
                    }
                    case 4: {
                        a = new Hakkimizda();
                        tag = "Hakkımızda";
                        break;
                    }
                    case 5: {
                        a = new İletisim();
                        tag = "İletişim";
                        break;
                    }
                    default : {
                        a = new AnaEkranFragment();
                        tag = "AnaEkranFragment";
                        break;
                    }
                }

                ft.replace(R.id.anaicerik_fragment, a, tag);
                ft.addToBackStack(tag);
                ft.commit();


                //menü itemine bastıktan sonra drawer kapanmıyor.
                DrawerLayout drawerLayout = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawers();
            }
        });

        return view;

    }
}
