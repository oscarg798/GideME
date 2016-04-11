package com.gideme.entities.model;

import android.content.Context;

import com.gideme.entities.dto.PlaceDTO;

/**
 * Created by oscargallon on 4/11/16.
 */
public interface IPlaceInformation {

    void getPlaceInformationSuccess(PlaceDTO placeDTO);

    void getPlaceInformationFail(PlaceDTO placeDTO, String error);
}
