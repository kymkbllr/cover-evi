package com.coverevi.coverevi;

import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.coverevi.coverevi.Misc.DPPXConverter;
import com.coverevi.coverevi.Misc.NavigationDrawer;


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

        setupActionBar();
        addDrawerItems();
        setupDrawer();
    }

    private void setupActionBar() {

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.action_bar_header);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(0xff000000));

        final RelativeLayout logoRelativeLayout = (RelativeLayout) actionBar.getCustomView().findViewById(R.id.cover_evi_logo_container);

        logoRelativeLayout.post(new Runnable() {
            @Override
            public void run() {
                ImageView logoImageView = (ImageView) actionBar.getCustomView().findViewById(R.id.cover_evi_header_logo);
                int imageWidth = logoImageView.getDrawable().getIntrinsicWidth();

                Point point = new Point();
                getWindowManager().getDefaultDisplay().getSize(point);

                int screenWidth = point.x;

                int measuredPadding = ((screenWidth - imageWidth) / 2) - (int) (DPPXConverter.convertDpToPixel(72, getApplicationContext())) ;

                logoRelativeLayout.setPadding(measuredPadding, 0, 0, 0);

            }
        });
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