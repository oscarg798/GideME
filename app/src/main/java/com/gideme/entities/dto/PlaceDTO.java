package com.gideme.entities.dto;

import java.util.List;

/**
 * Created by oscargallon on 4/4/16.
 */
public class PlaceDTO {

    /**
     * Referencia del lugar sirve para obtener mas información
     * con el api de google places
     */
    private final String reference;

    /**
     * Nombre del lugar
     */
    private final String name;

    /**
     * Url del icono de la categoria del lugar
     */
    private final String iconURL;

    /**
     * ID del lugar
     */
    private final String placeID;

    /**
     * Puntuación media obtenida de los usuarios
     */
    private final float rating;

    /**
     * Tipos de lugar
     */
    private final List<String> types;

    /**
     * Latitud y longitud del lugar
     */
    private final LocationDTO locationDTO;

    /**
     * referencia de las photos sirve para consumirlas
     */
    private final List<String> photosReference;

    /**
     * Dirección del lugar
     */
    private String address;

    /**
     * Modos en los cuales se puede ir al lugar
     */
    private List<String> travel_modes;

    /**
     * Constructor de un lugar
     * @param reference
     * @param name
     * @param iconURL
     * @param placeID
     * @param rating
     * @param types
     * @param locationDTO
     * @param photosReference
     * @param address
     * @param travel_modes
     */
    public PlaceDTO(String reference, String name, String iconURL, String placeID, float rating,
                    List<String> types, LocationDTO locationDTO,List<String>photosReference,
                    String address, List<String> travel_modes) {
        this.reference = reference;
        this.name = name;
        this.iconURL = iconURL;
        this.placeID = placeID;
        this.rating = rating;
        this.types = types;
        this.locationDTO = locationDTO;
        this.photosReference = photosReference;
        this.address = address;
        this.travel_modes = travel_modes;
    }

    public String getReference() {
        return reference;
    }

    public String getName() {
        return name;
    }

    public String getIconURL() {
        return iconURL;
    }

    public String getPlaceID() {
        return placeID;
    }

    public float getRating() {
        return rating;
    }

    public List<String> getTypes() {
        return types;
    }

    public LocationDTO getLocationDTO() {
        return locationDTO;
    }

    public List<String> getPhotosReference() {
        return photosReference;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getTravel_modes() {
        return travel_modes;
    }

    public void setTravel_modes(List<String> travel_modes) {
        this.travel_modes = travel_modes;
    }

    /**
     * Clase del patron Builder de un lugar
     */
    public static class PlaceBuilder {

        private String nestedReference;
        private String nestedName;
        private String nestedIconURL;
        private String nestedPlaceID;
        private float nestedRating;
        private List<String> nestedTypes;
        private LocationDTO nestedLocationDTO;
        private List<String>nestedPhotosReference;
        private String nestedAddress;
        private List<String> nestedTravelModes;


        /**
         * Contructor con los datos minimos necesesarios para contruir un lugar
         * @param nestedName nombre del lugar
         * @param nestedReference referencia lugar
         * @param nestedPlaceID ID del lugar
         */
        public PlaceBuilder(String nestedName, String nestedReference, String nestedPlaceID) {
            this.nestedName = nestedName;
            this.nestedReference = nestedReference;
            this.nestedPlaceID = nestedPlaceID;
        }


        public PlaceBuilder nestedReference(String nestedReference) {
            this.nestedReference = nestedReference;
            return this;
        }

        public PlaceBuilder nestedName(String nestedName) {
            this.nestedName = nestedName;
            return this;
        }

        public PlaceBuilder nestedIconUrl(String nestedIconURL) {
            this.nestedIconURL = nestedIconURL;
            return this;
        }

        public PlaceBuilder nestedPlaceID(String nestedPlaceID) {
            this.nestedPlaceID = nestedPlaceID;
            return this;
        }

        public PlaceBuilder nestedRating(float nestedRating) {
            this.nestedRating = nestedRating;
            return this;
        }

        public PlaceBuilder nestedTypes(List<String> nestedTypes) {
            this.nestedTypes = nestedTypes;
            return this;
        }

        public PlaceBuilder nestedLocationDTO(LocationDTO nestedLocationDTO) {
            this.nestedLocationDTO = nestedLocationDTO;
            return this;
        }

        public PlaceBuilder nestedPhotosReference(List<String>nestedPhotosReference) {
            this.nestedPhotosReference = nestedPhotosReference;
            return this;
        }

        public PlaceBuilder nestedAddress(String nestedAddress) {
            this.nestedAddress = nestedAddress;
            return this;
        }

        public PlaceBuilder nestedTravelModes(List<String> nestedTravelModes) {
            this.nestedTravelModes = nestedTravelModes;
            return this;
        }

        /**
         * Metodo para contruir un lugar con las opciones ingresadas
         * @return el lugar contruido
         */
        public PlaceDTO createDTO() {
            return new PlaceDTO(nestedReference, nestedName, nestedIconURL,
                    nestedPlaceID, nestedRating, nestedTypes, nestedLocationDTO,
                    nestedPhotosReference, nestedAddress, nestedTravelModes);
        }


    }

}
