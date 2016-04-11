package com.gideme.entities.dto;

import java.io.Serializable;

/**
 * Created by ogallonr on 05/04/2016.
 */
public class CategoryDTO  implements Serializable{

    private final String name;
    private final String categoryKey;
    private final String categoryIconURL;

    public CategoryDTO(String name, String categoryKey, String categoryIconURL) {
        this.name = name;
        this.categoryKey = categoryKey;
        this.categoryIconURL = categoryIconURL;
    }

    public String getName() {
        return name;
    }

    public String getCategoryKey() {
        return categoryKey;
    }

    public String getCategoryIconURL() {
        return categoryIconURL;
    }
}
