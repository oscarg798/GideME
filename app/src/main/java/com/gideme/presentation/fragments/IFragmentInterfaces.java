package com.gideme.presentation.fragments;

import android.location.Location;

import java.io.Serializable;

/**
 * Created by oscargallon on 4/14/16.
 */
public class IFragmentInterfaces {

    public interface ICategoryFragment {
        void onLocationGot(Location location);
    }

    public interface IMapFragment extends Serializable{
        void onMapInitializatedListener();
    }


}
