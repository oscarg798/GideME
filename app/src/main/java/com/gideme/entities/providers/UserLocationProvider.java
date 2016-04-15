package com.gideme.entities.providers;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;


import com.gideme.entities.providers.interfaces.LocationProviderUtils;
import com.gideme.utils.ErrorCodes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oscargallon on 4/12/16.
 */
public class UserLocationProvider extends Service implements LocationListener {

    private LocationManager locationManager;

    private boolean canGetLocation = false;

    private Context context;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;

    private static final long MIN_TIME_BW_UPDATES = 0;

    private Location userLocation;

    private List<LocationProviderUtils.onSubscribeforLocationUpdates> onSubscribeforLocationUpdatesList;

    private boolean hasGotLocationOnce = false;

    public static UserLocationProvider UserLocationProvider;


    private UserLocationProvider(Context context) {
        this.context = context;
        this.locationManager = (LocationManager) this.context
                .getSystemService(LOCATION_SERVICE);
        onSubscribeforLocationUpdatesList = new ArrayList<>();

    }

    public static UserLocationProvider getLocationProvider(Context context) {
        if (UserLocationProvider == null) {
            UserLocationProvider = new UserLocationProvider(context);
        }
        return UserLocationProvider;
    }

    public void getUserLocationByNetwork(LocationProviderUtils.onGetLocation onGetLocation) {
        if (isNetworkEnabled()) {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            Log.d("Network", "Network");
            if (locationManager != null) {
                userLocation = locationManager
                        .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (userLocation != null) {
                    onGetLocation.locationGot(userLocation);
                    hasGotLocationOnce = true;
                } else {
                    onGetLocation
                            .errorGettingLocation(ErrorCodes.CAN_NOT_GET_USER_LOCATION);
                }
            } else {
                onGetLocation
                        .errorGettingLocation(ErrorCodes.LOCATION_MANAGER_UNAVALAIBLE);
            }

            locationManager.removeUpdates(this);

        } else {
            onGetLocation
                    .errorGettingLocation(ErrorCodes.NETWORK_PROVIDER_DISABLE);
        }
    }

    public void getUserLocationByGPS(LocationProviderUtils.onGetLocation onGetLocation
            , LocationProviderUtils.onSubscribeforLocationUpdates
                                             onSubscribeforLocationUpdates) {

        getLocationUpdates(onSubscribeforLocationUpdates);
        if (isGPSEnabled()) {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 0, 0, this);
            Log.d("GPS Enabled", "GPS Enabled");
            if (locationManager != null) {
                userLocation = locationManager
                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (userLocation != null) {
                    hasGotLocationOnce = true;
                    onGetLocation.locationGot(userLocation);
                }
            } else {
                onGetLocation
                        .errorGettingLocation(ErrorCodes.LOCATION_MANAGER_UNAVALAIBLE);
            }
        }

    }

    private void getLocationUpdates(LocationProviderUtils.onSubscribeforLocationUpdates onSubscribeforLocationUpdates) {
        if (this.onSubscribeforLocationUpdatesList == null) {
            this.onSubscribeforLocationUpdatesList = new ArrayList<>();
        }
        this.onSubscribeforLocationUpdatesList.add(onSubscribeforLocationUpdates);


    }

    private void unSuscribeLocationUpdates() {
        this.onSubscribeforLocationUpdatesList = null;
        if (this.locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }


    private boolean isGPSEnabled() {
        return locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private boolean isNetworkEnabled() {
        return locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    @Override
    public void onLocationChanged(Location location) {
        if (onSubscribeforLocationUpdatesList != null) {
            for (LocationProviderUtils.onSubscribeforLocationUpdates on : onSubscribeforLocationUpdatesList) {
                on.locationUpdateGot(location);
            }

            onSubscribeforLocationUpdatesList = null;
            if (locationManager != null) {
                locationManager.removeUpdates(this);

            }


        }
        unSuscribeLocationUpdates();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
