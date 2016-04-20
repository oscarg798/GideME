package com.gideme.core.entities.dto;

import java.io.Serializable;

/**
 * Created by oscargallon on 4/4/16.
 * Clase para referenciar un objeto
 * que hace referencia a la latitud y longitud
 */
public class LocationDTO  implements Serializable{

    /**
     * Latitud
     */
    private final double lat;

    /**
     * Longitud
     */
    private final double lng;

    /**
     * Constructor
     * @param lat
     * @param lng
     */
    public LocationDTO(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
