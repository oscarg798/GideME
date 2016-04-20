package com.gideme.wearmodule;

import android.app.Activity;

import com.gideme.core.R;
import com.gideme.core.asyn_tasks.ITransitRestriction;
import com.gideme.core.asyn_tasks.TransitRestriction;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.kml.KmlPlacemark;
import com.rm.androidesentials.controllers.abstracts.AbstractController;
import com.rm.androidesentials.utils.ErrorCodes;


/**
 * Created by oscargallon on 4/18/16.
 */
public class WearMapFragmentController extends AbstractController implements ITransitRestriction {

    /**
     * Contructor de la clase
     *
     * @param activity actividad a la cual pertenece el controlador
     */
    public WearMapFragmentController(Activity activity) {
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
            showConfirmationWearActivity( "No esta en area de pico y placa", true);
        }
    }

    @Override
    public void userHasNotTransitRestriction(ErrorCodes codes, LatLng locationDTO) {
        if (codes.equals(ErrorCodes.USER_HAS_TRANSIT_RESTRICTION)) {
            showConfirmationWearActivity(  "Esta en area de pico y placa", false);
        }
    }
}
