package com.gideme.presentation.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.IntentSender;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.gideme.R;
import com.gideme.controllers.abstracts.NavigationDrawerActivityController;
import com.gideme.presentation.fragments.CategoriesFragment;
import com.gideme.presentation.fragments.TripFragment;
import com.gideme.utils.UTILEnum;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        ResultCallback<LocationSettingsResult> {


    protected Fragment fragment;

    protected GoogleApiClient googleApiClient;

    protected NavigationDrawerActivityController navigationDrawerActivityController;

    protected MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initModelComponents();
        initViewComponents();

    }

    public void initViewComponents() {
        fragment = CategoriesFragment.newInstance();
        changeFragment(fragment);

    }

    public void initModelComponents() {
        navigationDrawerActivityController = new NavigationDrawerActivityController(this);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        googleApiClient.connect();
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(60 * 1000 * 2);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);


        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        navigationDrawerActivityController.getUserLocation(UTILEnum.GPS);

                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(
                                    NavigationDrawerActivity.this, 1000);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });
    }

    public void userLocationGot(Location location) {
        if (fragment != null && fragment instanceof CategoriesFragment) {
            ((CategoriesFragment) fragment).onLocationGot(location);
        }
        if (menuItem != null) {

            menuItem.setIcon(getResources().getDrawable(R.drawable.ic_my_location_white_24dp));


        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        menuItem = menu.findItem(R.id.location_avalaible);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.location_avalaible: {
                if (navigationDrawerActivityController != null) {
                    navigationDrawerActivityController.getUserLocation(UTILEnum.GPS);
                    if(!item.getIcon().equals(getResources()
                            .getDrawable(R.drawable.ic_my_location_gray_24dp))){
                        item.setIcon(getResources()
                                .getDrawable(R.drawable.ic_my_location_gray_24dp));
                    }
                }
                break;
            }
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_categories) {
            fragment = CategoriesFragment.newInstance();

        } else if (id == R.id.nav_gallery) {
            fragment = TripFragment.newInstance();

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        changeFragment(fragment);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void changeFragment(Fragment fragment) {
        this.fragment = fragment;
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.nv_frame_layout, fragment)
                .commit();

    }

    @Override
    public void onConnected(Bundle bundle) {

        Log.i("CONNECT", "GOOGLE PLAY SERVICES");

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("CONNECT SUSPEND", "GOOGLE PLAY SERVICES");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("CONNECT FAILED", "GOOGLE PLAY SERVICES");
    }

    @Override
    public void onResult(LocationSettingsResult locationSettingsResult) {
        Log.i("CONNECT ON RESULT", "GOOGLE PLAY SERVICES");
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment != null && fragment instanceof CategoriesFragment
                && navigationDrawerActivityController != null &&
                navigationDrawerActivityController.getLocation() != null) {
            ((CategoriesFragment) fragment).onLocationGot(navigationDrawerActivityController
                    .getLocation());
            navigationDrawerActivityController.setLocation(null);
        }
    }


}
