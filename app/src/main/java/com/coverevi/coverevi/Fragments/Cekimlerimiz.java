package com.coverevi.coverevi.Fragments;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.coverevi.coverevi.HTTP.HttpHandler;
import com.coverevi.coverevi.R;

/**
 * Created by cezvedici on 26.04.2017.
 */

public class Cekimlerimiz extends Fragment {
    private View view;
    public ListView listView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cekimlerimiz, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();


        listView = (ListView) view.findViewById(R.id.cecelist);
        new cekimleri_getir().execute();
    }

    public class cekimleri_getir extends AsyncTask<Integer, Void, Void> {
        public String response;

        @Override
        protected void onPostExecute(Void aVoid) {
           listView.setAdapter(new com.coverevi.coverevi.Adapters.Cekimlerimiz(getActivity(),response));
        }

        @Override
        protected Void doInBackground(Integer... params) {
            // Log.i("COVERLOAD", "Yüklenecek kategori numarası " + params[0]);
            HttpHandler httpHandler = new HttpHandler("http://coverevi.com/api/cekim_getir.php");
            this.response = httpHandler.createGETRequest();

            return null;
        }
    }
}

