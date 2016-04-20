package com.gideme.wearmodule;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.DelayedConfirmationView;
import android.support.wearable.view.DismissOverlayView;
import android.view.View;
import android.view.WindowInsets;
import android.widget.FrameLayout;

import com.gideme.core.entities.dto.LocationDTO;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class TransitActivity extends WearableActivity implements IFragmentInterfaces.IMapFragment{

    private static final SimpleDateFormat AMBIENT_DATE_FORMAT =
            new SimpleDateFormat("HH:mm", Locale.US);

    private Fragment fragment;
    private DismissOverlayView mDismissOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transit2);
        setAmbientEnabled();

        final FrameLayout topFrameLayout = (FrameLayout) findViewById(R.id.root_container);

        final FrameLayout mapFrameLayout = (FrameLayout) findViewById(R.id.wear_frame_layout);

        topFrameLayout.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                // Call through to super implementation and apply insets
                insets = topFrameLayout.onApplyWindowInsets(insets);

                FrameLayout.LayoutParams params =
                        (FrameLayout.LayoutParams) mapFrameLayout.getLayoutParams();

                // Add Wearable insets to FrameLayout container holding map as margins
                params.setMargins(
                        insets.getSystemWindowInsetLeft(),
                        insets.getSystemWindowInsetTop(),
                        insets.getSystemWindowInsetRight(),
                        insets.getSystemWindowInsetBottom());
                mapFrameLayout.setLayoutParams(params);

                return insets;
            }
        });

        // Obtain the DismissOverlayView and display the introductory help text.
        mDismissOverlay = (DismissOverlayView) findViewById(R.id.dismiss_overlay);
        mDismissOverlay.setIntroText(R.string.intro_text);
        mDismissOverlay.showIntroIfNecessary();



        changeFragment(WearMapFragment.newInstance());

    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);
        updateDisplay();
    }



    @Override
    public void onUpdateAmbient() {
        super.onUpdateAmbient();
        updateDisplay();
    }

    @Override
    public void onExitAmbient() {
        updateDisplay();
        super.onExitAmbient();
    }

    private void updateDisplay() {

    }

    public void changeFragment(Fragment fragment) {
        this.fragment = fragment;
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.wear_frame_layout, fragment)
                .addToBackStack(null)
                .commit();

    }


    @Override
    public void onMapInitializatedListener() {
        LocationDTO locationDTO = new LocationDTO(Double.parseDouble(getString(R.string.medellin_latitude)),
                Double.parseDouble(getString(R.string.medellin_longitude)));

        ((WearMapFragment) fragment).loadMapLayer(locationDTO);
    }

    @Override
    public void onMapLongClickListener() {
        mDismissOverlay.show();
    }



}
