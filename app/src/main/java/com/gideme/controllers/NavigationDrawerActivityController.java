package com.gideme.controllers;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.provider.Settings;

import com.gideme.R;
import com.gideme.presentation.activities.NavigationDrawerActivity;
import com.rm.androidesentials.controllers.abstracts.AbstractController;
import com.rm.androidesentials.model.interfaces.LocationProviderUtils;
import com.rm.androidesentials.model.providers.UserLocationProvider;
import com.rm.androidesentials.utils.ErrorCodes;
import com.rm.androidesentials.utils.UTILEnum;

/**
 * Created by oscargallon on 4/19/16.
 */
public class NavigationDrawerActivityController extends AbstractController
        implements LocationProviderUtils.onSubscribeforLocationUpdates,
        LocationProviderUtils.onGetLocation {

    private Location location;

    /**
     * Contructor de la clase
     *
     * @param activity actividad a la cual pertenece el controlador
     */
    public NavigationDrawerActivityController(Activity activity) {
        super(activity);
    }

    @Override
    public void locationGot(Location location) {
        this.location = location;
        ((NavigationDrawerActivity) getActivity()).userLocationGot(location);
    }

    @Override
    public void errorGettingLocation(ErrorCodes error) {
        switch (error) {
            case CAN_NOT_GET_USER_LOCATION:
                showAlertDialog(getActivity().getApplicationContext()
                        .getString(R.string.error_title), getActivity().getApplicationContext()
                        .getString(R.string.can_not_get_location));
                break;
            case NETWORK_PROVIDER_DISABLE:
                showAlertDialog(getActivity().getApplicationContext()
                        .getString(R.string.error_title), getActivity().getApplicationContext()
                        .getString(R.string.network_provider_disable));
                break;
            case GPS_PROVIDER_DISABLE:

                showAlertDialog(getActivity().getApplicationContext()
                                .getString(R.string.error_title), getActivity().getApplicationContext()
                                .getString(R.string.gps_provider_disable), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                getActivity().startActivity(myIntent);
                            }
                        }, null, getActivity().getApplicationContext().getString(R.string.ok_button_label),
                        null);
                break;
            case LOCATION_MANAGER_UNAVALAIBLE:
                showAlertDialog(getActivity().getApplicationContext()
                        .getString(R.string.error_title), getActivity().getApplicationContext()
                        .getString(R.string.location_manager_unavaliable));
                break;
        }
    }

    public void getUserLocation(UTILEnum utilEnum) {
        switch (utilEnum) {
            case GPS:
                UserLocationProvider
                        .getLocationProvider(getActivity().getApplicationContext())
                        .getUserLocationByGPS(this, this);
                break;
            case NETWORK:
                UserLocationProvider
                        .getLocationProvider(getActivity().getApplicationContext())
                        .getUserLocationByNetwork(this);
        }


    }


    @Override
    public void locationUpdateGot(Location location) {
        this.location = location;
        ((NavigationDrawerActivity) getActivity()).userLocationGot(location);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}