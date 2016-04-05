package com.gideme.entities.model;

import android.content.Context;

import com.gideme.R;
import com.gideme.entities.dto.LocationDTO;
import com.gideme.entities.dto.PlaceDTO;
import com.gideme.entities.utils.CoupleParams;
import com.gideme.services.http.HTTPServices;
import com.gideme.services.interfaces.IHTTPServices;
import com.gideme.utils.Utils;

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

    private final String JSON_ARRAY_FORMAT_ERROR = "error en el formato";

    private final String CAN_NOT_GET_PLACES_ERROR= "No se obtuvieron lugares";

    /**
     * Contructor privado por ser singleton
     */
    private Place() {

    }

    /**
     * Metodo para obtener la instancia unica
     *
     * @return instancia de modelo lugar
     */
    public static Place getInstance() {
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
        String [] auxStringArray = null;
        try {

            LocationDTO locationDTO = new LocationDTO(jsonObject.getJSONObject(context.getString(R.string.geometry_key))
                    .getJSONObject(context.getString(R.string.location_key)).getDouble(context
                            .getString(R.string.latitude_key)),jsonObject.getJSONObject(context.getString(R.string.geometry_key))
                    .getJSONObject(context.getString(R.string.location_key)).getDouble(context
                            .getString(R.string.latitude_key)));


            if(Utils.jsonHasProperty(jsonObject.names(),context.getString(R.string.photos_key))){
                auxJsonArray = jsonObject.getJSONArray(context.getString(R.string.photos_key));
                photosList = new ArrayList<>();
                for (int i = 0; i < auxJsonArray.length(); i++) {
                    auxJsonObject = auxJsonArray.getJSONObject(i);
                    photosList.add(auxJsonObject.getString(context.getString(R.string.photo_reference)));
                }
            }

            float rating  = 0;

            if(Utils.jsonHasProperty(jsonObject.names(),context.getString(R.string.rating_key))){
                rating = Float.parseFloat(jsonObject
                        .getString(context.getString(R.string.rating_key)));
            }

            placeDTO = new PlaceDTO.PlaceBuilder(jsonObject.getString(context.getString(R.string.name_key)),
                    jsonObject.getString(context.getString(R.string.reference_key)),
                    jsonObject.getString(context.getString(R.string.place_id_key)))
                    .nestedIconUrl(jsonObject.getString(context.getString(R.string.icon_key)))
                    .nestedRating(rating)
                    .nestedLocationDTO(locationDTO)
                    .nestedPhotosReference(photosList)
                    .nestedTypes(Utils.getStringListFromJsonArray(jsonObject.getJSONArray(context
                            .getString(R.string.types_key)))).createDTO();


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

    @Override
    public List<PlaceDTO> getPlacesFromJsonArray(JSONArray placeDTOJsonArray, Context context) {
        JSONObject placeDTOJsonObject = null;
        List<PlaceDTO> placeDTOList = new ArrayList<>();
        PlaceDTO auxPlaceDTO = null;
        for (int i = 0; i < placeDTOJsonArray.length(); i++) {
            try {
                placeDTOJsonObject = placeDTOJsonArray.getJSONObject(i);
                auxPlaceDTO = getPlaceFromJSonObject(placeDTOJsonObject, context);
                if (auxPlaceDTO != null) {
                    placeDTOList.add(auxPlaceDTO);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                auxPlaceDTO = null;
                placeDTOJsonObject = null;
                return placeDTOList = null;
            }
        }
        auxPlaceDTO = null;
        placeDTOJsonObject = null;
        return placeDTOList;
    }

    /**
     * Callback de exito del metodo que obtiene los
     * lugares por categoria
     * @param response respuesta del servicio
     * @param placesByCategory promesas a llamar
     * @param context contexto de la aplicación
     */
    private void getPlacesByCategorySucces(String response, IPlacesByCategory placesByCategory,
                                           Context context){

        try {
            JSONObject auxJsonObject  = new JSONObject(response);
            JSONArray auxJsonArray = auxJsonObject.getJSONArray(context.getString(R.string.result_key));
            if(auxJsonArray!=null){
                    List<PlaceDTO> placeDTOList = getPlacesFromJsonArray(auxJsonArray, context);
                    if(placeDTOList!=null){
                        placesByCategory.getPlacesByCategorySuccess(placeDTOList);
                    }else{
                        placesByCategory.getPlacesByCategoryFail(CAN_NOT_GET_PLACES_ERROR);
                    }
            }else{
                placesByCategory.getPlacesByCategoryFail(JSON_ARRAY_FORMAT_ERROR);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            placesByCategory.getPlacesByCategoryFail(JSON_ARRAY_FORMAT_ERROR);
        }


    }

    @Override
    public void getPlacesbyCategory(final IPlacesByCategory placesByCategory,final String category,
                                    final String radius, final LocationDTO locationDTO,
                                    final Context context) {

        CoupleParams auxCoupleParam = null;
        List<CoupleParams> coupleParams = new ArrayList<>();

        auxCoupleParam = new CoupleParams.CoupleParamBuilder(context.getString(R.string.location_key))
                .nestedParam(locationDTO.getLat()+","+locationDTO.getLng())
                .createCoupleParam();
        coupleParams.add(auxCoupleParam);

        auxCoupleParam = new CoupleParams.CoupleParamBuilder(context.getString(R.string.types_key))
                .nestedParam(category)
                .createCoupleParam();
        coupleParams.add(auxCoupleParam);

        auxCoupleParam = new CoupleParams.CoupleParamBuilder(context.getString(R.string.radius_key))
                .nestedParam(radius)
                .createCoupleParam();
        coupleParams.add(auxCoupleParam);


        auxCoupleParam = new CoupleParams.CoupleParamBuilder(context.getString(R.string.key_key))
                .nestedParam(context.getString(R.string.google_places_api_key))
                .createCoupleParam();

        coupleParams.add(auxCoupleParam);

        HTTPServices httpServices = new HTTPServices(new IHTTPServices() {
            @Override
            public void successFullResponse(String response) {
                    getPlacesByCategorySucces(response, placesByCategory, context);
            }

            @Override
            public void errorResponse(String message, JSONObject jsonObject) {
                placesByCategory.getPlacesByCategoryFail(message);
            }
        }, coupleParams, "POST", true);

        httpServices.execute(context.getString(R.string.google_places_url));
    }
}
