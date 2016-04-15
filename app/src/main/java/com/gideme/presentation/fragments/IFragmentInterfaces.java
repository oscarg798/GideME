package com.gideme.presentation.fragments;

import android.location.Location;

/**
 * Created by oscargallon on 4/14/16.
 */
public class IFragmentInterfaces {

    public interface ICategoryFragment {
        void onLocationGot(Location location);
    }


}
