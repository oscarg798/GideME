package com.gideme.wearmodule;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;


import com.gideme.core.controllers.MapFragmentController;
import com.gideme.core.entities.dto.LocationDTO;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.kml.KmlContainer;
import com.google.maps.android.kml.KmlLayer;


import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

/**
 * MapFrament class that extends from {@link Fragment}
 * Activities that contain this fragment must implement the
 * {@link IFragmentInterfaces.IMapFragment} interface
 * to handle when map is loadd.
 * Use the {@link WearMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WearMapFragment extends com.google.android.gms.maps.MapFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private IFragmentInterfaces.IMapFragment iMapFragment;

    private GoogleMap map;

    private static final int PLACE_ZOOM = 15;

    private static final int LAYER_ZOOM = 12;

    private WearMapFragmentController wearMapFragmentController;


    public WearMapFragment() {
        // Required empty public constructor
    }

    public static WearMapFragment newInstance() {

        return new WearMapFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wearMapFragmentController = new WearMapFragmentController(getActivity());
    }


    public void initComponents() {
        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                //map.setMyLocationEnabled(true);
                iMapFragment.onMapInitializatedListener();
                map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {
                        iMapFragment.onMapLongClickListener();
                    }
                });

            }
        });


    }

    public void loadPlaceLocation(LocationDTO locationDTO) {
        Marker m = map.addMarker(new MarkerOptions()
                .position(new LatLng(locationDTO.getLat(), locationDTO.getLng())));
        makeCameraUpdate(locationDTO, PLACE_ZOOM);


    }

    private void makeCameraUpdate(LocationDTO locationDTO, int zoom) {
        CameraPosition posicion = new CameraPosition.Builder().target(
                new LatLng(locationDTO.getLat(), locationDTO.getLng()))
                .zoom(zoom).build();
        CameraUpdate camUpd = CameraUpdateFactory.newCameraPosition(posicion);
        map.animateCamera(camUpd, 2000, null);
    }

    public void loadMapLayer(LocationDTO locationDTO) {

        map.addMarker(new MarkerOptions()
                .position(new LatLng(locationDTO.getLat(), locationDTO.getLng()))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        try {
            KmlLayer layer = new KmlLayer(map, com.gideme.core.R.raw.pico,
                    getActivity().getApplicationContext());
            layer.addLayerToMap();

            List<KmlContainer> kmlContainers = (List<KmlContainer>) layer.getContainers();
            kmlContainers = (List<KmlContainer>) kmlContainers.get(0).getContainers();
            wearMapFragmentController.checkTransitRestriction(kmlContainers.get(0).getPlacemarks(),
                    new LatLng(locationDTO.getLat(), locationDTO.getLng()));

            makeCameraUpdate(locationDTO, LAYER_ZOOM);

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onAttach(getActivity());

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof IFragmentInterfaces.IMapFragment) {
            iMapFragment = (IFragmentInterfaces.IMapFragment) activity;
            initComponents();

        } else {
            throw new RuntimeException(activity.getApplicationContext().toString()
                    + " must implement IFragmentInterfaces.IMapFragment");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        iMapFragment = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private WearMapFragment getMapFragment() {
        android.app.FragmentManager fm = null;

        Log.d("MAP", "sdk: " + Build.VERSION.SDK_INT);
        Log.d("MAP", "release: " + Build.VERSION.RELEASE);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Log.d("MAP", "using getFragmentManager");
            fm = getFragmentManager();
        } else {
            Log.d("MAP", "using getChildFragmentManager");
            fm = getChildFragmentManager();
        }

        return (WearMapFragment) fm.findFragmentById(R.id.map);
    }

}
