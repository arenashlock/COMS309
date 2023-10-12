package com.example.flushd;

import static com.example.flushd.utils.SHARED.getAccountType;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

/**
 * Jack - Creates a navigation drawer that can be used on other classes
 */
public class sidebar extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawer;

    /**
     * sets the content view to the navigation menu and navigation drawer xml
     * @param view - basic view parameter
     */
    @Override
    public void setContentView(View view) {
        mDrawer = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_sidebar, null);
        FrameLayout container = mDrawer.findViewById(R.id.activityContainer);
        container.addView(view);
        super.setContentView(mDrawer);

        Toolbar toolbar = mDrawer.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = mDrawer.findViewById(R.id.nvView);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,mDrawer,toolbar,R.string.drawer_open, R.string.drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    /**
     * Switches screens based on the item clicked in the navigation drawer
     * @param item - The selected item
     * @return false
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mDrawer.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.nav_settings:
                startActivity(new Intent(this, Settings.class));
                overridePendingTransition(0,0);
                break;
            case R.id.nav_map:
                startActivity(new Intent(this, Map.class));
                overridePendingTransition(0,0);
                break;
            case R.id.nav_bathroom:
                startActivity(new Intent(this, BathroomList.class));
                overridePendingTransition(0,0);
                break;
            case R.id.nav_job:
                if (getAccountType().equals("User")) {
                    break;
                }
                startActivity(new Intent(this, ViewJob.class));
                overridePendingTransition(0,0);
                break;
            case R.id.nav_logout:
                startActivity(new Intent(this, MainActivity.class));
                overridePendingTransition(0,0);
        }
        return false;
    }

    /**
     * Allows a title to be set to the newly changed screen
     * @param titleString - title of screen
     */
    protected void allocateActivityTitle(String titleString) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(titleString);
        }
    }
}