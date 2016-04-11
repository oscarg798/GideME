package com.gideme.presentation.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.gideme.R;
import com.gideme.controllers.PlaceDetailController;
import com.gideme.entities.dto.PlaceDTO;
import com.gideme.presentation.fragments.MapFragment;
import com.gideme.utils.Utils;
import com.squareup.picasso.Picasso;

public class PlaceDetailActivity extends AppCompatActivity {

    private ImageView ivPlaceImage;

    private Toolbar toolbar;

    private PlaceDTO placeDTO;

    private CollapsingToolbarLayout collapsingToolbarLayout;

    private PlaceDetailController placeDetailController;

    private FrameLayout frameLayout;

    private TextView txtPlaceAddress;

    private TextView txtPlacePhoneNumber;

    private TextView txtPlace;

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);
        initModelComponents();
        initViewComponents();
    }

    public void initModelComponents() {
        placeDTO = (PlaceDTO) getIntent().getExtras().getSerializable(getApplicationContext()
                .getString(R.string.place_key));
        placeDetailController = new PlaceDetailController(this);
        placeDetailController.getPlaceInformation(placeDTO);
    }

    public void refreshPlaceDetails(PlaceDTO placeDTO) {
        this.placeDTO = placeDTO;


        if (placeDTO.getAddress() != null) {
            txtPlaceAddress.setText(placeDTO.getAddress());
        } else {
            txtPlaceAddress.setText(getString(R.string.not_avaliable));
        }

        if (placeDTO.getPhoneNumber() != null) {
            txtPlacePhoneNumber.setText(placeDTO.getPhoneNumber());
        } else {
            txtPlacePhoneNumber.setText(getString(R.string.not_avaliable));
        }


    }

    public void initViewComponents() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.ctl_title);

        ivPlaceImage = (ImageView) findViewById(R.id.iv_place_image);

        collapsingToolbarLayout.setTitle(placeDTO.getName());

        txtPlaceAddress = (TextView) findViewById(R.id.txt_place_Address);

        txtPlacePhoneNumber = (TextView) findViewById(R.id.txt_place_phone_number);

        frameLayout = (FrameLayout) findViewById(R.id.frame_layout);


        if (placeDTO.getPhotosReference() != null
                && placeDTO.getPhotosReference().size() > 0) {
            String placeImageUrl = getString(R.string.google_places_photos_url)
                    + Utils.INTERROGATION__SYMBOL
                    + Utils.MAX_WIDTH
                    + Utils.AMPERSAND
                    + Utils.REFERENCE
                    + placeDTO.getPhotosReference().get(0)
                    + Utils.AMPERSAND
                    + Utils.APIKEY
                    + getString(R.string.google_places_api_key);
            Picasso.with(getApplicationContext())
                    .load(placeImageUrl).into(ivPlaceImage);
        }

        fragment =
                MapFragment.newInstance(placeDTO.getLocationDTO());

        changeFragment(fragment);

    }

    public void changeFragment(Fragment fragment) {
        this.fragment = fragment;
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }

}
