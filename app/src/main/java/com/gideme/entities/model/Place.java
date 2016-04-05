package com.gideme.entities.model;

import android.content.Context;

import com.gideme.R;
import com.gideme.entities.dto.LocationDTO;
import com.gideme.entities.dto.PlaceDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by oscargallon on 4/4/16.
 * Modelo de lugar se implementan los metodos del negocio
 */
public class Place implements IPlace {

    /**
     * Instancia unica de esta clase
     */
    private static final Place instance = new Place();

    /**
     * Contructor privado por ser singleton
     */
    private Place() {

    }

    /**
     * Metodo para obtener la instancia unica
     * @return instancia de modelo lugar
     */
    private static Place getInstance() {
        return instance;
    }


    @Override
    public PlaceDTO getPlaceFromJSonObject(JSONObject jsonObject, Context context) {

        if (jsonObject == null || context == null) {
            return null;
        }

        PlaceDTO placeDTO = null;
        String address = null;
        JSONObject auxJsonObject = null;
        JSONArray auxJsonArray = null;
        List<String> photosList = null;
        try {

            LocationDTO locationDTO = new LocationDTO(jsonObject.getDouble(context
                    .getString(R.string.latitude_key)), jsonObject.getDouble(context
                    .getString(R.string.latitude_key)));


            auxJsonArray = jsonObject.getJSONArray(context.getString(R.string.photos_key));

            for (int i = 0; i < auxJsonArray.length(); i++) {
                auxJsonObject = auxJsonArray.getJSONObject(i);
                photosList.add(auxJsonObject.getString(context.getString(R.string.photo_reference)));
            }


            placeDTO = new PlaceDTO.PlaceBuilder(jsonObject.getString(context.getString(R.string.name_key)),
                    jsonObject.getString(context.getString(R.string.reference_key)),
                    jsonObject.getString(context.getString(R.string.reference_key)))
                    .nestedIconUrl(jsonObject.getString(context.getString(R.string.icon_key)))
                    .nestedRating(Float.parseFloat(jsonObject.getString(context.getString(R.string.rating_key))))
                    .nestedLocationDTO(locationDTO)
                    .nestedPhotosReference(photosList)
                    .nestedTypes(new ArrayList<String>(Arrays.asList(((String[]) jsonObject.get(context
                            .getString(R.string.types_key)))))).createDTO();


        } catch (JSONException e) {
            e.printStackTrace();
            address = null;
            auxJsonObject = null;
            auxJsonArray = null;
            photosList = null;
            return placeDTO = null;
        }

        address = null;
        auxJsonObject = null;
        auxJsonArray = null;

        return placeDTO;
    }

    @Override
    public PlaceDTO addInformationToPlaceDTO(JSONObject jsonObject, Context context, PlaceDTO placeDTO) {
        JSONArray auxJsonArray = null;
        try {
            placeDTO.setAddress(jsonObject.getString(context.getString(R.string.formatted_address_key)));
            auxJsonArray = jsonObject.getJSONObject(context.getString(R.string.geometry_key)).getJSONArray(context.getString(R.string.access_points_key));
            if (auxJsonArray != null && auxJsonArray.length() > 0) {
                placeDTO.setTravel_modes(new ArrayList<>
                        (Arrays.asList((String[]) auxJsonArray.getJSONObject(0)
                                .get(context.getString(R.string.travel_modes_key)))));
            }

        } catch (JSONException e) {
            e.printStackTrace();

        }

        auxJsonArray = null;
        return placeDTO;
    }
}
