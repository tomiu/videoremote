package com.eo.videoremote;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.eo.videoremote.fragments.VideoListFragment;
import com.eo.videoremote.interfaces.VideoSelected;
import com.eo.videoremote.models.Video;
import com.eo.videoremote.utils.Logger;

public class MainActivity extends AppCompatActivity implements VideoSelected {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (savedInstanceState == null) {
            Fragment fragment = VideoListFragment.newInstance(null);
            openFragment(fragment, false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onVideoSelected(Video video) {
        Logger.debug(TAG, "onVideoSelected: " + video);
        if (video.isDirectory()) {
            Fragment fragment = VideoListFragment.newInstance(video.getPath());
            openFragment(fragment, true);
        }
    }

    /**
     *
     * @param fragment to open
     * @param addToBackStack do we add to backstack?
     */
    void openFragment(android.support.v4.app.Fragment fragment, boolean addToBackStack) {
        final String fragmentTag = ((Object) fragment).getClass().getSimpleName(); //ugly cast for IDEA bug
        Logger.debug(TAG, fragmentTag);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, fragmentTag);
        if (addToBackStack)
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }


}
