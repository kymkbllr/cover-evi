package com.coverevi.coverevi;

import android.app.ActionBar;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listView = (ListView) findViewById(R.id.navList);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        addDrawerItems();
        setupDrawer();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

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

        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.action_bar_header);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(0xff1f1d20));

    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };


        mDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(mDrawerToggle);
    }

    private void addDrawerItems() {
        NavigationDrawer adapter = new NavigationDrawer(this);
        listView.setAdapter(adapter);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        // Activate the navigation drawer toggle
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);

    }
}
