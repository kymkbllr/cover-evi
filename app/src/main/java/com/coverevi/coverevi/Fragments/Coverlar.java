package com.coverevi.coverevi.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.coverevi.coverevi.Adapters.CoverlarAdapter;
import com.coverevi.coverevi.HTTP.HttpHandler;
import com.coverevi.coverevi.R;

public class Coverlar extends Fragment {
    private View view;
    private int YuklenecekOgeSayisi;

    public ListView lvCoverlar;
    public CoverlarAdapter coverlarAdapter;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_coverlar, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        coverlarAdapter = new CoverlarAdapter(getActivity());

        // Yüklenecek öğe sayısı toplam kategori sayısına eşittir.
        YuklenecekOgeSayisi = CoverlarAdapter.catIds.length;

        lvCoverlar = (ListView) this.view.findViewById(R.id.lvCoverlar);

        /*
        *
        * API'den coverları çekme işlemi burada yapılacaktır.
         */

        if (YuklenecekOgeSayisi > 0) {
            progressDialog = ProgressDialog.show(getActivity(), "Yükleniyor...", "İçerik yüklenirken lütfen bekleyin! (0 / 12)");
            progressDialog.setCancelable(false);
        }

        for (Integer catId : CoverlarAdapter.catIds) {
            new CoverlariGetir().execute(catId);
        }
    }

    public void TumOgelerYuklendi() {
        progressDialog.dismiss();
        lvCoverlar.setAdapter(coverlarAdapter);
    }


    public class CoverlariGetir extends AsyncTask<Integer, Void, Void> {
        public int YuklenenOgeIndeksi = 0;
        public String response;

        @Override
        protected void onPostExecute(Void aVoid) {
            // API'den gelen cevabı CoverlerAdapterine gönder.
            coverlarAdapter.addToResponse(YuklenenOgeIndeksi, this.response);

            // Yüklenecek öğe sayısını bir azaltıyoruz.
            YuklenecekOgeSayisi--;

            // Process Dialoğu update et

            progressDialog.setMessage("İçerik yüklenirken lütfen bekleyin! (" + (12 - YuklenecekOgeSayisi)+ " / 12) ");

            // Eğer öğe sayısı 0'a eşit olursa tüm cover türleri yüklendi demektir.
            if (YuklenecekOgeSayisi == 0) {
                TumOgelerYuklendi();
            }
        }

        @Override
        protected Void doInBackground(Integer... params) {
            // Log.i("COVERLOAD", "Yüklenecek kategori numarası " + params[0]);
            YuklenenOgeIndeksi = params[0];

            HttpHandler httpHandler = new HttpHandler("http://coverevi.com/api/youtube_getir.php?tur=" + params[0]);
            this.response = httpHandler.createGETRequest();

            return null;
        }
    }

}
