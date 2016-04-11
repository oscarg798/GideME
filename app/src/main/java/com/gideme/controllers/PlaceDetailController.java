package com.gideme.controllers;

import android.app.Activity;
import android.util.Log;

import com.gideme.controllers.abstracts.AbstractController;
import com.gideme.entities.dto.PlaceDTO;
import com.gideme.entities.model.IPlaceInformation;
import com.gideme.entities.model.Place;
import com.gideme.presentation.activities.PlaceDetailActivity;

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
