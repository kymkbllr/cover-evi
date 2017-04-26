package com.coverevi.coverevi.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coverevi.coverevi.R;

/**
 * Created by cezvedici on 26.04.2017.
 */

public class Etkinlikler extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_etkinlikler, container, false);
        return view;
    }
}
