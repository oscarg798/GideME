package com.gideme.controllers;

import android.app.Activity;
import android.content.DialogInterface;
import android.location.Location;

import com.gideme.R;
import com.gideme.controllers.abstracts.AbstractController;
import com.gideme.entities.providers.UserLocationProvider;
import com.gideme.entities.providers.interfaces.LocationProviderUtils;
import com.gideme.presentation.activities.CategoriesActivity;
import com.gideme.utils.ErrorCodes;
import com.gideme.utils.UTILEnum;

/**
 * Created by ogallonr on 06/04/2016.
 */
public class CategoryController extends AbstractController implements LocationProviderUtils.onGetLocation {
    /**
     * Contructor de la clase
     *
     * @param activity actividad a la cual pertenece el controlador
     */
    public CategoryController(Activity activity) {
        super(activity);
    }

    @Override
    public void locationGot(Location location) {
        ((CategoriesActivity) getActivity()).userLocationAvaliable(location);
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
                                getUserLocation(UTILEnum.NETWORK);
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
                        .getUserLocationByGPS(this);
                break;
            case NETWORK:
                UserLocationProvider
                        .getLocationProvider(getActivity().getApplicationContext())
                        .getUserLocationByNetwork(this);
        }


    }


}
