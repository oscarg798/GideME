package com.gideme.core.entities.model;



import com.gideme.core.entities.dto.PlaceDTO;

import java.util.List;

/**
 * Created by ogallonr on 05/04/2016.
 */
public interface IPlacesByCategory {

    void getPlacesByCategorySuccess(List<PlaceDTO> placeDTOList);

    void getPlacesByCategoryFail(String message);


}
