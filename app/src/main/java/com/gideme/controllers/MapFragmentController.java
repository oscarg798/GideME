package com.gideme.controllers;

import android.app.Activity;

import com.gideme.R;

import com.gideme.core.asyn_tasks.ITransitRestriction;
import com.gideme.core.asyn_tasks.TransitRestriction;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.kml.KmlPlacemark;
import com.rm.androidesentials.controllers.abstracts.AbstractController;
import com.rm.androidesentials.utils.ErrorCodes;

import java.io.Serializable;
import java.util.List;

/**
 * Created by oscargallon on 4/18/16.
 */
public class MapFragmentController extends AbstractController implements ITransitRestriction {

    /**
     * Contructor de la clase
     *
     * @param activity actividad a la cual pertenece el controlador
     */
    public MapFragmentController(Activity activity) {
        super(activity);
    }

    public void checkTransitRestriction(Iterable<KmlPlacemark> placemarkList, LatLng latLng) {
        TransitRestriction transitRestriction = new TransitRestriction(placemarkList,
                latLng, this);
        transitRestriction.execute();

    }

    @Override
    public void userHasTransitRestriction(ErrorCodes code) {
        if (code.equals(ErrorCodes.USER_HAS_NOT_TRANSIT_RESTRICTION)) {
            showAlertDialog(getActivity().getApplicationContext()
                    .getString(R.string.alert_label), "No esta en area de pico y placa");
        }
    }

    @Override
    public void userHasNotTransitRestriction(ErrorCodes codes, LatLng locationDTO) {
        if (codes.equals(ErrorCodes.USER_HAS_TRANSIT_RESTRICTION)) {
            showAlertDialog(getActivity().getApplicationContext()
                    .getString(R.string.alert_label), "Esta en area de pico y placa");
        }
    }
}
