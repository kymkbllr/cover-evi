package com.coverevi.coverevi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.GridView;
import android.widget.HorizontalScrollView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: GridView screen genişliğine göre otomatik ayarlanacak
        GridView gridView = (GridView) findViewById(R.id.gdHaber);
        HaberAdapter haberAdapter = new HaberAdapter(this );
        gridView.setAdapter(haberAdapter);


        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rcYoutube);
        recyclerView.setLayoutManager(layoutManager);
        YoutubeAdapter youtubeAdapter = new YoutubeAdapter(this);
        recyclerView.setAdapter(youtubeAdapter);


    }
}
