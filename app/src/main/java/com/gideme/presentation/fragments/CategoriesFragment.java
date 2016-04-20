package com.gideme.presentation.fragments;

import android.app.Fragment;
import android.content.Context;
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

import com.gideme.core.entities.dto.CategoryDTO;
import com.gideme.core.entities.dto.LocationDTO;
import com.gideme.presentation.activities.NavigationDrawerActivity;
import com.gideme.presentation.activities.PlacesByCategoryActivity;
import com.gideme.presentation.adapters.CategoriesAdapter;
import com.gideme.presentation.listeners.RecyclerItemOnClickListener;
import com.gideme.presentation.listeners.interfaces.OnItemClickListener;
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
import com.rm.androidesentials.model.utils.CoupleParams;

import java.util.ArrayList;
import java.util.List;


public class CategoriesFragment extends Fragment  implements IFragmentInterfaces.ICategoryFragment {

    private List<CategoryDTO> categoriesList;
    private Toolbar toolbar;
    private CategoryController categoriesController;
    private RecyclerView recyclerView;
    private LocationDTO locationDTO;


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
    public void onAttach(Context context) {
        super.onAttach(context);
        if(getActivity()!= null && getActivity() instanceof NavigationDrawerActivity){
             getActivity().onAttachFragment(this);
        }
    }

    @Override
    public void onLocationGot(Location location) {
        locationDTO = new LocationDTO(location.getLatitude(), location.getLongitude());
    }
}
