package com.gideme.core.asyn_tasks;


import com.google.android.gms.maps.model.LatLng;
import com.rm.androidesentials.utils.ErrorCodes;

/**
 * Created by oscargallon on 4/18/16.
 */
public interface ITransitRestriction {

    void userHasTransitRestriction(ErrorCodes code);

    void userHasNotTransitRestriction(ErrorCodes codes, LatLng locationDTO);
}
