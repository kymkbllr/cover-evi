package com.coverevi.coverevi.Fragments;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coverevi.coverevi.Adapters.HaberAdapter;
import com.coverevi.coverevi.Adapters.YoutubeAdapter;
import com.coverevi.coverevi.CustomViews.ExpandableGridView;
import com.coverevi.coverevi.HTTP.HttpHandler;
import com.coverevi.coverevi.R;

public class AnaEkranFragment extends Fragment {
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.ana_ekran, container, false);

        ExpandableGridView gdHaber = (ExpandableGridView) view.findViewById(R.id.gdHaber);
        gdHaber.setExpanded(true);
        gdHaber.setAdapter(new HaberAdapter(getActivity()));

        ExpandableGridView gdTest = (ExpandableGridView) view.findViewById(R.id.gdTest);
        gdTest.setExpanded(true);
        gdTest.setAdapter(new HaberAdapter(getActivity()));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        new CoverlariGetir().execute();
    }

    private class CoverlariGetir extends AsyncTask<Void, Void, Void> {
        private String YouTubeResponse;

        @Override
        protected void onPostExecute(Void activity) {
            RecyclerView rcYoutube = (RecyclerView) view.findViewById(R.id.rcYoutube);
            rcYoutube.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            rcYoutube.setAdapter(new YoutubeAdapter(getActivity(), YouTubeResponse));
        }

        @Override
        protected Void doInBackground(Void... params) {
            HttpHandler httpHandler = new HttpHandler("http://coverevi.com/api/youtube_getir.php");
            this.YouTubeResponse = httpHandler.createGETRequest();

            return null;
        }
    }
}
