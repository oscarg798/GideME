package com.gideme.entities.dto;

/**
 * Created by oscargallon on 4/4/16.
 * Clase para referenciar un objeto
 * que hace referencia a la latitud y longitud
 */
public class LocationDTO {

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

}
