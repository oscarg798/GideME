package com.gideme.presentation.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

    private AppBarLayout appBarLayout;

    private CoordinatorLayout coordinatorLayout;

    private FloatingActionButton fab1;

    private FloatingActionButton fab;

    private boolean hasShowMenuFAB = false;

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

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.cl_place_detail);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.ctl_title);

        ivPlaceImage = (ImageView) findViewById(R.id.iv_place_image);

        collapsingToolbarLayout.setTitle(placeDTO.getName());
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBarPlus1);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBarPlus1);

        fab1 = (FloatingActionButton) findViewById(R.id.fab1);

        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);

        txtPlaceAddress = (TextView) findViewById(R.id.txt_place_Address);

        txtPlacePhoneNumber = (TextView) findViewById(R.id.txt_place_phone_number);

        frameLayout = (FrameLayout) findViewById(R.id.frame_layout);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = new AppBarLayout.Behavior();
        behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
            @Override
            public boolean canDrag(AppBarLayout appBarLayout) {
                return false;
            }
        });
        params.setBehavior(behavior);


        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Animation show_fab_1 = AnimationUtils.loadAnimation(getApplication(), R.anim.show_fab);
                    CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) fab1.getLayoutParams();
                    layoutParams.rightMargin += (int) (fab1.getWidth() * 1.7);
                    layoutParams.bottomMargin += (int) (fab1.getHeight() * 0.25);
                    fab1.setLayoutParams(layoutParams);
                    fab1.startAnimation(show_fab_1);
                    //fab1.setVisibility(View.VISIBLE);
                    fab1.setClickable(true);


            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideMenuButton((FloatingActionButton)v);
            }
        });





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

    private void hideMenuButton(FloatingActionButton fab){
        Animation hide_fab_1 = AnimationUtils.loadAnimation(getApplication(), R.anim.hide_fab);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
        layoutParams.rightMargin -= (int) (fab.getWidth() * 1.7);
        layoutParams.bottomMargin -= (int) (fab.getHeight() * 0.25);
        fab1.setLayoutParams(layoutParams);
        fab1.startAnimation(hide_fab_1);
        fab1.setClickable(false);
    }

}
