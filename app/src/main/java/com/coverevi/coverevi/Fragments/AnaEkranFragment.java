package com.coverevi.coverevi.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.coverevi.coverevi.Adapters.EtkinlikAdapter;
import com.coverevi.coverevi.Adapters.HaberAdapter;
import com.coverevi.coverevi.Adapters.YoutubeAdapter;
import com.coverevi.coverevi.CustomViews.ExpandableGridView;
import com.coverevi.coverevi.HTTP.HttpHandler;
import com.coverevi.coverevi.Misc.Connectivity;
import com.coverevi.coverevi.R;

public class AnaEkranFragment extends Fragment {
    private View view;
    private ProgressDialog progressDialog;
    private int leftToLoadCount = 2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.ana_ekran, container, false);

        progressDialog = ProgressDialog.show(getActivity(), "Yükleniyor...", "İçerik yüklenirken lütfen bekleyin!");
        progressDialog.setCancelable(false);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        /* Başlamadan önce internet bağlantısını kontrol edelim */
        if (!Connectivity.isNetworkAvailable(getActivity())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setMessage("Cover Evi çalışmak için internet bağlantısına ihtiyaç duyar. İnternet bağlantınızı aktif edip tekrar deneyin.")
                    .setCancelable(false)
                    .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // quit application
                            System.exit(0);
                        }
                    });

            builder.show();
        }

        new CoverlariGetir().execute();
        new HaberleriGetir().execute();
        new EtkinlikGetir().execute();
    }

    public void decreaseLoadCount() {
        leftToLoadCount--;

        if (leftToLoadCount == 0) {
            progressDialog.dismiss();
        }
    }

    private class CoverlariGetir extends AsyncTask<Void, Void, Void> {
        private String YouTubeResponse;

        @Override
        protected void onPostExecute(Void activity) {
            RecyclerView rcYoutube = (RecyclerView) view.findViewById(R.id.rcYoutube);
            rcYoutube.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            rcYoutube.setAdapter(new YoutubeAdapter(getActivity(), YouTubeResponse));
            decreaseLoadCount();
        }

        @Override
        protected Void doInBackground(Void... params) {
            HttpHandler httpHandler = new HttpHandler("http://coverevi.com/api/youtube_getir.php");
            this.YouTubeResponse = httpHandler.createGETRequest();

            return null;
        }
    }

    private class HaberleriGetir extends AsyncTask<Void, Void, Void> {
        private String HaberResponse;

        @Override
        protected void onPostExecute(Void activity) {
            ExpandableGridView gdHaber = (ExpandableGridView) view.findViewById(R.id.gdHaber);
            gdHaber.setExpanded(true);
            gdHaber.setAdapter(new HaberAdapter(getActivity(), this.HaberResponse));
            decreaseLoadCount();
        }

        @Override
        protected Void doInBackground(Void... params) {
            HttpHandler httpHandler = new HttpHandler("http://coverevi.com/api/haber_getir.php");
            this.HaberResponse = httpHandler.createGETRequest();

            return null;
        }
    }

    private class EtkinlikGetir extends AsyncTask<Void, Void, Void> {
        private String EtkinlikResponse;

        @Override
        protected void onPostExecute(Void activity) {
            ListView lvEtkinlik = (ListView) view.findViewById(R.id.lvEtkinlik);
            lvEtkinlik.setAdapter(new EtkinlikAdapter(getActivity(), this.EtkinlikResponse));

            lvEtkinlik.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getActivity().getApplicationContext(), "You've clicked on item position: " + position + ", id: " + id, Toast.LENGTH_SHORT).show();
                }
            });


            decreaseLoadCount();
        }

        @Override
        protected Void doInBackground(Void... params) {
            HttpHandler httpHandler = new HttpHandler("http://coverevi.com/api/etkinlik_getir.php");
            this.EtkinlikResponse = httpHandler.createGETRequest();

            return null;
        }
    }
}
