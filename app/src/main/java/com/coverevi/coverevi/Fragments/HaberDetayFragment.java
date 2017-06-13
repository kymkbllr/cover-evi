package com.coverevi.coverevi.Fragments;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coverevi.coverevi.HTTP.HttpHandler;
import com.coverevi.coverevi.R;
import com.squareup.picasso.Picasso;

/**
 * Created by cezvedici on 13.06.2017.
 */

public class HaberDetayFragment extends Fragment {
    private View view;
    protected TextView baslik, icerik;
    protected ImageView resim;
    private int id;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.haber_detay, container, false);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle bundle = this.getArguments();

        baslik = (TextView) view.findViewById(R.id.haber_baslik);
        icerik = (TextView) view.findViewById(R.id.haber_icerik);
        resim = (ImageView) view.findViewById(R.id.haber_resim);

        if (bundle != null) {
            baslik.setText(bundle.getString("title"));
            icerik.setText(bundle.getString("introtext"));

            Picasso.with(getActivity())
                    .load(bundle.getString("image_url"))
                    .into(resim);
        }
    }
}
