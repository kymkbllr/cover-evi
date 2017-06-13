package com.coverevi.coverevi;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.coverevi.coverevi.Fragments.AnaEkranFragment;
import com.coverevi.coverevi.HTTP.HttpHandler;
import com.coverevi.coverevi.Misc.DPPXConverter;
import com.coverevi.coverevi.Misc.NavigationDrawer;
import com.coverevi.coverevi.Services.StreamService;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ActionBarDrawerToggle mDrawerToggle;
    public DrawerLayout drawerLayout;
    private Intent streamService;

    ImageButton fb, tw, in, yt;
    TextView durum, calanparca;

    boolean isPlaying = false;

    MyReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.navList);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        setupActionBar();
        addDrawerItems();
        setupDrawer();

        //ana ekran fragmentini başlat
        FragmentManager f = getFragmentManager();
        FragmentTransaction ft = f.beginTransaction();

        AnaEkranFragment a = new AnaEkranFragment();
        ft.add(R.id.anaicerik_fragment, a, "ana ekran");
        ft.addToBackStack("ana ekran");
        ft.commit();

        /*
        Sosyal medya ikonlarının tıklanabilirlik özelliği aşağıda işlenmiştir.
         */

        fb = (ImageButton) findViewById(R.id.facebook);
        tw = (ImageButton) findViewById(R.id.twitter);
        in = (ImageButton) findViewById(R.id.instagram);
        yt = (ImageButton) findViewById(R.id.youtube);

        durum = (TextView) findViewById(R.id.playerDurum);
        durum.setText("Radyoyu başlatmak için soldaki butona basın.");

        calanparca = (TextView) findViewById(R.id.currentplaying);
        calanparca.setText("Çalan parça bilgisi getiriliyor..");

        streamService = new Intent(MainActivity.this, StreamService.class);

        registerReceiver();

        Timer updateSongTimer = new Timer();
        updateSongTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                new UpdateCurrentSong().execute();
            }
        }, 0, 2 * 1000);
    }

    private void registerReceiver() {
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(StreamService.CONTROL_STATUS);
        registerReceiver(myReceiver, intentFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
    }

    @Override
    public void onStop() {
        super.onStop();
        unregisterReceiver(myReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //TODO cover evi : durdu ne yazık ki cover evi ,diye exception fırlatıyor.(RECEİVER);

        unregisterReceiver(myReceiver);
        stopService(streamService);
    }

    private void setupActionBar() {

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
//action bardaki logonun ortalanmasıyla alakalı
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

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public void sosyalMedya(View view) {
        String URL;

        if (view.getId() == fb.getId()) {
            URL = "https://www.facebook.com/coverevi";
        } else if (view.getId() == tw.getId()) {
            URL = "https://twitter.com/coverevioffical";
        } else if (view.getId() == in.getId()) {
            URL = "https://www.instagram.com/coverevioffical/";
        } else {
            URL = "https://www.youtube.com/channel/UC1lXVf7UkxY09-DhXDXXBsg";
        }

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
        startActivity(browserIntent);
    }

    public void controlPlayer(View view) {
        Drawable drawable;

        isPlaying = !isPlaying;

        if (isPlaying) {
            drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.stop);
            startService(streamService);
            durum.setText("Bağlanıyor..");
        } else {
            drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.play);
            stopService(streamService);
            durum.setText("Durduruldu.");
        }

        ImageView view1 = (ImageView) findViewById(R.id.playpausebutton);
        view1.setImageDrawable(drawable);
    }

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(StreamService.CONTROL_STATUS))
            {
                durum.setText(intent.getStringExtra("message"));
            }
        }
    }

    public class UpdateCurrentSong extends AsyncTask<Void, Void, Void> {
        String response;

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                calanparca.setText(jsonObject.getString("song"));

                // her parça yeniden güncellendiği zaman
                // anaicerik_fragment ona göre şekil alacak.
                final LinearLayout playerContainer = (LinearLayout) findViewById(R.id.playerContainer);

                playerContainer.post(new Runnable() {
                    @Override
                    public void run() {
                        final FrameLayout anaicerik_fragment = (FrameLayout) findViewById(R.id.anaicerik_fragment);
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                                anaicerik_fragment.getLayoutParams()
                        );

                        int height = playerContainer.getMeasuredHeight();
                        layoutParams.setMargins(0, 0, 0, height);

                        anaicerik_fragment.setLayoutParams(layoutParams);
                    }
                });

            } catch (org.json.JSONException e) {
                Log.i("CALANPARCA", e.getMessage());
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            HttpHandler httpHandler = new HttpHandler("http://www.coverevi.com/api/sc_api/sc_api.php");
            this.response = httpHandler.createGETRequest();

            return null;
        }
    }
}