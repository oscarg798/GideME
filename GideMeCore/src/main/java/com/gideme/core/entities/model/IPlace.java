package com.gideme.core.entities.model;

import android.content.Context;


import com.gideme.core.entities.dto.LocationDTO;
import com.gideme.core.entities.dto.PlaceDTO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by oscargallon on 4/4/16.
 */
public interface IPlace {

    /**
     * Metodo para obtener un lugar desde un JSON
     *
     * @param jsonObject objeto json del lugar
     * @param context    contexto de la aplicación
     * @return PlaceDTO creado
     * Null en caso de error
     */
    PlaceDTO getPlaceFromJSonObject(JSONObject jsonObject, Context context);

    /**
     * Metodo para adicionar propiedades a un objeto PlaceDTO
     * creado previamente
     *
     * @param jsonObject JSON con las propiedades a adicionar
     * @param context    contexto de la aplicación
     * @param placeDTO   el lugar modificado
     *                   el lugar sin modificar
     * @return
     */
    PlaceDTO addInformationToPlaceDTO(JSONObject jsonObject, Context context, PlaceDTO placeDTO);


    /**
     * Metodo para obtener una lista de lugares de un arreglo json
     *
     * @param jsonArray arreglo de lugares en formato json
     * @param context   contexto de la aplicación
     * @return lista de lugares PlaceDTO
     * null si existe un error
     */
    List<PlaceDTO> getPlacesFromJsonArray(JSONArray jsonArray, Context context);

    void addPhotoUrlToPlace(JSONArray jsonArray, Context context);

    /**
     * Metodo para obtener los lugares por categoria
     *
     * @param placesByCategory callback para llamar en caso
     *                         de exito o error
     */
    void getPlacesbyCategory(IPlacesByCategory placesByCategory, String category, String radius
            , LocationDTO locationDTO, Context context);

    void orderPlaceListByDistanceToUserLocation(List<PlaceDTO> placeDTOList, LocationDTO userLocation);

    void getPlaceInformation(PlaceDTO placeDTO, IPlaceInformation iPlaceInformation,
                             Context context);

}
