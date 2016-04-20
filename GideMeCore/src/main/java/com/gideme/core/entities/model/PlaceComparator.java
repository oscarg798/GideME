package com.gideme.core.entities.model;


import com.gideme.core.entities.dto.PlaceDTO;

import java.util.Comparator;

/**
 * Created by oscargallon on 4/10/16.
 */
public class PlaceComparator implements Comparator<PlaceDTO> {
    @Override
    public int compare(PlaceDTO place1, PlaceDTO place2) {
        return Double.compare(place1.getDistanceFromUserLocationToPlace(),
                place2.getDistanceFromUserLocationToPlace());
    }
}
