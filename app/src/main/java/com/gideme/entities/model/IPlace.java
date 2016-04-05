package com.gideme.entities.model;

import android.content.Context;

import com.gideme.entities.dto.PlaceDTO;

import org.json.JSONObject;

/**
 * Created by oscargallon on 4/4/16.
 */
public interface IPlace {

    /**
     * Metodo para obtener un lugar desde un JSON
     * @param jsonObject objeto json del lugar
     * @param context contexto de la aplicación
     * @return PlaceDTO creado
     *         Null en caso de error
     */
    PlaceDTO getPlaceFromJSonObject(JSONObject jsonObject, Context context);

    /**
     * Metodo para adicionar propiedades a un objeto PlaceDTO
     * creado previamente
     * @param jsonObject JSON con las propiedades a adicionar
     * @param context contexto de la aplicación
     * @param placeDTO el lugar modificado
     *                 el lugar sin modificar
     * @return
     */
    PlaceDTO addInformationToPlaceDTO(JSONObject jsonObject, Context context, PlaceDTO placeDTO);

}
