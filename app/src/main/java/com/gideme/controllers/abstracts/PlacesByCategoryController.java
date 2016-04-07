package com.gideme.controllers.abstracts;

import android.app.Activity;

import com.gideme.entities.dto.LocationDTO;
import com.gideme.entities.dto.PlaceDTO;
import com.gideme.entities.model.IPlacesByCategory;
import com.gideme.entities.model.Place;
import com.gideme.presentation.activities.MainActivity;

import java.util.List;

/**
 * Created by ogallonr on 05/04/2016.
 */
public class PlacesByCategoryController extends AbstractController {



    /**
     * Contructor de la clase
     *
     * @param activity actividad a la cual pertenece el controlador
     */
    public   PlacesByCategoryController(Activity activity) {
        super(activity);
    }



    public void getPlacesByCategory(String category, String radius, LocationDTO locationDTO) {
        showProgressDialog("Alerta", "Consultando sitios cercanos");

        Place.getInstance().getPlacesbyCategory(new IPlacesByCategory() {
            @Override
            public void getPlacesByCategorySuccess(List<PlaceDTO> placeDTOList) {

                ((MainActivity) getActivity()).showPlacesByCategory(placeDTOList);
                dismissProgressDialog();
            }

            @Override
            public void getPlacesByCategoryFail(String message) {
                    showAlertDialogWithTwoCustomOnClickListener("Error", message);
                    dismissProgressDialog();
            }
        }, category, radius, locationDTO, this.getActivity().getApplicationContext());

    }
}
