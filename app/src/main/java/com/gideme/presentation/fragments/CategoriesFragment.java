package com.gideme.presentation.fragments;

import android.app.Fragment;
import android.content.IntentSender;
import android.content.res.TypedArray;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gideme.R;
import com.gideme.controllers.CategoryController;
import com.gideme.entities.dto.CategoryDTO;
import com.gideme.entities.dto.LocationDTO;
import com.gideme.entities.utils.CoupleParams;
import com.gideme.presentation.activities.PlacesByCategoryActivity;
import com.gideme.presentation.adapters.CategoriesAdapter;
import com.gideme.presentation.listeners.RecyclerItemOnClickListener;
import com.gideme.presentation.listeners.interfaces.OnItemClickListener;
import com.gideme.utils.UTILEnum;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.util.ArrayList;
import java.util.List;


public class CategoriesFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        ResultCallback<LocationSettingsResult> {

    private List<CategoryDTO> categoriesList;
    private Toolbar toolbar;
    private CategoryController categoriesController;
    private RecyclerView recyclerView;
    private LocationDTO locationDTO;
    protected GoogleApiClient googleApiClient;

    public CategoriesFragment() {

    }

    public static CategoriesFragment newInstance() {
        return new CategoriesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoriesController = new CategoryController(getActivity());
        categoriesController.setFragment(this);
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        googleApiClient.connect();
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        //**************************
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        categoriesController.getUserLocation(UTILEnum.GPS);

                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(
                                    getActivity(), 1000);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });

        //getActivity().startActivityForResult(result);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.categories_fragment, container, false);
        initViewComponents(view);
        return view;
    }

    public void initViewComponents(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        getCategoriesArray();
        createCategoriesView();
    }

    public void userLocationAvaliable(Location location) {
        locationDTO = new LocationDTO(location.getLatitude(), location.getLongitude());
    }

    public void getCategoriesArray() {
        TypedArray typedArray = getResources().obtainTypedArray(R.array.categories);
        categoriesList = new ArrayList<>();
        String[] auxStringArray = null;
        CategoryDTO categoryDTO = null;
        for (int i = 0; i < typedArray.length(); i++) {
            auxStringArray = getResources().getStringArray(typedArray.getResourceId(i, 0));
            categoryDTO = new CategoryDTO(auxStringArray[0], auxStringArray[1], auxStringArray[2]);
            categoriesList.add(categoryDTO);
        }
        typedArray.recycle();
        categoryDTO = null;
        auxStringArray = null;

    }

    public void createCategoriesView() {
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(categoriesList, getActivity().getApplicationContext());
        recyclerView.setAdapter(categoriesAdapter);


        recyclerView.addOnItemTouchListener(new RecyclerItemOnClickListener(getActivity().getApplicationContext(), new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (locationDTO != null) {
                    CategoryDTO categoryDTO = categoriesList.get(position);
                    List<CoupleParams> coupleParamses = new ArrayList<CoupleParams>();

                    coupleParamses.add(new CoupleParams.CoupleParamBuilder(getActivity().getApplicationContext()
                            .getString(R.string.category_key))
                            .nestedParam(categoryDTO.getCategoryKey()).createCoupleParam());

                    coupleParamses.add(new CoupleParams
                            .CoupleParamBuilder(getString(R.string.location_key))
                            .nestedObject(locationDTO).createCoupleParam());

                    categoriesController.changeActivity(PlacesByCategoryActivity.class, coupleParamses);
                } else {
                    categoriesController.showAlertDialog(getString(R.string.error_title),
                            getString(R.string.should_have_location));
                }

            }
        }));


    }

    @Override
    public void onConnected(Bundle bundle) {

        Log.i("CONNECT", "GOOGLE PLAY SERVICES");

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("CONNECT SUSPEND", "GOOGLE PLAY SERVICES");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("CONNECT FAILED", "GOOGLE PLAY SERVICES");
    }

    @Override
    public void onResult(LocationSettingsResult locationSettingsResult) {
        Log.i("CONNECT ON RESULT", "GOOGLE PLAY SERVICES");
    }
}
