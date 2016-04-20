package com.gideme.controllers;

import android.app.Activity;
import android.util.Log;

import com.gideme.core.entities.dto.PlaceDTO;
import com.gideme.core.entities.model.IPlaceInformation;
import com.gideme.core.entities.model.Place;
import com.gideme.presentation.activities.PlaceDetailActivity;
import com.rm.androidesentials.controllers.abstracts.AbstractController;

/**
 * Created by oscargallon on 4/11/16.
 */
public class PlaceDetailController extends AbstractController implements IPlaceInformation {

    /**
     * Contructor de la clase
     *
     * @param activity actividad a la cual pertenece el controlador
     */
    public PlaceDetailController(Activity activity) {
        super(activity);
    }


    public void getPlaceInformation(PlaceDTO placeDTO) {
        Place.getInstance().getPlaceInformation(placeDTO, this, getActivity().getApplicationContext());

    }

    @Override
    public void getPlaceInformationSuccess(PlaceDTO placeDTO) {
        ((PlaceDetailActivity) getActivity()).refreshPlaceDetails(placeDTO);
    }

    @Override
    public void getPlaceInformationFail(PlaceDTO placeDTO, String error) {
        Log.e("ERROR", error);
    }
}
