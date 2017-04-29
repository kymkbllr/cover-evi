package com.coverevi.coverevi.Fragments;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.coverevi.coverevi.Adapters.EtkinlikAdapter;
import com.coverevi.coverevi.HTTP.HttpHandler;
import com.coverevi.coverevi.R;

public class Etkinlikler extends Fragment {
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_etkinlikler, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        new EtkinlikGetir().execute();
    }

    private class EtkinlikGetir extends AsyncTask<Void, Void, Void> {
        private String EtkinlikResponse;

        @Override
        protected void onPostExecute(Void activity) {
            ListView lvEtkinlik = (ListView) view.findViewById(R.id.lvEtkinlikler);
            lvEtkinlik.setAdapter(new EtkinlikAdapter(getActivity(), this.EtkinlikResponse, 1));
        }

        @Override
        protected Void doInBackground(Void... params) {
            HttpHandler httpHandler = new HttpHandler("http://coverevi.com/api/etkinlik_getir.php");
            this.EtkinlikResponse = httpHandler.createGETRequest();

            return null;
        }
    }
}
