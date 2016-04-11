package com.gideme.presentation.activities;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.gideme.R;
import com.gideme.entities.dto.PlaceDTO;
import com.gideme.utils.Utils;
import com.squareup.picasso.Picasso;

public class PlaceDetailActivity extends AppCompatActivity {

    private ImageView ivPlaceImage;

    private Toolbar toolbar;

    private PlaceDTO placeDTO;

    private CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);
        initModelComponents();
        initViewComponents();
    }

    public void initModelComponents(){
        placeDTO = (PlaceDTO) getIntent().getExtras().getSerializable(getApplicationContext()
                .getString(R.string.place_key));
    }

    public void initViewComponents(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.ctl_title);

        ivPlaceImage = (ImageView) findViewById(R.id.iv_place_image);

        collapsingToolbarLayout.setTitle(placeDTO.getName());

        if(placeDTO.getPhotosReference()!=null
                && placeDTO.getPhotosReference().size()>0){
            String placeImageUrl = getString(R.string.google_places_photos_url)
                    +Utils.INTERROGATION__SYMBOL
                    +Utils.MAX_WIDTH
                    +Utils.AMPERSAND
                    +Utils.REFERENCE
                    +placeDTO.getPhotosReference().get(0)
                    +Utils.AMPERSAND
                    +Utils.APIKEY
                    +getString(R.string.google_places_api_key);
            Picasso.with(getApplicationContext())
                    .load(placeImageUrl).into(ivPlaceImage);
        }


    }

}
