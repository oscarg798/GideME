package com.gideme.core.entities.model;


import com.gideme.core.entities.dto.PlaceDTO;

/**
 * Created by oscargallon on 4/11/16.
 */
public interface IPlaceInformation {

    void getPlaceInformationSuccess(PlaceDTO placeDTO);

    void getPlaceInformationFail(PlaceDTO placeDTO, String error);
}
