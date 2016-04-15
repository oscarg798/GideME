package com.gideme.controllers;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.provider.Settings;

import com.gideme.R;
import com.gideme.controllers.abstracts.AbstractController;
import com.gideme.entities.providers.UserLocationProvider;
import com.gideme.entities.providers.interfaces.LocationProviderUtils;
import com.gideme.presentation.fragments.CategoriesFragment;
import com.gideme.utils.ErrorCodes;
import com.gideme.utils.UTILEnum;

/**
 * Created by ogallonr on 06/04/2016.
 */
public class CategoryController extends AbstractController  {
    /**
     * Contructor de la clase
     *
     * @param activity actividad a la cual pertenece el controlador
     */
    public CategoryController(Activity activity) {
        super(activity);
    }


}
