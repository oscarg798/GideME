package com.gideme.entities.providers.interfaces;

import android.location.Location;

import com.gideme.utils.ErrorCodes;

/**
 * Created by oscargallon on 4/12/16.
 */
public final class LocationProviderUtils {


    public interface onGetLocation {
        void locationGot(Location location);

        void errorGettingLocation(ErrorCodes error);

    }

    public interface onSubscribeforLocationUpdates {
        void locationUpdateGot(Location location);

    }
}
