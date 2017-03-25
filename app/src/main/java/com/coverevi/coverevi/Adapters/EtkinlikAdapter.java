package com.coverevi.coverevi.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coverevi.coverevi.R;

/**
 * Created by cezvedici on 16.03.2017.
 */

public class EtkinlikAdapter extends RecyclerView.Adapter {

    private LayoutInflater layoutInflater;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View view) {
            super(view);

        }
    }

    public EtkinlikAdapter(Activity activity){
        layoutInflater =(LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.youtube_item, null);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }
}