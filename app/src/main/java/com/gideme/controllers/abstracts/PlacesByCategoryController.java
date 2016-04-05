package com.gideme.controllers.abstracts;

import android.app.Activity;

import com.gideme.entities.dto.LocationDTO;
import com.gideme.entities.dto.PlaceDTO;
import com.gideme.entities.model.IPlacesByCategory;
import com.gideme.entities.model.Place;

import java.util.List;

/**
 * Created by ogallonr on 05/04/2016.
 */
public class PlacesByCategoryController extends AbstractController {

    public static  PlacesByCategoryController instance;

    /**
     * Contructor de la clase
     *
     * @param activity actividad a la cual pertenece el controlador
     */
    private  PlacesByCategoryController(Activity activity) {
        super(activity);
    }

    public static PlacesByCategoryController getInstance(Activity activity){
        if(instance == null){
            instance = new PlacesByCategoryController(activity);
        }
        return instance;
    }

    public void getPlacesByCategory(String category, String radius, LocationDTO locationDTO) {
        Place.getInstance().getPlacesbyCategory(new IPlacesByCategory() {
            @Override
            public void getPlacesByCategorySuccess(List<PlaceDTO> placeDTOList) {

            }

            @Override
            public void getPlacesByCategoryFail(String message) {

            }
        }, category, radius, locationDTO, this.getActivity().getApplicationContext());

    }
}
