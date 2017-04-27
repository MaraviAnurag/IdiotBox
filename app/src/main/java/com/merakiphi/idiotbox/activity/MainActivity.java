package com.merakiphi.idiotbox.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import com.merakiphi.idiotbox.R;
import com.merakiphi.idiotbox.fragment.MoviesFragment;
import com.merakiphi.idiotbox.fragment.TvShowsFragment;

public class MainActivity extends AppCompatActivity {

        private Toolbar toolbar;
    private TextView toolbar_title;

    BottomNavigationView navigation;


    private static final String TAG = MainActivity.class.getSimpleName();
    private Fragment fragment,fragme;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fragmentManager = getSupportFragmentManager();
        fragme = new MoviesFragment();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragme).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_documents:
                    toolbar_title =(TextView) findViewById(R.id.toolbar_title);
                    toolbar_title.setText("MOVIES");
                    toolbar_title.setTextColor(getResources().getColor(R.color.colorAccent));
                    fragment = new MoviesFragment();
                    break;
                case R.id.navigation_groups:
                    toolbar_title =(TextView) findViewById(R.id.toolbar_title);
                    toolbar_title.setText("TV SHOWS");
                    toolbar_title.setTextColor(getResources().getColor(R.color.tv_show_accent));
                    fragment = new TvShowsFragment();
                    break;
                default:
                    fragment = new MoviesFragment();
                    break;
            }
            final FragmentTransaction transaction = fragmentManager.beginTransaction();
            Log.i(TAG, "onNavigationItemSelected: " + getFragmentManager().getBackStackEntryCount());
            transaction.replace(R.id.container, fragment).commit();
            return true;
        }

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search) {
        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
        startActivity(intent);}

        if (id == R.id.action_settings) {
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intent);}

        if (id == R.id.action_about_us) {
        Intent intent = new Intent(getApplicationContext(), AboutUsActivity.class);
        startActivity(intent);}

        return super.onOptionsItemSelected(item);
    }

}

